package com.redbottledesign.accounting.quickbooks.builders;

import com.redbottledesign.accounting.quickbooks.models.*;
import com.redbottledesign.util.Argument;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * A builder for Payment Deposit entries.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class PaymentDepositBuilder
extends AbstractTransactionBuilder {
    /**
     * The type of transaction this builder constructs.
     */
    public static final TxnType TRANSACTION_TYPE = TxnType.DEPOSIT;

    private Account depositTo;
    private Date date;
    private Memo memo;
    private Account cashBackAccount;
    private Memo cashBackMemo;
    private Amount cashBackAmount;

    private BigDecimal paymentTotal;
    private List<SplitLine> paymentLines;

    /**
     * Constructor for {@code PaymentDepositBuilder}.
     */
    public PaymentDepositBuilder() {
        this.paymentTotal = BigDecimal.ZERO;
        this.paymentLines = new LinkedList<>();
    }

    /**
     * Gets the account into which the deposit should be recorded.
     *
     * @return  The deposit account.
     */
    public Account getDepositTo() {
        return this.depositTo;
    }

    /**
     * Sets the bank account into which the deposit should be recorded.
     *
     * @param   depositTo
     *          The deposit account to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public PaymentDepositBuilder setDepositTo(final Account depositTo) {
        this.depositTo = depositTo;

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
     * Sets the effective date of the deposit.
     *
     * <p>This populates the date of the IIF transaction.</p>
     *
     * @param   date
     *          The date to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public PaymentDepositBuilder setDate(final Date date) {
        this.date = date;

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
     * Sets a miscellaneous note on the top level of the deposit.
     *
     * <p>QuickBooks also supports a memo on each individual payment in this
     * deposit.</p>
     *
     * @param   memo
     *          The memo to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public PaymentDepositBuilder setMemo(final Memo memo) {
        this.memo = memo;

        return this;
    }

    /**
     * Gets the account into which a remaining cash back amount should be
     * deposited.
     *
     * @return The cash back account.
     */
    public Account getCashBackAccount() {
        return this.cashBackAccount;
    }

    /**
     * Sets the account into which a remaining cash back amount should be
     * deposited.
     *
     * <p>This value is optional, but can only be specified if the cash back
     * amount is a non-zero amount.</p>
     *
     * <p>This value is only required if the cash back amount is a non-zero
     * amount.</p>
     *
     * @param   cashBackAccount
     *          The cash back account to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public PaymentDepositBuilder setCashBackAccount(final Account cashBackAccount) {
        this.cashBackAccount = cashBackAccount;

        return this;
    }

    /**
     * Gets the memo that is being recorded on the cash back part of the
     * transaction.
     *
     * @return The cash back memo.
     */
    public Memo getCashBackMemo() {
        return this.cashBackMemo;
    }

    /**
     * Sets the memo that is being recorded on the cash back part of the
     * transaction.
     *
     * <p>This value is optional, but can only be specified if the cash
     * back account is set and the cash back amount is a non-zero amount.</p>
     *
     * @param   cashBackMemo
     *          The cash back memo to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public PaymentDepositBuilder setCashBackMemo(final Memo cashBackMemo) {
        this.cashBackMemo = cashBackMemo;

        return this;
    }

    /**
     * Gets the amount of the deposit that's being considered cash back.
     *
     * @return The CashBackAmount.
     */
    public Amount getCashBackAmount() {
        return this.cashBackAmount;
    }

    /**
     * Sets the amount of the deposit that's being considered cash back.
     *
     * <p>This value is optional, but must be specified if the cash back
     * account has been specified.</p>
     *
     * @param   cashBackAmount
     *          The cash back amount to set in the transaction being built.
     *
     * @return  This object, for chaining.
     */
    public PaymentDepositBuilder setCashBackAmount(final Amount cashBackAmount) {
        this.cashBackAmount = cashBackAmount;

        return this;
    }

    /**
     * Indicates whether or not the deposit has been set to have cash back.
     *
     * @return  {@code true} if the deposit has a cash back amount;
     *          or, {@code false}, otherwise.
     */
    public boolean hasCashBack() {
        return (this.getCashBackAccount() != null);
    }

    /**
     * Gets the total amount of all payments added on this builder.
     *
     * @return The payment total.
     */
    public BigDecimal getPaymentTotal() {
        return this.paymentTotal;
    }

    /**
     * Gets the payment lines that have been created for the transaction.
     *
     * @return The list of payment split lines in the deposit.
     */
    protected List<SplitLine> getPaymentLines() {
        return this.paymentLines;
    }

    /**
     * Adds a new payment to the deposit transaction being built.
     *
     * <p>The amount must be a positive number.</p>
     *
     * @param   receivedFrom
     *          The customer, vendor, contractor, etc. to associate with the
     *          payment.
     *
     * @param   fromAccount
     *          The account that the payment affects.
     *
     * @param   memo
     *          A note to add to the payment.
     *
     * @param   checkNumber
     *          An optional check number to associate with the payment.
     *
     * @param   paymentMethod
     *          The method by which the payment was made.
     *
     * @param   txnClass
     *          An optional transaction class for the payment.
     *
     * @param   amount
     *          The amount of money being exchanged.
     *
     * @return  This object, for chaining.
     */
    public PaymentDepositBuilder addPayment(final Name receivedFrom, final Account fromAccount,
                                            final Memo memo, final DocNumber checkNumber,
                                            final PaymentMethod paymentMethod, final TxnClass txnClass,
                                            final Amount amount) {
        SplitLine paymentSplit = new SplitLine();

        Argument.ensureNotNull(receivedFrom,    "receivedFrom");
        Argument.ensureNotNull(fromAccount,     "fromAccount");
        Argument.ensureNotNull(memo,            "memo");
        Argument.ensureNotNull(checkNumber,     "checkNumber");
        Argument.ensureNotNull(paymentMethod,   "paymentMethod");
        Argument.ensureNotNull(txnClass,        "txnClass");
        Argument.ensureNotNull(amount,          "amount");

        if (amount.getValue().signum() < 0) {
            throw new IllegalArgumentException("Payment amount cannot be negative.");
        }

        paymentSplit.setName(receivedFrom);
        paymentSplit.setAccount(fromAccount);
        paymentSplit.setMemo(memo);
        paymentSplit.setDocNumber(checkNumber);
        paymentSplit.setPaymentMethod(paymentMethod);
        paymentSplit.setTxnClass(txnClass);
        paymentSplit.setAmount(new Amount(amount.getValue().negate()));

        this.getPaymentLines().add(paymentSplit);

        this.paymentTotal = this.paymentTotal.add(amount.getValue());

        return this;
    }

    /**
     * Builds the transaction, using the parameters set on this builder
     * instance.
     *
     * <p>All of the fields of this instance (e.g. deposit account, date,
     * payments, etc.) must be set before calling this method.</p>
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
        Transaction     transaction  = new Transaction();
        List<DataLine>  depositLines = new LinkedList<>();

        this.ensureReadyToBuild();

        this.addDepositLine(depositLines);
        this.addCashBackLine(depositLines);

        depositLines.addAll(this.getPaymentLines());

        for (DataLine line : depositLines) {
            line.setType(TRANSACTION_TYPE);
            line.setDate(date);

            transaction.addLine(line);
        }

        transaction.ensureIsInBalance();

        return transaction;
    }

    /**
     * Adds the line to the deposit transaction that actually puts the sum of
     * the payments, minus any cash back amount, into an account.
     *
     * @param   depositLines
     *          The existing lines in the deposit transaction, to which the
     *          deposit line will be added.
     */
    protected void addDepositLine(List<DataLine> depositLines) {
        final BigDecimal paymentTotal  = this.getPaymentTotal(),
                         depositTotal;

        if (this.hasCashBack()) {
            final Amount cashBackAmount = this.getCashBackAmount();

            depositTotal = paymentTotal.subtract(cashBackAmount.getValue());
        }
        else {
            depositTotal = paymentTotal;
        }

        this.addLine(
            depositLines,
            this.getDepositTo(),
            new Amount(depositTotal),
            Name.EMPTY,
            this.getMemo());
    }

    /**
     * Adds a line to the deposit transaction for the cash back amount, if
     * cash back is expected.
     *
     * @param   depositLines
     *          The existing lines in the deposit transaction, to which the
     *          cash back line will be added.
     */
    protected void addCashBackLine(List<DataLine> depositLines) {
        Amount  cashBackAmount  = this.getCashBackAmount();

        if (this.hasCashBack()) {
            Account cashBackAccount = this.getCashBackAccount();
            Memo    cashBackMemo    = this.getCashBackMemo();

            this.addLine(
                depositLines,
                cashBackAccount,
                cashBackAmount,
                Name.EMPTY,
                cashBackMemo);
        }
    }

    /**
     * Ensures that this builder has all of the context it needs to construct a
     * proper payment.
     *
     * @throws  IllegalArgumentException
     *          If any arguments are missing or incorrect.
     */
    protected void ensureReadyToBuild()
    throws IllegalArgumentException {
        Account depositTo       = this.getDepositTo();
        Date    date            = this.getDate();
        Memo    memo            = this.getMemo();
        Account cashBackAccount = this.getCashBackAccount();
        Memo    cashBackMemo    = this.getCashBackMemo();
        Amount  cashBackAmount  = this.getCashBackAmount();

        Argument.ensureNotNull(depositTo, "depositTo");
        Argument.ensureNotNull(date,      "date");
        Argument.ensureNotNull(memo,      "memo");

        if (cashBackMemo == null) {
            Argument.ensureAllOrNoneNull(
                "If either cashbackAccount or cashbackAmount have a value, both must have a value.",
                cashBackAccount,
                cashBackAmount);
        }
        else {
            Argument.ensureAllOrNoneNull(
                "When cashbackMemo, cashbackAccount, or cashbackAmount have a value, all must " +
                "have a value.",
                cashBackAccount,
                cashBackAmount,
                cashBackMemo);
        }
    }
}
