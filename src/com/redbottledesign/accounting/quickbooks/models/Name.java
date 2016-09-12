package com.redbottledesign.accounting.quickbooks.models;

/**
 * A generic name for a person or entity in a transaction.
 *
 * <p>This is more general than a Vendor or Account.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class Name
extends StringValue {
    /**
     * The value to use when a {@link Name} is not being provided.
     */
    public static final Name NULL = new Name();

    /**
     * Constructor for a {@link Name} to wrap the specified name.
     *
     * @param   value
     *          The name to wrap.
     */
    public Name(String value) {
        super(value);
    }

    /**
     * Private constructor used to instantiate the special {@link #NULL}
     * sentinel value.
     */
    private Name() {
        super("", true);
    }
}
