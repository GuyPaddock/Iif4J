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
import com.redbottledesign.accounting.quickbooks.models.Transaction;
import com.redbottledesign.accounting.quickbooks.models.TxnClass;
import com.redbottledesign.accounting.quickbooks.models.TxnType;
import com.redbottledesign.util.Argument;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * A builder for General Journal entries.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class GeneralJournalBuilder
extends AbstractTransactionBuilder {
    /**
     * The type of transaction this builder constructs.
     */
    public static final TxnType TRANSACTION_TYPE = TxnType.GENERAL_JOURNAL;

    private Date date;
    private DocNumber entryNumber;
    private List<DataLine> journalLines;

    /**
     * Constructor for {@code GeneralJournalBuilder}
     */
    public GeneralJournalBuilder() {
        this.journalLines = new LinkedList<>();
    }

    /**
     * Gets the effective date of the journal entry.
     *
     * @return  The date.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Sets the effective date of the journal entry.
     *
     * <p>This populates the date of the IIF transaction.</p>
     *
     * @param   date
     *          The date to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public GeneralJournalBuilder setDate(final Date date) {
        this.date = date;

        return this;
    }

    /**
     * Gets the unique entry number for the journal entry.
     *
     * @return  The entry number.
     */
    public DocNumber getEntryNumber() {
        return this.entryNumber;
    }

    /**
     * Sets the unique entry number for the journal entry.
     *
     * <p>This populates the document number of the IIF transaction.</p>
     *
     * @param   entryNumber
     *          The entry number to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public GeneralJournalBuilder setEntryNumber(final DocNumber entryNumber) {
        this.entryNumber = entryNumber;

        return this;
    }

    /**
     * Gets the transaction lines that have been created for the transaction.
     *
     * @return The list of transaction lines.
     */
    protected List<DataLine> getJournalLines() {
        return this.journalLines;
    }

    /**
     * Adds a new line to the transaction being built.
     *
     * <p>By convention, the amount is negative for a credit and positive for a
     * debit.</p>
     *
     * @param   account
     *          The name of the account that the line affects.
     *
     * @param   amount
     *          The amount of money being exchanged.
     *
     * @return  This object, for chaining.
     */
    public GeneralJournalBuilder addLine(final String account, final BigDecimal amount) {
        return this.addLine(
            new Account(account),
            new Amount(amount),
            Name.EMPTY,
            Memo.EMPTY,
            TxnClass.EMPTY);
    }

    /**
     * Adds a new line to the transaction being built.
     *
     * <p>By convention, the amount is negative for a credit and positive for a
     * debit.</p>
     *
     * @param   account
     *          The name of the account that the line affects.
     *
     * @param   amount
     *          The amount of money being exchanged.

     * @param   name
     *          The name (customer, vendor, contractor, etc.) to associate
     *          with the line.
     *
     * @return  This object, for chaining.
     */
    public GeneralJournalBuilder addLine(final String account, final BigDecimal amount,
                                         final String name) {
        return this.addLine(
            new Account(account),
            new Amount(amount),
            new Name(name),
            Memo.EMPTY,
            TxnClass.EMPTY);
    }

    /**
     * Adds a new line to the transaction being built.
     *
     * <p>By convention, the amount is negative for a credit and positive for a
     * debit.</p>
     *
     * @param   account
     *          The name of the account that the line affects.
     *
     * @param   amount
     *          The amount of money being exchanged.
     *
     * @param   name
     *          The name (customer, vendor, contractor, etc.) to associate
     *          with the line.
     *
     * @param   memo
     *          A note to add to the line.
     *
     * @return  This object, for chaining.
     */
    public GeneralJournalBuilder addLine(final String account, final BigDecimal amount,
                                         final String name, final String memo) {
        return this.addLine(
            new Account(account),
            new Amount(amount),
            new Name(name),
            new Memo(memo),
            TxnClass.EMPTY);
    }

    /**
     * Adds a new line to the transaction being built.
     *
     * <p>By convention, the amount is negative for a credit and positive for a
     * debit.</p>
     *
     * @param   account
     *          The name of the account that the line affects.
     *
     * @param   amount
     *          The amount of money being exchanged.
     *
     * @param   name
     *          The name (customer, vendor, contractor, etc.) to associate
     *          with the line.
     *
     * @param   memo
     *          A note to add to the line.
     *
     * @param   txnClass
     *          The transaction class.
     *
     * @return  This object, for chaining.
     */
    public GeneralJournalBuilder addLine(final String account, final BigDecimal amount,
                                         final String name, final String memo, final String txnClass) {
        return this.addLine(
            new Account(account),
            new Amount(amount),
            new Name(name),
            new Memo(memo),
            new TxnClass(txnClass));
    }

    /**
     * Adds a new line to the transaction being built.
     *
     * <p>By convention, the amount is negative for a credit and positive for a
     * debit.</p>
     *
     * @param   account
     *          The name of the account that the line affects.
     *
     * @param   amount
     *          The amount of money being exchanged.
     *
     * @return  This object, for chaining.
     */
    public GeneralJournalBuilder addLine(final Account account, final Amount amount) {
        return this.addLine(account, amount, Name.EMPTY, Memo.EMPTY, TxnClass.EMPTY);
    }

    /**
     * Adds a new line to the transaction being built.
     *
     * <p>By convention, the amount is negative for a credit and positive for a
     * debit.</p>
     *
     * @param   account
     *          The name of the account that the line affects.
     *
     * @param   amount
     *          The amount of money being exchanged.
     *
     * @param   name
     *          The name (customer, vendor, contractor, etc.) to associate
     *          with the line.
     *
     * @return  This object, for chaining.
     */
    public GeneralJournalBuilder addLine(final Account account, final Amount amount,
                                         final Name name) {
        return this.addLine(account, amount, name, Memo.EMPTY, TxnClass.EMPTY);
    }

    /**
     * Adds a new line to the transaction being built.
     *
     * <p>By convention, the amount is negative for a credit and positive for a
     * debit.</p>
     *
     * @param   account
     *          The name of the account that the line affects.
     *
     * @param   amount
     *          The amount of money being exchanged.
     *
     * @param   name
     *          The name (customer, vendor, contractor, etc.) to associate
     *          with the line.
     *
     * @param   memo
     *          A note to add to the line.
     *
     * @return  This object, for chaining.
     */
    public GeneralJournalBuilder addLine(final Account account, final Amount amount,
                                         final Name name, final Memo memo) {
        return this.addLine(account, amount, name, memo, TxnClass.EMPTY);
    }

    /**
     * Adds a new line to the transaction being built.
     *
     * <p>By convention, the amount is negative for a credit and positive for a
     * debit.</p>
     *
     * @param   account
     *          The name of the account that the line affects.
     *
     * @param   amount
     *          The amount of money being exchanged.
     *
     * @param   name
     *          The name (customer, vendor, contractor, etc.) to associate
     *          with the line.
     *
     * @param   memo
     *          A note to add to the line.
     *
     * @param   txnClass
     *          The transaction class.
     *
     * @return  This object, for chaining.
     */
    public GeneralJournalBuilder addLine(final Account account, final Amount amount,
                                         final Name name, final Memo memo, final TxnClass txnClass) {
        this.addLine(this.getJournalLines(), account, amount, name, memo, txnClass);

        return this;
    }

    /**
     * Builds the transaction, using the parameters set on this builder
     * instance.
     *
     * <p>The date must be set and the transaction must be in balance before
     * calling this method. If you wish to generate a transaction even
     * if it is not in balance, use {@link #build(boolean)} instead.</p>
     *
     * <p>This can be called multiple times to generate identical
     * transactions.</p>
     *
     * @see #setDate(Date)
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
        return this.build(true);
    }

    /**
     * Builds the transaction, using the parameters set on this builder
     * instance.
     *
     * <p>The date must be set before calling this method. If
     * {@code mustBalance} is {@code true}, the transaction must also be in
     * balance.</p>
     *
     * <p>This can be called multiple times to generate identical
     * transactions.</p>
     *
     * @see #setDate(Date)
     *
     * @param   mustBalance
     *          Whether the transaction must be strictly in balance in order
     *          to be exported.
     *
     * @return  The fully constructed transaction.
     *
     * @throws  IllegalArgumentException
     *          If any required field has not yet been set.
     *
     * @throws  IllegalStateException
     *          If {@code mustBalance} is {@code true} and the transaction is
     *          not in balance.
     */
    public Transaction build(boolean mustBalance)
    throws IllegalArgumentException, IllegalStateException {
        Transaction transaction = new Transaction();
        DocNumber   entryNumber = this.getEntryNumber();
        Date        date        = this.getDate();

        Argument.ensureNotNull(date, "date");

        for (DataLine line : this.getJournalLines()) {
            line.setType(TRANSACTION_TYPE);
            line.setDate(date);

            if (entryNumber != null) {
                line.setDocNumber(entryNumber);
            }

            transaction.addLine(line);
        }

        if (mustBalance) {
            transaction.ensureIsInBalance();
        }

        return transaction;
    }


}
