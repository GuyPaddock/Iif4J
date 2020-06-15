/*
 * Copyright (C) 2016 Red Bottle Design, LLC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
