/**
 * Copyright (C) 2016  Red Bottle Design, LLC
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
 * Represents the "memo" for a transaction, which can provide notes and other
 * information about the transaction.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class Memo
extends StringValue {
    /**
     * The value to use when a {@link Memo} is not being provided.
     */
    public static final Memo NULL = new Memo();

    /**
     * Constructor for a {@link Memo} to wrap the specified text.
     *
     * @param   value
     *          The memo text to wrap.
     */
    public Memo(String value) {
        super(value);
    }

    /**
     * Private constructor used to instantiate the special {@link #NULL}
     * sentinel value.
     */
    private Memo() {
        super("", true);
    }
}
