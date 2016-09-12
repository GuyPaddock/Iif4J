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
    public Name(String name) {
        super(name);
    }
}
