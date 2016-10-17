package com.redbottledesign.accounting.quickbooks.exception;

/**
 * A special type of {@link IllegalStateException} that is thrown to signal
 * when a particular transaction is out-of-balance (i.e. the amount of
 * credits does not equal the amount of debits).
 *
 * <p>Transactions must be in balance in order to be exported to IIF.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class OutOfBalanceException
extends IllegalStateException {
    /**
     * {@inheritDoc}
     */
    public OutOfBalanceException() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public OutOfBalanceException(final String s) {
        super(s);
    }

    /**
     * {@inheritDoc}
     */
    public OutOfBalanceException(final String s, final Throwable throwable) {
        super(s, throwable);
    }

    /**
     * {@inheritDoc}
     */
    public OutOfBalanceException(final Throwable throwable) {
        super(throwable);
    }
}
