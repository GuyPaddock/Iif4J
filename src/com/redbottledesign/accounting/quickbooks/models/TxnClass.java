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
    public static final TxnClass NULL = new TxnClass.Null();

    public TxnClass(String name) {
        super(name);
    }

    protected static class Null extends TxnClass {
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
