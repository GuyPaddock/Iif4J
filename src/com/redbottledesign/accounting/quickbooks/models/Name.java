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
 * A generic name for a person or entity in a transaction.
 *
 * <p>This is more general than a Vendor or Account.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class Name
extends StringValue {
    /**
     * The value to use when a {@link Name} is not being provided.
     */
    public static final Name NULL = new Name();

    /**
     * Constructor for a {@link Name} to wrap the specified name.
     *
     * @param   value
     *          The name to wrap.
     */
    public Name(String value) {
        super(value);
    }

    /**
     * Private constructor used to instantiate the special {@link #NULL}
     * sentinel value.
     */
    private Name() {
        super("", true);
    }
}
