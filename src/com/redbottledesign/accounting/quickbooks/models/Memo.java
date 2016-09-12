package com.redbottledesign.accounting.quickbooks.models;

/**
 * Represents the "memo" for a transaction, which can provide notes and other
 * information about the transaction.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class Memo
extends StringValue {
    /**
     * The value to use when a {@link Memo} is not being provided.
     */
    public static final Memo NULL = new Memo();

    /**
     * Constructor for a {@link Memo} to wrap the specified text.
     *
     * @param   value
     *          The memo text to wrap.
     */
    public Memo(String value) {
        super(value);
    }

    /**
     * Private constructor used to instantiate the special {@link #NULL}
     * sentinel value.
     */
    private Memo() {
        super("", true);
    }
}
