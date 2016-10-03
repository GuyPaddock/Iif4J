package com.redbottledesign.accounting.quickbooks.builders;

import com.redbottledesign.accounting.quickbooks.models.*;
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
        this.addLine(
            this.getJournalLines(),
            account,
            amount,
            name,
            memo,
            txnClass);

        return this;
    }

    /**
     * Gets the effective date of the journal entry.
     *
     * @return  The date.
     */
    protected Date getDate() {
        return this.date;
    }

    /**
     * Gets the unique entry number for the journal entry.
     *
     * @return  The entry number.
     */
    protected DocNumber getEntryNumber() {
        return this.entryNumber;
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
     * Builds the transaction, using the parameters set on this builder
     * instance.
     *
     * <p>Both the date and entry number must be set before calling this
     * method.</p>
     *
     * <p>This can be called multiple times to generate identical
     * transactions.</p>
     *
     * @see #setDate(Date)
     * @see #setEntryNumber(DocNumber)
     *
     * @return  The fully constructed transaction.
     *
     * @throws  IllegalArgumentException
     *          If either the date and entry number values have not yet been
     *          set.
     */
    public Transaction build()
    throws IllegalArgumentException {
        Transaction transaction = new Transaction();
        DocNumber   entryNumber = this.getEntryNumber();
        Date        date        = this.getDate();

        Argument.ensureNotNull(date,        "date");
        Argument.ensureNotNull(entryNumber, "entryNumber");

        for (DataLine line : this.getJournalLines()) {
            line.setType(TRANSACTION_TYPE);
            line.setDate(date);
            line.setDocNumber(entryNumber);

            transaction.addLine(line);
        }

        return transaction;
    }
}
