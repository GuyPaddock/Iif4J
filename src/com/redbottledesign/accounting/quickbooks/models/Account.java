package com.redbottledesign.accounting.quickbooks.models;

/**
 * A representation of an account from the Chart of Accounts in QuickBooks.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class Account
extends StringValue {
    /**
     * Constructor for {@link Account} that wraps an account having the
     * specified name.
     *
     * @param   name
     *          The name of the account.
     */
    public Account(final String name) {
        super(name);
    }
}
