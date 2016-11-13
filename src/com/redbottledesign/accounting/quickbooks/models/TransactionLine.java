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

import com.redbottledesign.accounting.quickbooks.iif.IifExportable;
import com.redbottledesign.accounting.quickbooks.util.IifUtils;

import java.util.EnumSet;
import java.util.function.Supplier;

/**
 * A data line that represents the first line of a {@link Transaction}.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class TransactionLine
extends DataLine {
    private static final EnumSet<TxnType> PRINTABLE_TYPES =
        EnumSet.of(
            TxnType.CHECK,
            TxnType.INVOICE,
            TxnType.CREDIT_MEMO,
            TxnType.CASH_SALE);

    private static final EnumSet<TxnType> RECEIVABLE_TYPES =
        EnumSet.of(
            TxnType.BILL,
            TxnType.INVOICE);

    /**
     * {@inheritDoc}
     *
     * <p>The transaction type cannot be set if values on the transaction have
     * been set via {@link #setNeedsToBePrinted(BooleanValue)},
     * {@link #setDueDate(Date)}, or {@link #setTerms(PaymentTerms)}.</p>
     *
     * @throws  IllegalStateException
     *          If the transaction has field values that prevent a change in
     *          transaction type.
     */
    @Override
    public void setType(final TxnType type) {
        if ((this.needsToBePrinted() != BooleanValue.EMPTY) ||
            (this.getDueDate()       != null) ||
            (this.getTerms()         != null)) {
            throw new IllegalStateException(
                "The transaction type cannot be changed when type-specific fields are populated.");
        }

        super.setType(type);
    }

    /**
     * Indicates whether a check, invoice, credit memo, or sales receipt has
     * been marked as "To be printed."
     *
     * <p>This value must be {@link BooleanValue#EMPTY} for all other types of
     * transactions.</p>
     */
    private BooleanValue needsToBePrinted;

    /**
     * The due date of the bill payment or invoice payment, for a bill or
     * invoice only.
     */
    private Date dueDate;

    /**
     * The payment terms, for invoices only.
     */
    private PaymentTerms terms;

    /**
     * Gets whether a check, invoice, credit memo, or sales receipt has been
     * marked as "To be printed."
     *
     * @return  {@link BooleanValue#TRUE} if it needs to be printed;
     *          or, {@link BooleanValue#FALSE} otherwise.
     */
    public BooleanValue needsToBePrinted() {
        final BooleanValue result;

        if (this.needsToBePrinted != null) {
            result = this.needsToBePrinted;
        }
        else {
            result = BooleanValue.EMPTY;
        }

        return result;
    }

    /**
     * Sets whether or not this transaction should be marked as "To be printed."
     *
     * <p>This can only be set for a check, invoice, credit memo, or sales
     * receipt transaction.</p>
     *
     * @param   needsToBePrinted
     *          The new value for whether or not this needs to be printed.
     */
    public void setNeedsToBePrinted(final BooleanValue needsToBePrinted) {
        if (!this.isPrintable()) {
            throw new IllegalArgumentException(
                "Whether or not a transaction needs printing can only be set " +
                "on a check, invoice, credit memo, or sales receipt " +
                "transaction.");
        }

        this.needsToBePrinted = needsToBePrinted;
    }

    /**
     * Gets the payment terms for an invoice transaction.
     *
     * @return  The payment terms.
     */
    public PaymentTerms getTerms() {
        return this.terms;
    }

    /**
     * Sets the payment terms for an invoice.
     *
     * <p>This can only be set for an invoice or bill transaction.</p>
     *
     * @param   terms
     *          The new terms for the invoice.
     *
     * @throws  IllegalStateException
     *          If called on anything other than an invoice transaction.
     */
    public void setTerms(final PaymentTerms terms) {
        if (!this.isReceivable()) {
            throw new IllegalArgumentException(
                "Payment terms can only be set on an invoice or bill transaction.");
        }

        this.terms = terms;
    }

    /**
     * Gets the due date for a bill or invoice transaction.
     *
     * @return  The due date.
     */
    public Date getDueDate() {
        return this.dueDate;
    }

    /**
     * Sets the due date for an invoice.
     *
     * <p>This can only be set for a bill or invoice transaction.</p>
     *
     * @param   dueDate
     *          The new due date for the bill or invoice.
     *
     * @throws  IllegalStateException
     *          If called on anything other than a bill or invoice transaction.
     */
    public void setDueDate(final Date dueDate) {
        if (!this.isReceivable()) {
            throw new IllegalArgumentException(
                "A due date can only be set on a bill or invoice transaction.");
        }

        this.dueDate = dueDate;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code "TRNS"}, always.
     */
    @Override
    public String getLineType() {
        return "TRNS";
    }

    /**
     * {@inheritDoc}
     *
     * <p>All of the columns of this line are exported into a tab-separated IIF
     * line.</p>
     *
     * @return  A representation of the data in this line, in IIF format.
     */
    @Override
    public String toIifString() {
        final IifExportable[]   columns;
        final IifExportable     dueDate,
                                terms;

        dueDate =
            this.conditionalDefault(
                this::getDueDate,
                () -> !this.isReceivable(),
                StringValue.EMPTY);

        terms =
            this.conditionalDefault(
                this::getTerms,
                () -> !this.isReceivable(),
                StringValue.EMPTY);

        columns = new IifExportable[] {
            this.getDocNumber(),        // 1
            this.getId(),               // 2
            this.getType(),             // 3
            this.getDate(),             // 4
            this.getAccount(),          // 5
            this.getName(),             // 6
            this.getTxnClass(),         // 7
            this.getAmount(),           // 8
            this.getPaymentMethod(),    // 9
            this.needsToBePrinted(),    // 10
            dueDate,                    // 11
            terms,                      // 12
            this.getMemo(),             // 13
        };

        return IifUtils.exportToString(new String[] { this.getLineType() }, columns);
    }

    /**
     * Attempts to get a value from the given getter, providing a default
     * value only if the field can be defaulted.
     *
     * <p>This is only used to provide default values for optional fields, or
     * fields that don't apply to a given transaction type.</p>
     *
     * @param   getter
     *          The getter to invoke to attempt to obtain the value.
     *
     * @param   defaultable
     *          Whether or not a default value should be provided if the field
     *          has no value.
     *
     * @param   defaultValue
     *          The default value to supply if the field is not set and it
     *          can be defaulted.
     *
     * @return  Either the underlying value; the default value; or,
     *          {@code null} if the value is unset and cannot be defaulted.
     */
    protected IifExportable conditionalDefault(final Supplier<IifExportable> getter,
                                               final Supplier<Boolean> defaultable,
                                               final IifExportable defaultValue) {
        IifExportable value = getter.get();

        if ((value == null) && defaultable.get()) {
            value = defaultValue;
        }

        return value;
    }

    /**
     * Indicates whether or not the current transaction line corresponds to a
     * type of transaction that must typically be printed (check, invoice,
     * credit memo, or receipt).
     *
     * @return  {@code true} if this transaction is a printable instrument;
     *          or, {@code false} otherwise.
     */
    protected boolean isPrintable() {
        return PRINTABLE_TYPES.contains(this.getType());
    }

    /**
     * Indicates whether or not the current transaction line corresponds to a
     * receivable (either an invoice or a vendor bill).
     *
     * @return  {@code true} if this transaction is a receivable;
     *          or, {@code false} otherwise.
     */
    protected boolean isReceivable() {
        return RECEIVABLE_TYPES.contains(this.getType());
    }
}
