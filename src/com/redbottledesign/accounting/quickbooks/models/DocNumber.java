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
 * The unique identifier for this document number.
 *
 * <p>If not specified, QuickBooks will automatically assign the document
 * number.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class DocNumber
extends StringValue {
    /**
     * The value to use when a {@link DocNumber} is not being provided.
     */
    public static final DocNumber EMPTY = new DocNumber();

    /**
     * Constructor for a {@code DocNumber} to wrap the specified name.
     *
     * @param   value
     *          The document number to wrap.
     */
    public DocNumber(String value) {
        super(value);
    }

    /**
     * Private constructor used to instantiate the special {@link #EMPTY}
     * sentinel value.
     */
    private DocNumber() {
        super("", true);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The value can be no longer than 15 characters.</p>
     *
     * @throws  IllegalArgumentException
     *          If the value is longer than 15 characters.
     */
    @Override
    protected void setValue(final String value)
    throws IllegalArgumentException {
        if (value.length() > 15) {
            throw new IllegalArgumentException(
                String.format(
                    "Value cannot be longer than 15 characters (was given `%s`).", value));
        }

        super.setValue(value);
    }
}
