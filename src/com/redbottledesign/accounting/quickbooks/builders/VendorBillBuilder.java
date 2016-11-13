package com.redbottledesign.accounting.quickbooks.builders;

import com.redbottledesign.accounting.quickbooks.models.Account;
import com.redbottledesign.accounting.quickbooks.models.Amount;
import com.redbottledesign.accounting.quickbooks.models.DataLine;
import com.redbottledesign.accounting.quickbooks.models.Date;
import com.redbottledesign.accounting.quickbooks.models.DocNumber;
import com.redbottledesign.accounting.quickbooks.models.Memo;
import com.redbottledesign.accounting.quickbooks.models.Name;
import com.redbottledesign.accounting.quickbooks.models.PaymentTerms;
import com.redbottledesign.accounting.quickbooks.models.SplitLine;
import com.redbottledesign.accounting.quickbooks.models.Transaction;
import com.redbottledesign.accounting.quickbooks.models.TransactionLine;
import com.redbottledesign.accounting.quickbooks.models.TxnClass;
import com.redbottledesign.accounting.quickbooks.models.TxnType;
import com.redbottledesign.util.Argument;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A builder for transactions that represent bills from vendors.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class VendorBillBuilder {
    /**
     * The type of transaction this builder constructs.
     */
    public static final TxnType TRANSACTION_TYPE = TxnType.BILL;

    private Date date;
    private DocNumber referenceNumber;
    private BigDecimal lineItemTotal;

    private TransactionLine transactionLine;
    private List<SplitLine> splitLines;

    /**
     * Constructor for {@code VendorBillBuilder}.
     */
    public VendorBillBuilder() {
        this.setLineItemTotal(BigDecimal.ZERO);

        this.setTransactionLine(this.createTransactionLine());
        this.setSplitLines(Collections.emptyList());
    }

    /**
     * Gets the vendor from which the bill is being received.
     *
     * @return  The vendor.
     */
    public Name getVendor() {
        return this.getTransactionLine().getName();
    }

    /**
     * Sets the vendor from which the bill is being received.
     *
     * @param   vendorName
     *          The vendor to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public VendorBillBuilder setVendor(final Name vendorName) {
        this.getTransactionLine().setName(vendorName);

        return this;
    }

    /**
     * Gets the date the bill was received.
     *
     * @return  The date.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Sets the date the bill was received.
     *
     * <p>This populates the date of the IIF transaction.</p>
     *
     * @param   date
     *          The date to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public VendorBillBuilder setDate(final Date date) {
        this.date = date;

        return this;
    }

    /**
     * Gets the unique reference # for the bill.
     *
     * @return  The reference #.
     */
    public DocNumber getReferenceNumber() {
        return this.referenceNumber;
    }

    /**
     * Sets the unique reference # for the bill.
     *
     * <p>This populates the document number of the IIF transaction.</p>
     *
     * @param   referenceNumber
     *          The reference # to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public VendorBillBuilder setReferenceNumber(final DocNumber referenceNumber) {
        this.referenceNumber = referenceNumber;

        return this;
    }

    /**
     * Gets the due date for the bill.
     *
     * @return  The due date.
     */
    public Date getDueDate() {
        return this.getTransactionLine().getDueDate();
    }

    /**
     * Sets the due date for the bill.
     *
     * @param   dueDate
     *          The new due date for the bill.
     */
    public VendorBillBuilder setDueDate(final Date dueDate) {
        this.getTransactionLine().setDueDate(dueDate);

        return this;
    }

    /**
     * Gets the payment terms for the bill.
     *
     * @return  The payment terms.
     */
    public PaymentTerms getTerms() {
        return this.getTransactionLine().getTerms();
    }

    /**
     * Sets the payment terms for the bill.
     *
     * @param   terms
     *          The new terms for the invoice.
     */
    public VendorBillBuilder setTerms(final PaymentTerms terms) {
        this.getTransactionLine().setTerms(terms);

        return this;
    }

    /**
     * Gets the miscellaneous note on the bill.
     *
     * @return  The memo.
     */
    public Memo getMemo() {
        return this.getTransactionLine().getMemo();
    }

    /**
     * Sets a miscellaneous note on the bill.
     *
     * @param   memo
     *          The memo to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public VendorBillBuilder setMemo(final Memo memo) {
        this.getTransactionLine().setMemo(memo);

        return this;
    }

    /**
     * Gets the first line of the transaction.
     *
     * @return  The transaction line.
     */
    protected TransactionLine getTransactionLine() {
        return this.transactionLine;
    }

    /**
     * Sets the first line of the transaction.
     *
     * @param   transactionLine
     *          The new transaction line.
     *
     * @return  This object, for chaining.
     */
    protected VendorBillBuilder setTransactionLine(final TransactionLine transactionLine) {
        this.transactionLine = transactionLine;

        return this;
    }

    /**
     * Gets all of the lines that represent the line items of the bill.
     *
     * @return  The split lines in the transaction.
     */
    protected List<SplitLine> getSplitLines() {
        return this.splitLines;
    }

    /**
     * Sets the lines that represent the line items of the bill.
     *
     * @param   splitLines
     *          The new line items in the bill.
     *
     * @return  This object, for chaining.
     */
    protected VendorBillBuilder setSplitLines(final List<SplitLine> splitLines) {
        this.splitLines = new LinkedList<>(splitLines);

        return this;
    }

    /**
     * Gets the total amount of all line items added on this builder.
     *
     * <p>This amount is equal to the amount in the first transaction line.</p>
     *
     * @return  The line item total amount.
     */
    protected BigDecimal getLineItemTotal() {
        return this.lineItemTotal;
    }

    /**
     * Sets the total amount of all line items on this builder.
     *
     * @param   lineItemTotal
     *          The new line item total.
     */
    protected VendorBillBuilder setLineItemTotal(final BigDecimal lineItemTotal) {
        this.lineItemTotal = lineItemTotal;

        return this;
    }

    /**
     * Adds a new line item to the vendor bill transaction being built.
     *
     * <p>The amount can be either a positive number (for an amount owed to the
     * vendor) or a negative number (for a credit from the vendor).</p>
     *
     * @param   account
     *          The expense account that the new line item affects.
     *
     * @param   amount
     *          The amount of the line item.
     *
     * @param   customerOrJob
     *          The customer (if any) who will be reimbursing the expense of
     *          this line.
     *
     * @param   memo
     *          A note to add to the payment.
     *
     * @param   txnClass
     *          An optional transaction class for the line item.
     *
     * @return  This object, for chaining.
     */
    public VendorBillBuilder addLineItem(final Account account, final Amount amount,
                                         final Name customerOrJob, final Memo memo,
                                         final TxnClass txnClass) {
        SplitLine paymentSplit = new SplitLine();

        Argument.ensureNotNull(account,         "account");
        Argument.ensureNotNull(amount,          "amount");
        Argument.ensureNotNull(customerOrJob,   "customerOrJob");
        Argument.ensureNotNull(memo,            "memo");
        Argument.ensureNotNull(txnClass,        "txnClass");

        paymentSplit.setType(TRANSACTION_TYPE);
        paymentSplit.setAccount(account);
        paymentSplit.setAmount(new Amount(amount.getValue()));
        paymentSplit.setName(customerOrJob);
        paymentSplit.setMemo(memo);
        paymentSplit.setTxnClass(txnClass);

        this.getSplitLines().add(paymentSplit);

        this.lineItemTotal = this.lineItemTotal.add(amount.getValue());

        return this;
    }

    /**
     * Builds the transaction, using the parameters set on this builder
     * instance.
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
        TransactionLine transactionLine = this.getTransactionLine();
        List<DataLine>  lines           = new LinkedList<>();

        transactionLine.setMemo(this.getMemo());
        transactionLine.setAmount(new Amount(this.getLineItemTotal().negate()));

        lines.add(this.getTransactionLine());
        lines.addAll(this.getSplitLines());

        for (DataLine line : lines) {
            line.setDocNumber(this.getReferenceNumber());
            line.setDate(this.getDate());

            transaction.addLine(line);
        }

        transaction.ensureIsInBalance();

        return transaction;
    }

    /**
     * Constructs the transaction line, which is the first line of the bill
     * when exported to IIF.
     *
     * <p>The transaction line identifies that this is a bill that is credited
     * from Accounts Payable.</p>
     *
     * @return  The first line of the transaction.
     */
    protected TransactionLine createTransactionLine() {
        TransactionLine line = new TransactionLine();

        line.setType(TxnType.BILL);
        line.setAccount(Account.ACCOUNTS_PAYABLE);

        return line;
    }
}
