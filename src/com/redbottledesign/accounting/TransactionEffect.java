package com.redbottledesign.accounting;

/**
 * The effect that a particular transaction has on the current ledger.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public enum TransactionEffect {
    /**
     * A transaction type that transfers money from another account into the
     * ledger.
     */
    DEBIT(1),

    /**
     * A transaction type that has no effect on the ledger.
     */
    NONE(0),

    /**
     * A transaction type that transfers money to another account from the
     * ledger.
     */
    CREDIT(-1);

    private final int signNumber;

    /**
     * Enumeration constructor for {@link TransactionEffect}.
     *
     * @param   signNumber
     *          The sign of the transaction type.
     */
    TransactionEffect(int signNumber) {
        this.signNumber = signNumber;
    }

    /**
     * Gets a number that represents the "sign" of this type of transaction.
     *
     * @return   0 if the transaction has no effect on the ledger;
     *           1 if the transaction adds money to the ledger;
     *          -1 if the argument subtracts money from the ledger.
     */
    public int getSignNumber() {
        return this.signNumber;
    }
}
