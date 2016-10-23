/*
 * Copyright (C) 2016 Red Bottle Design, LLC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.redbottledesign.accounting.quickbooks.iif;

import com.redbottledesign.accounting.quickbooks.models.Name;
import com.redbottledesign.accounting.quickbooks.models.NameLine;
import com.redbottledesign.accounting.quickbooks.models.Transaction;
import com.redbottledesign.util.Argument;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Top-level representation of a QuickBooks IIF file, which can contain zero or
 * more {@link Transaction} objects.
 *
 * <p>Note that an IIF file does not do any I/O. It is merely an abstraction of
 * the contents of what can be written out to a stream or file on disk, but
 * contains none of the logic to perform those operations.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class IifFile extends CompositeExportableList {
    private Set<Name> customerNames;
    private Set<Name> vendorNames;
    private Set<Name> otherNames;

    /**
     * Default constructor for {@code IifFile}.
     */
    public IifFile() {
        this.customerNames = new TreeSet<>();
        this.vendorNames = new TreeSet<>();
        this.otherNames = new TreeSet<>();
    }

    /**
     * Adds the specified transaction to this file.
     *
     * <p>The transaction is cloned in the process. Consequently, any changes
     * made to the transaction should not have an impact on the information this
     * object exports.</p>
     *
     * @param   transaction
     *          The transaction to clone and add to this file.
     */
    public void addTransaction(final Transaction transaction) {
        this.getExportables().add(transaction.clone());
    }

    /**
     * Adds the given name to the list of customers declared at the top of the
     * file.
     *
     * <p>If a customer with exactly the same name has already been declared,
     * the name will not be exported twice. Comparisons are case sensitive.</p>
     *
     * <p>The customer name cannot appear in any other name table (i.e. it
     * cannot also appear in the vendor name table or other name table.</p>
     *
     * <p>If no customers have been added, a customer table is not added to
     * the IIF output file during export.</p>
     *
     * @param   customerName
     *          The name to add to the customer table.
     */
    public void addCustomerName(final Name customerName)
    throws IllegalArgumentException {
        Argument.ensureNotNull(customerName, "customerName");

        this.ensureUniqueAmongTables(
            customerName,
            Arrays.asList(this.getVendorNames(), this.getOtherNames()));

        this.customerNames.add(customerName);
    }

    /**
     * Gets a read-only view of the set of customer names that have been added
     * to this file.
     *
     * @return  The customer name set.
     */
    public Set<Name> getCustomerNames() {
        return Collections.unmodifiableSet(this.customerNames);
    }

    /**
     * Adds the given name to the list of vendors declared at the top of the
     * file.
     *
     * <p>If a vendor with exactly the same name has already been declared,
     * the name will not be exported twice. Comparisons are case sensitive.</p>
     *
     * <p>The vendor name cannot appear in any other name table (i.e. it
     * cannot also appear in the customer name table or other name table.</p>
     *
     * <p>If no vendors have been added, a vendor table is not added to the IIF
     * output file during export.</p>
     *
     * @param   vendorName
     *          The name to add to the vendor table.
     */
    public void addVendorName(final Name vendorName) {
        Argument.ensureNotNull(vendorName, "vendorName");

        this.ensureUniqueAmongTables(
            vendorName,
            Arrays.asList(this.getCustomerNames(), this.getOtherNames()));

        this.vendorNames.add(vendorName);
    }

    /**
     * Gets a read-only view of the set of vendor names that have been added to
     * this file.
     *
     * @return  The vendor name set.
     */
    public Set<Name> getVendorNames() {
        return Collections.unmodifiableSet(this.vendorNames);
    }

    /**
     * Adds the given entity to the list of "other names" declared at the top of
     * the file.
     *
     * <p>If an other entity with exactly the same name has already been
     * declared, the name will not be exported twice. Comparisons are case
     * sensitive.</p>
     *
     * <p>The entity cannot appear in any other name table (i.e. it cannot also
     * appear in the customer name table or vendor name table.</p>
     *
     * <p>If no "other names" have been added, an "other names" table is not
     * added to the IIF output file during export.</p>
     *
     * @param   otherName
     *          The name to add to the other names table.
     */
    public void addOtherName(final Name otherName) {
        Argument.ensureNotNull(otherName, "otherName");

        this.ensureUniqueAmongTables(
            otherName,
            Arrays.asList(this.getVendorNames(), this.getCustomerNames()));

        this.otherNames.add(otherName);
    }

    /**
     * Gets a read-only view of the set of other names that have been added to
     * this file.
     *
     * @return  The other name set.
     */
    public Set<Name> getOtherNames() {
        return Collections.unmodifiableSet(this.otherNames);
    }

    /**
     * {@inheritDoc}
     *
     * <p>A file header is automatically pre-pended to the output.</p>
     */
    @Override
    protected List<IifExportable> prepareExportables() {
        final List<IifExportable> result = new LinkedList<>();

        result.addAll(
            this.exportNames(this.customerNames, HeaderLine.Type.CUSTOMER, "CUST"));

        result.addAll(
            this.exportNames(this.vendorNames, HeaderLine.Type.VENDOR, "VEND"));

        result.addAll(
            this.exportNames(this.otherNames, HeaderLine.Type.OTHER_NAME, "OTHERNAME"));

        result.add(new TransactionHeader());
        result.addAll(super.prepareExportables());

        return result;
    }

    /**
     * Exports the given collection of names to a list of IIF exportables,
     * which constitute a "name table" in the output.
     *
     * <p>If {@code names} is an empty collection, the resulting list is
     * also empty.</p>
     *
     * @param   names
     *          The names to export.
     *
     * @param   headerType
     *          The type of header will denote the start of the table.
     *
     * @param   lineType
     *          The type that will denote the beginning of each line in the
     *          table.
     *
     *          FIXME: Make line type an enum.
     *
     * @return  The list of IIF-exportable lines for the table containing the
     *          given names; or, an empty list if {@code names} is empty.
     */
    protected List<IifExportable> exportNames(final Collection<Name> names,
                                              final HeaderLine.Type headerType,
                                              final String lineType) {
        List<IifExportable> result = new LinkedList<>();

        if (!names.isEmpty()) {
            result.add(new HeaderLine(headerType));

            result.addAll(
                names.stream()
                    .map(name -> new NameLine(lineType, name))
                    .collect(Collectors.toList()));
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Also appends a trailing newline, to signify the end of the file.
     * (Without this, QuickBooks will not process the last transaction in
     * the file.)</p>
     */
    @Override
    public String toIifString() {
        return super.toIifString() + "\n";
    }

    /**
     * Ensures that the given name does not appear in any of the given name
     * tables.
     *
     * @param   name
     *          The name being checked.
     *
     * @param   searchTables
     *          The tables to ensure do not contain the name.
     *
     * @throws  IllegalArgumentException
     *          If the given name already appears in more than one table in the
     *          same file.
     */
    protected void ensureUniqueAmongTables(final Name name, final Collection<Collection<Name>> searchTables) {
        searchTables.parallelStream()
            .filter((t) -> t.contains(name))
            .findFirst()
            .ifPresent((c) -> {
                throw new IllegalArgumentException(
                    String.format(
                        "The name `%s` must appear only in one name table. It cannot " +
                        "simultaneously appear in the 'customer name', 'vendor names', or 'other" +
                        "names' tables.",
                        name.getValue()));
                }
            );
    }
}
