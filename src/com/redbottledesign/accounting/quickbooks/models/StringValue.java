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
import com.redbottledesign.accounting.quickbooks.util.IifUtils;

/**
 * Parent class for values that are represented internally as a {@link String}.
 *
 * <p>{@code null} or empty strings are not allowed. Use {@link #EMPTY} to
 * represent such as string.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class StringValue
implements IifExportable {
    /**
     * A non-null placeholder for a StringValue that represents no value.
     */
    public static final StringValue EMPTY = new StringValue("", true);

    /**
     * Whether or not {@code null} or empty values are accepted.
     *
     * The default is {@code false}; this is used internally to provide the
     * {@link #EMPTY} sentinel value.
     */
    private Boolean isNullOkay;

    /**
     * The {@link String} value inside this object.
     */
    private String value;

    /**
     * Constructor for {@code StringValue} that populates the new instance from
     * the provided {@link String}.
     *
     * @param   value
     *          The value to wrap.
     */
    public StringValue(String value) {
        this(value, false);
    }

    /**
     * Internal constructor for {@code StringValue} that permits a {@code null}
     * or empty {@link String} value.
     *
     * <p>This should only be used internally by subclasses, in order to
     * construct {@code EMPTY} sentinel values.</p>
     *
     * @param   isNullOkay
     *          Whether or not {@code value} can be {@code null} or an empty
     *          string.
     *
     * @param   value
     *          The value to wrap.
     */
    protected StringValue(String value, Boolean isNullOkay) {
        this.setNullOkay(isNullOkay);
        this.setValue(value);
    }

    /**
     * Gets whether or not the value can be {@code null} or an empty string.
     *
     * @return  {@code true} if {@code null} or an empty string is permitted;
     *          otherwise, {@code false}.
     */
    protected boolean isNullOkay() {
        return this.isNullOkay;
    }

    /**
     * Sets whether or not the value can be {@code null} or an empty string.
     *
     * @param   isNullOkay
     *          {@code true} if {@code null} or an empty string should be
     *          permitted; otherwise, {@code false}.
     */
    protected void setNullOkay(boolean isNullOkay) {
        this.isNullOkay = isNullOkay;
    }

    /**
     * Gets the value inside this object.
     *
     * @return  the value of this object.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Sets the value inside this object.
     *
     * @param   value
     *          The new value.
     */
    protected void setValue(String value) {
        if (!isNullOkay() && (value == null || value.isEmpty())) {
            throw new IllegalArgumentException(
                this.getClass().getSimpleName() + " value cannot be null or empty.");
        }

        this.value = value;
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
        return IifUtils.escapeColumn(this.getValue());
    }
}
