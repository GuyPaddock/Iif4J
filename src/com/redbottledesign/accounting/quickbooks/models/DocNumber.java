package com.redbottledesign.accounting.quickbooks.models;

/**
 * The unique identifier for this document number.
 *
 * <p>If not specified, QuickBooks will automatically assign the document
 * number.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class DocNumber
extends StringValue {
    /**
     * The value to use when a {@link DocNumber} is not being provided.
     */
    public static final DocNumber NULL = new DocNumber();

    /**
     * Constructor for a {@link DocNumber} to wrap the specified name.
     *
     * @param   value
     *          The document number to wrap.
     */
    public DocNumber(String value) {
        super(value);
    }

    /**
     * Private constructor used to instantiate the special {@link #NULL}
     * sentinel value.
     */
    private DocNumber() {
        super("", true);
    }
}
