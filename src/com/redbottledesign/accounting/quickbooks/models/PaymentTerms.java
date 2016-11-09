package com.redbottledesign.accounting.quickbooks.models;

/**
 * The terms of an invoice (i.e. when payment for the invoice is requested).
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class PaymentTerms
extends StringValue {
    /**
     * The value to use when a {@link PaymentTerms} is not being provided.
     */
    public static final PaymentTerms EMPTY = new PaymentTerms();

    /**
     * Payment due upon receipt.
     */
    public static final PaymentTerms ON_RECEIPT = new PaymentTerms("Due on receipt");

    /**
     * 7 day terms.
     */
    public static final PaymentTerms NET_7 = new PaymentTerms("Net 7");

    /**
     * 15 day terms.
     */
    public static final PaymentTerms NET_15 = new PaymentTerms("Net 15");

    /**
     * 30 day terms.
     */
    public static final PaymentTerms NET_30 = new PaymentTerms("Net 30");

    /**
     * 60 day terms.
     */
    public static final PaymentTerms NET_60 = new PaymentTerms("Net 60");

    /**
     * Constructor for a {@code PaymentTerms} to wrap the specified name.
     *
     * @param   value
     *          The terms to wrap.
     */
    public PaymentTerms(String value) {
        super(value);
    }

    /**
     * Private constructor used to instantiate the special {@link #EMPTY}
     * sentinel value.
     */
    private PaymentTerms() {
        super("", true);
    }
}
