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
    public static final DocNumber NULL = new DocNumber.Null();

    public DocNumber(String name) {
        super(name);
    }

    protected static class Null extends DocNumber {
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
