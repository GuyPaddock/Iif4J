package com.redbottledesign.accounting.quickbooks.models;

/**
 * A "class" for a QuickBooks transaction.
 *
 * <p>This is similar to a category or tag for the transaction, for grouping
 * transactions together separately from the Chart of Accounts and associated
 * Vendor/Name.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class TxnClass
extends StringValue {
    /**
     * The value to use when a {@link TxnClass} is not being provided.
     */
    public static final TxnClass NULL = new TxnClass();

    /**
     * Constructor for a {@link TxnClass} to wrap the specified class.
     *
     * @param   value
     *          The class to wrap.
     */
    public TxnClass(String value) {
        super(value);
    }

    /**
     * Private constructor used to instantiate the special {@link #NULL}
     * sentinel value.
     */
    private TxnClass() {
        super("", true);
    }
}
