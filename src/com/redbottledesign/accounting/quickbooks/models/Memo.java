package com.redbottledesign.accounting.quickbooks.models;

/**
 * Represents the "memo" for a transaction, which can provide notes and other
 * information about the transaction.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class Memo
extends StringValue {
    public static final Memo NULL = new Memo.Null();

    public Memo(String value) {
        super(value);
    }

    protected static class Null extends Memo {
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
