package com.redbottledesign.accounting.quickbooks.models;

/**
 * The unique identifier for this transaction.
 *
 * <p>If not specified, QuickBooks will automatically assign the transaction
 * number.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class TxnIdentifier
extends StringValue {
    /**
     * The value to use when a {@link TxnIdentifier} is not being provided.
     */
    public static final TxnIdentifier NULL = new TxnIdentifier();

    /**
     * Constructor for a {@link TxnIdentifier} to wrap the specified identifier.
     *
     * @param   value
     *          The identifier to wrap.
     */
    public TxnIdentifier(String value) {
        super(value);
    }

    /**
     * Private constructor used to instantiate the special {@link #NULL}
     * sentinel value.
     */
    private TxnIdentifier() {
        super("", true);
    }
}
