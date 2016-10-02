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
    /**
     * The value to use when a {@link TxnIdentifier} is not being provided.
     */
    public static final TxnIdentifier EMPTY = new TxnIdentifier();

    /**
     * Constructor for a {@code TxnIdentifier} to wrap the specified identifier.
     *
     * @param   value
     *          The identifier to wrap.
     */
    public TxnIdentifier(String value) {
        super(value);
    }

    /**
     * Private constructor used to instantiate the special {@link #EMPTY}
     * sentinel value.
     */
    private TxnIdentifier() {
        super("", true);
    }
}
