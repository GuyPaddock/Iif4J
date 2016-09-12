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
    public static final TxnIdentifier NULL = new TxnIdentifier.Null();

    public TxnIdentifier(String name) {
        super(name);
    }

    protected static class Null extends TxnIdentifier {
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
