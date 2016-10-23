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

import com.redbottledesign.accounting.quickbooks.util.IifUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A representation of a header line at the top of tables in IIF files.
 *
 * <p>This version of the file header configures QuickBooks to take in the
 * following columns for transactions, in the order specified below:</p>
 *
 * <ol>
 *   <li>{@code TRNSID}</li>
 *   <li>{@code TRNSTYPE}</li>
 *   <li>{@code DOCNUM}</li>
 *   <li>{@code DATE}</li>
 *   <li>{@code ACCNT}</li>
 *   <li>{@code NAME}</li>
 *   <li>{@code CLASS}</li>
 *   <li>{@code AMOUNT}</li>
 *   <li>{@code PAYMETH}</li>
 *   <li>{@code MEMO}</li>
 * </ol>
 *
 * <p>The following columns are used for Customer, Vendor, and Other Names, in
 * the order specified below:</p>
 *
 * <ol>
 *   <li>{@code NAME}</li>
 * </ol>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class HeaderLine
implements IifExportable {
    /**
     * The type of header line {@see #Type}.
     */
    private Type type;

    /**
     * Constructor for {@code HeaderLine}.
     *
     * @param   type
     *          The type of header line.
     */
    public HeaderLine(final Type type) {
        this.type = type;
    }

    /**
     * Gets the type of this header line.
     *
     * @return  The type of header line.
     */
    public Type getType() {
        return this.type;
    }

    /**
     * An enumeration of the types of header lines.
     */
    public enum Type {
        /**
         * Header line that describes the schema for transaction rows inside a
         * transaction.
         */
        TRANSACTION(new String[] {
            "!TRNS",  "DOCNUM",  "TRNSID", "TRNSTYPE", "DATE", "ACCNT", "NAME", "CLASS",
            "AMOUNT", "PAYMETH", "MEMO" }),

        /**
         * Header line that describes the schema for split rows inside a
         * transaction.
         */
        TRANSACTION_SPLIT(new String[] {
            "!SPL",   "DOCNUM",  "SPLID",  "TRNSTYPE", "DATE", "ACCNT", "NAME", "CLASS",
            "AMOUNT", "PAYMETH", "MEMO"}),

        /**
         * Header line that marks the end of the transaction schema header.
         */
        TRANSACTION_TERMINATION(new String[] {
            "!ENDTRNS", ""
        }),

        /**
         * Header line that describes the schema for customer names.
         */
        CUSTOMER(new String[] { "!CUST", "NAME" }),

        /**
         * Header line that describes the schema for vendor names.
         */
        VENDOR(new String[] { "!VEND", "NAME" }),

        /**
         * Header line that describes the schema for other names.
         */
        OTHER_NAME(new String[] { "!OTHERNAME", "NAME" });

        private final List<String> columns;

        /**
         * Enum constructor for {@code Type}.
         *
         * @param   columns
         *          The values for each of the columns in this type of header
         *          line.
         */
        Type(String[] columns) {
            this.columns = Collections.unmodifiableList(Arrays.asList(columns));
        }

        /**
         * Gets the values for each of the columns in this type of header line.
         *
         * @return  The values for this header line.
         */
        public List<String> getColumns() {
            return this.columns;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return  Each of the columns in this header line, separate by tabs.
     */
    @Override
    public String toIifString() {
        return IifUtils.joinColumns(this.getType().getColumns());
    }
}
