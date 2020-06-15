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
 * Parent class for all QuickBook values.
 *
 * <p>{@code null} or empty values are not allowed. Use {@code EMPTY} sentinel
 * values in subclasses to represent such values.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public abstract class AbstractValue<T>
implements IifExportable {
    /**
     * Whether or not {@code null} or empty values are accepted.
     *
     * The default is {@code false}; this is used internally to provide the
     * {@code EMPTY} sentinel value.
     */
    private boolean isEmptyOkay;

    /**
     * The value inside this object.
     */
    private T value;

    /**
     * Constructor for {@code AbstractValue} that populates a new instance to
     * wrap the provided value.
     *
     * @param   value
     *          The value to wrap.
     */
    public AbstractValue(T value) {
        this(value, false);
    }

    /**
     * Internal constructor for {@code AbstractValue} that permits a
     * {@code null} or empty value.
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
    protected AbstractValue(T value, boolean isEmptyOkay) {
        this.setEmptyOkay(isEmptyOkay);
        this.setValue(value);
    }

    /**
     * Gets the value inside this object.
     *
     * @return  the value of this object.
     */
    public T getValue() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Two {@code AbstractValue} instances are equal if they are the same
     * type and wrap the same value.</p>
     */
    @Override
    public boolean equals(final Object other) {
        boolean result;

        if (this == other) {
            result = true;
        }
        else if (other.getClass().equals(this.getClass())) {
            AbstractValue<?> that = (AbstractValue<?>)other;

            result = this.getValue().equals(that.getValue());
        }
        else {
            result = false;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The hash code is based on the wrapped value's hashcode.</p>
     */
    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }

    /**
     * Gets whether or not the value can be empty.
     *
     * @return  {@code true} if {@code null} or an empty value is permitted;
     *          otherwise, {@code false}.
     */
    protected boolean isEmptyOkay() {
        return this.isEmptyOkay;
    }

    /**
     * Sets whether or not the value can be empty.
     *
     * @param   isEmptyOkay
     *          {@code true} if an empty value should be permitted;
     *          otherwise, {@code false}.
     */
    protected void setEmptyOkay(boolean isEmptyOkay) {
        this.isEmptyOkay = isEmptyOkay;
    }

    /**
     * Indicates whether or not {@code null} is allowed internally for a
     * sentinel value.
     *
     * <p>The default is {@code false}.</p>
     *
     * @return  {@code true} if {@code null} can be used internally as a
     *          sentinel value; or, {@code false}, otherwise.
     */
    protected boolean isSentinelNullOkay() {
        return false;
    }

    /**
     * Sets the value inside this object.
     *
     * @param   value
     *          The new value.
     */
    protected void setValue(T value) {
        if ((!this.isEmptyOkay() || !this.isSentinelNullOkay()) && (value == null)) {
            throw new IllegalArgumentException(
                this.getClass().getSimpleName() + " value cannot be null.");
        }
        else if (!this.isEmptyOkay() && this.isValueEmpty(value)) {
            throw new IllegalArgumentException(
                this.getClass().getSimpleName() + " value cannot be empty.");
        }

        this.value = value;
    }

    /**
     * Determines if the given value is empty.
     *
     * @param   value
     *          The value to check.
     *
     * @return  {@code true} if the given value is empty;
     *          or, {@code false} otherwise.
     */
    protected abstract boolean isValueEmpty(T value);
}
