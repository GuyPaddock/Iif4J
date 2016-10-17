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
package com.redbottledesign.accounting.quickbooks.builders;

import com.redbottledesign.accounting.quickbooks.models.Account;
import com.redbottledesign.accounting.quickbooks.models.Amount;
import com.redbottledesign.accounting.quickbooks.models.DataLine;
import com.redbottledesign.accounting.quickbooks.models.Date;
import com.redbottledesign.accounting.quickbooks.models.DocNumber;
import com.redbottledesign.accounting.quickbooks.models.Memo;
import com.redbottledesign.accounting.quickbooks.models.Name;
import com.redbottledesign.accounting.quickbooks.models.PaymentMethod;
import com.redbottledesign.accounting.quickbooks.models.Transaction;
import com.redbottledesign.accounting.quickbooks.models.TxnType;
import com.redbottledesign.util.Argument;

import java.util.LinkedList;
import java.util.List;

/**
 * A builder for Customer Payment entries.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class CustomerPaymentBuilder
extends AbstractTransactionBuilder {
    /**
     * The type of transaction this builder constructs.
     */
    public static final TxnType TRANSACTION_TYPE = TxnType.PAYMENT;

    /**
     * The account that represents accounts receivable in QuickBooks.
     */
    public static final Account ACCOUNTS_RECEIVABLE = new Account("Accounts Receivable");

    private Name customer;
    private Amount amount;
    private Date date;
    private PaymentMethod paymentMethod;
    private DocNumber referenceNumber;
    private Memo memo;
    private Account depositTo;

    /**
     * Sets the customer from which the payment is being received.
     *
     * @param   customer
     *          The customer to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public CustomerPaymentBuilder setCustomer(final Name customer) {
        this.customer = customer;

        return this;
    }

    /**
     * Sets the amount of the payment.
     *
     * <p>The amount should be a positive number.</p>
     *
     * @param   amount
     *          The payment amount to set in the transaction being built.
     */
    public CustomerPaymentBuilder setAmount(final Amount amount) {
        if (amount.getValue().signum() < 0) {
            throw new IllegalArgumentException("amount must be a positive number.");
        }

        this.amount = amount;

        return this;
    }

    /**
     * Sets the effective date of the payment.
     *
     * <p>This populates the date of the IIF transaction.</p>
     *
     * @param   date
     *          The date to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public CustomerPaymentBuilder setDate(final Date date) {
        this.date = date;

        return this;
    }

    /**
     * Sets the method by which the payment was made.
     *
     * @param   paymentMethod
     *          The payment method to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public CustomerPaymentBuilder setPaymentMethod(final PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;

        return this;
    }

    /**
     * Sets the unique reference # for this payment.
     *
     * <p>This populates the document number of the IIF transaction.</p>
     *
     * @param   entryNumber
     *          The reference # to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public CustomerPaymentBuilder setReferenceNumber(final DocNumber entryNumber) {
        this.referenceNumber = entryNumber;

        return this;
    }

    /**
     * Sets a miscellaneous note on the payment.
     *
     * @param   memo
     *          The memo to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public CustomerPaymentBuilder setMemo(final Memo memo) {
        this.memo = memo;

        return this;
    }

    /**
     * Sets the account into which the payment will be deposited ("Undeposited
     * Funds" tends to be popular for this).
     *
     * @param   depositTo
     *          The deposit account to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public CustomerPaymentBuilder setDepositTo(final Account depositTo) {
        this.depositTo = depositTo;

        return this;
    }

    /**
     * Gets the customer from which the payment is being received.
     *
     * @return  The customer.
     */
    public Name getCustomer() {
        return this.customer;
    }

    /**
     * Gets the amount of the payment.
     *
     * @return  The payment amount.
     */
    public Amount getAmount() {
        return this.amount;
    }

    /**
     * Gets the effective date of the payment.
     *
     * @return  The date.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Gets the method by which the payment was made.
     *
     * @return  The payment method.
     */
    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    /**
     * Gets the unique reference # for this payment.
     *
     * @return  The reference #.
     */
    public DocNumber getReferenceNumber() {
        return this.referenceNumber;
    }

    /**
     * Gets the miscellaneous note on the payment.
     *
     * @return  The memo.
     */
    public Memo getMemo() {
        return this.memo;
    }

    /**
     * Gets the account into which the payment will be deposited.
     *
     * @return  The deposit account.
     */
    public Account getDepositTo() {
        return this.depositTo;
    }

    /**
     * Builds the transaction, using the parameters set on this builder
     * instance.
     *
     * <p>All of the fields of this instance (e.g. customer, amount,
     * date, etc.) must be set before calling this method.</p>
     *
     * <p>This can be called multiple times to generate identical
     * transactions.</p>
     *
     * @return  The fully constructed transaction.
     *
     * @throws  IllegalArgumentException
     *          If any required field has not yet been set.
     *
     * @throws  IllegalStateException
     *          If the transaction is not in balance.
     */
    public Transaction build()
    throws IllegalArgumentException, IllegalStateException {
        Transaction     transaction     = new Transaction();
        List<DataLine>  paymentLines    = new LinkedList<>();
        Name            customer        = this.getCustomer();
        Amount          debitAmount     = this.getAmount(),
                        creditAmount;
        Date            date            = this.getDate();
        PaymentMethod   paymentMethod   = this.getPaymentMethod();
        DocNumber       referenceNumber = this.getReferenceNumber();
        Memo            memo            = this.getMemo();
        Account         depositTo       = this.getDepositTo();

        Argument.ensureNotNull(customer,        "customer");
        Argument.ensureNotNull(debitAmount,     "debitAmount");
        Argument.ensureNotNull(date,            "date");
        Argument.ensureNotNull(paymentMethod,   "paymentMethod");
        Argument.ensureNotNull(referenceNumber, "referenceNumber");
        Argument.ensureNotNull(memo,            "memo");
        Argument.ensureNotNull(depositTo,       "depositTo");

        // Debit to the deposit account
        this.addLine(paymentLines, depositTo, debitAmount, customer, memo);

        // Credit from AR
        creditAmount = new Amount(amount.getValue().negate());

        this.addLine(paymentLines, ACCOUNTS_RECEIVABLE, creditAmount, customer, memo);

        for (DataLine line : paymentLines) {
            line.setType(TRANSACTION_TYPE);
            line.setDate(date);
            line.setDocNumber(referenceNumber);
            line.setPaymentMethod(paymentMethod);

            transaction.addLine(line);
        }

        transaction.ensureIsInBalance();

        return transaction;
    }
}
