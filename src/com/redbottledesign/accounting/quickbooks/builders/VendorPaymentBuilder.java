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
import com.redbottledesign.accounting.quickbooks.models.BooleanValue;
import com.redbottledesign.accounting.quickbooks.models.DataLine;
import com.redbottledesign.accounting.quickbooks.models.Date;
import com.redbottledesign.accounting.quickbooks.models.DocNumber;
import com.redbottledesign.accounting.quickbooks.models.Memo;
import com.redbottledesign.accounting.quickbooks.models.Name;
import com.redbottledesign.accounting.quickbooks.models.Transaction;
import com.redbottledesign.accounting.quickbooks.models.TransactionLine;
import com.redbottledesign.accounting.quickbooks.models.TxnType;
import com.redbottledesign.util.Argument;

import java.util.LinkedList;
import java.util.List;

/**
 * A builder for check payments toward Vendor Bills.
 *
 * <p><strong>NOTE:</strong> There is currently no way to automatically
 * associate the bill payment with the original bill. Vendor bill payments have
 * to be imported as checks written against Accounts Payable to a Vendor. This
 * is a limitation of QuickBooks 2006 and later for which there is no known
 * workaround.</p>
 *
 * <p>Use "Pay Bills" within QuickBooks to associate the payments as "Credits"
 * against the outstanding bills.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class VendorPaymentBuilder
extends AbstractTransactionBuilder {
    /**
     * The type of transaction this builder constructs.
     */
    public static final TxnType TRANSACTION_TYPE = TxnType.CHECK;

    private Name vendor;
    private Amount amount;
    private DocNumber referenceNumber;
    private Date date;
    private Account chargeToAccount;
    private Memo memo;

    /**
     * Constructor for {@code VendorPaymentBuilder}.
     */
    public VendorPaymentBuilder() {
        this.setReferenceNumber(DocNumber.EMPTY);
        this.setMemo(Memo.EMPTY);
    }

    /**
     * Gets the vendor to which the payment is being made.
     *
     * @return  The customer.
     */
    public Name getVendor() {
        return this.vendor;
    }

    /**
     * Sets the vendor to which the payment is being made.
     *
     * @param   vendor
     *          The vendor to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public VendorPaymentBuilder setVendor(final Name vendor) {
        this.vendor = vendor;

        return this;
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
     * Sets the amount of the payment.
     *
     * <p>The amount should be a positive number.</p>
     *
     * @param   amount
     *          The payment amount to set in the transaction being built.
     */
    public VendorPaymentBuilder setAmount(final Amount amount) {
        if (amount.getValue().signum() < 0) {
            throw new IllegalArgumentException("amount must be a positive number.");
        }

        this.amount = amount;

        return this;
    }

    /**
     * Gets the unique reference # for the bill payment.
     *
     * @return  The reference #.
     */
    public DocNumber getReferenceNumber() {
        return this.referenceNumber;
    }

    /**
     * Sets the unique reference # for the bill payment.
     *
     * <p>This populates the document number of the IIF transaction.</p>
     *
     * @param   referenceNumber
     *          The reference # to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public VendorPaymentBuilder setReferenceNumber(final DocNumber referenceNumber) {
        this.referenceNumber = referenceNumber;

        return this;
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
     * Sets the effective date of the payment.
     *
     * <p>This populates the date of the IIF transaction.</p>
     *
     * @param   date
     *          The date to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public VendorPaymentBuilder setDate(final Date date) {
        this.date = date;

        return this;
    }

    /**
     * Gets the account from which the payment will be credited.
     *
     * @return  The charge account.
     */
    public Account getChargeToAccount() {
        return this.chargeToAccount;
    }

    /**
     * Sets the account from which the payment will be credited (usually,
     * a checking account or credit card account).
     *
     * @param   chargeToAccount
     *          The charge account to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public VendorPaymentBuilder setChargeToAccount(final Account chargeToAccount) {
        this.chargeToAccount = chargeToAccount;

        return this;
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
     * Sets a miscellaneous note on the payment.
     *
     * @param   memo
     *          The memo to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public VendorPaymentBuilder setMemo(final Memo memo) {
        this.memo = memo;

        return this;
    }

    /**
     * Builds the transaction, using the parameters set on this builder
     * instance.
     *
     * <p>All of the fields of this instance (e.g. vendor, amount, date, etc.)
     * must be set before calling this method.</p>
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
        final Transaction       transaction     = new Transaction();
        final List<DataLine>    paymentLines    = new LinkedList<>();
        final Name              vendor          = this.getVendor();
        final Amount            debitAmount     = this.getAmount(),
                                creditAmount    = new Amount(debitAmount.getValue().negate());
        final DocNumber         refNumber       = this.getReferenceNumber();
        final Date              date            = this.getDate();
        final Account           chargeTo        = this.getChargeToAccount();
        final Memo              memo            = this.getMemo();
        final TransactionLine   txnLine;

        Argument.ensureNotNull(vendor,      "customer");
        Argument.ensureNotNull(debitAmount, "debitAmount");
        Argument.ensureNotNull(refNumber,   "refNumber");
        Argument.ensureNotNull(date,        "date");
        Argument.ensureNotNull(chargeTo,    "chargeTo");
        Argument.ensureNotNull(memo,        "memo");

        // Credit from Charge Account
        txnLine =
            (TransactionLine)this.addLine(
                paymentLines, chargeTo, creditAmount, vendor, Memo.EMPTY);

        txnLine.setType(TRANSACTION_TYPE);

        if (refNumber.getValue().isEmpty()) {
            txnLine.setNeedsToBePrinted(BooleanValue.TRUE);
        }
        else {
            txnLine.setNeedsToBePrinted(BooleanValue.FALSE);
        }

        // Debit to Accounts Payable for the Vendor
        this.addLine(paymentLines, Account.ACCOUNTS_PAYABLE, debitAmount, vendor, Memo.EMPTY);

        for (DataLine line : paymentLines) {
            line.setType(TRANSACTION_TYPE);
            line.setDate(date);
            line.setDocNumber(refNumber);
            line.setMemo(memo);

            transaction.addLine(line);
        }

        transaction.ensureIsInBalance();

        return transaction;
    }
}
