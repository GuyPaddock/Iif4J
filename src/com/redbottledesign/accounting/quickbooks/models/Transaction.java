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
package com.redbottledesign.accounting.quickbooks.models;

import com.redbottledesign.accounting.TransactionEffect;
import com.redbottledesign.accounting.quickbooks.iif.CompositeExportable;
import com.redbottledesign.accounting.quickbooks.iif.IifExportable;
import com.redbottledesign.accounting.quickbooks.iif.TransactionTerminationLine;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A QuickBooks transaction.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class Transaction
extends CompositeExportable
implements Cloneable {
    /**
     * The lines in this transaction.
     */
    private List<DataLine> lines;

    /**
     * Default constructor for {@code Transaction}.
     */
    public Transaction() {
        this.setLines(Collections.emptyList());
    }

    /**
     * Gets an immutable view of the lines in this transaction.
     *
     * @return  The lines in this transaction.
     */
    public List<DataLine> getLines() {
        return Collections.unmodifiableList(lines);
    }

    /**
     * Sets the lines in this transaction.
     *
     * <p>The contents of the list are copied into a new list.</p>
     *
     * @param   lines
     *          The new list of lines.
     */
    protected void setLines(final List<DataLine> lines) {
        this.lines = new LinkedList<>(lines);
    }

    /**
     * Adds the specified line to this transaction.
     *
     * <p>The line is cloned in the process. Consequently, any changes made to
     * the line should not have an impact on the information this object
     * exports.</p>
     *
     * @param   line
     *          The line to clone and add to this transaction.
     */
    public void addLine(final DataLine line) {
        this.lines.add(line.clone());
    }

    /**
     * Calculates and returns whether or not this transaction is "in balance".
     *
     * <p>A transaction is in balance only if both the totals of debits and the
     * total of credits in the transaction are equal.</p>
     *
     * @return  {@code true} if the transaction is in balance; otherwise,
     *          {@code false}.
     */
    public boolean isInBalance() {
        return (this.calculateBalanceDiscrepancy().signum() == 0);
    }

    /**
     * Checks whether or not this transaction is considered to be in balance,
     * and raises an appropriate exception if it is not.
     *
     * @see     #isInBalance()
     *
     * @throws  IllegalStateException
     *          If the transaction is not in balance.
     */
    public void ensureIsInBalance() {
        if (!this.isInBalance()) {
            throw new IllegalStateException(
                String.format(
                    "This transaction is not in balance (CREDITS: %s, DEBITS: %s, " +
                    "DISCREPANCY: %s%n%s",
                    this.calculateCreditTotal(),
                    this.calculateDebitTotal(),
                    this.calculateBalanceDiscrepancy(),
                    this.asHumanReadableReport()));
        }
    }

    /**
     * Calculates the amount (if any) that this is "out of balance".
     *
     * <p>A transaction is in balance only if both the totals of debits and the
     * total of credits in the transaction are equal.</p>
     *
     * <p>The value returned has the following significance:
     * <ul>
     *   <li>If debits are larger than credits, it is a positive number.</li>
     *   <li>If credits are larger than debits, it is a negative number.</li>
     *   <li>If the transaction is in balance, it is zero.</li>
     * </ul></p>
     *
     * @return  The amount of any balance discrepancy.
     */
    public BigDecimal calculateBalanceDiscrepancy() {
        return this.calculateDebitTotal().subtract(this.calculateCreditTotal());
    }

    /**
     * Calculates the sum of all debit lines in this transaction.
     *
     * @return  The total amount of debits in this transaction.
     */
    public BigDecimal calculateDebitTotal() {
        return this.calculateTotal(TransactionEffect.DEBIT);
    }

    /**
     * Calculates the sum of all credit lines in this transaction.
     *
     * @return  The total amount of debits in this transaction.
     */
    public BigDecimal calculateCreditTotal() {
        return this.calculateTotal(TransactionEffect.CREDIT);
    }

    /**
     * Converts this transaction to a human-friendly report, suitable for
     * printing.
     *
     * <p>The account, debits, and credits of each line are included. The
     * transaction does not need to be in balance for this to be
     * generated.</p>
     *
     * @return  The contents of this transaction, as a report.
     */
    public String asHumanReadableReport() {
        String result = null;

        try (StringWriter   stringWriter = new StringWriter();
             PrintWriter    printWriter  = new PrintWriter(stringWriter)) {
            String rowFormat = "%-64s\t%8s\t\t%8s%n";

            printWriter.println("Transaction Report");
            printWriter.println("==================");

            printWriter.printf(rowFormat, "Account", "Debits", "Credits");

            for (DataLine line : this.getLines()) {
                Account     account     = line.getAccount();
                BigDecimal  lineValue   = line.getAmount().getValue();

                if (lineValue.signum() >= 0) {
                    printWriter.printf(rowFormat, account.getValue(), lineValue.abs(), "");
                }
                else {
                    printWriter.printf(rowFormat, account.getValue(), "", lineValue.abs());
                }
            }

            printWriter.println();
            printWriter.flush();

            result = stringWriter.toString();
        }

        catch (IOException ex) {
            System.err.println("Failed to export transaction report: " + ex.getMessage());
            ex.printStackTrace();
        }

        return result;
    }

    /**
     * Creates a shallow copy of this {@code Transaction}.
     *
     * @return  A new {@code Transaction}, having all of the same field values
     *          and references as this instance.
     */
    @Override
    public Transaction clone() {
        try {
            return (Transaction)super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Calculates the sum total of all lines in this transaction of the
     * specified type.
     *
     * @param   type
     *          The type of transaction lines to include in the sum.
     *
     * @return  The sum of all transaction lines of the specified type.
     *          (This is always a positive number, or zero).
     */
    protected BigDecimal calculateTotal(TransactionEffect type) {
        BigDecimal sum;

        sum = this.getLines()
            .parallelStream()
            .map(line -> line.getAmount().getValue())
            .filter(amount -> amount.signum() == type.getSignNumber())
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .abs();

        return sum;
    }

    /**
     * {@inheritDoc}
     *
     * <p>A transaction termination line is automatically appended to the
     * output.</p>
     *
     * <p>The transaction must be in balance in order to be exportable.</p>
     *
     * @throws  IllegalStateException
     *          If the transaction is not in balance.
     */
    @Override
    protected List<IifExportable> prepareExportables()
    throws IllegalArgumentException {
        final List<IifExportable> result;

        this.ensureIsInBalance();

        result = new LinkedList<>(this.getLines());

        result.add(new TransactionTerminationLine());

        return result;
    }
}
