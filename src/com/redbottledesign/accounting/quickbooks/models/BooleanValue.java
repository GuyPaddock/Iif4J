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

import com.redbottledesign.accounting.quickbooks.iif.IifExportable;

/**
 * Parent class for values that are represented internally as a {@link Boolean}.
 *
 * <p>{@code null} or empty strings are not allowed. Use {@link #EMPTY} to
 * represent such as string.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class BooleanValue
extends AbstractValue<Boolean>
implements IifExportable {
    /**
     * A non-null placeholder for a BooleanValue that represents no value.
     */
    public static final BooleanValue EMPTY = new BooleanValue(null, true);

    /**
     * A {@code BooleanValue} that represents true values.
     */
    public static final BooleanValue TRUE = new BooleanValue(true, true);

    /**
     * A {@code BooleanValue} that represents false values.
     */
    public static final BooleanValue FALSE = new BooleanValue(false, true);

    /**
     * Constructor for {@code BooleanValue} that populates the new instance from
     * the provided {@link Boolean}.
     *
     * @param   value
     *          The value to wrap.
     */
    protected BooleanValue(Boolean value) {
        super(value, false);
    }

    /**
     * Internal constructor for {@code BooleanValue} that permits a {@code null}
     * or empty {@link Boolean} value.
     *
     * <p>This should only be used internally by subclasses, in order to
     * construct {@code EMPTY} sentinel values.</p>
     *
     * @param   isEmptyOkay
     *          Whether or not {@code value} can be {@code null} or an empty
     *          string.
     *
     * @param   value
     *          The value to wrap.
     */
    protected BooleanValue(Boolean value, boolean isEmptyOkay) {
        super(value, isEmptyOkay);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The string value is automatically escaped and wrapped in quotes.</p>
     *
     * @return  The value of this object, in a format acceptable for IIF.
     */
    @Override
    public String toIifString() {
        String  result;
        Boolean value = this.getValue();

        if (value == null) {
            result = "";
        }
        else if (value) {
            result = "Y";
        }
        else {
            result = "N";
        }

        return result;
    }

    @Override
    protected boolean isValueEmpty(final Boolean value) {
        return (value == null);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Booleans allow a sentinel value of {@code null}.</p>
     */
    @Override
    protected boolean isSentinelNullOkay() {
        return true;
    }

}
