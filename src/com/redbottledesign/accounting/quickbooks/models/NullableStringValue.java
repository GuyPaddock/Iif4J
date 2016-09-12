package com.redbottledesign.accounting.quickbooks.models;

/**
 * The unique identifier for this transaction.
 *
 * <p>If not specified, QuickBooks will automatically assign the transaction
 * number.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class NullableStringValue
extends StringValue {
    public NullableStringValue(String name) {
        super(name);
    }

    protected abstract static class Null extends NullableStringValue {
        public Null() {
            super("");
        }

        public String getValue() {
            return "";
        }

        protected void setValue(String value) {
            // No-op
        }
    }
}
