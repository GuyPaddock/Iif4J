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

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * A representation of a monetary value in a transaction.
 *
 * Amounts are limited to two decimal places, in USD.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class Amount
implements IifExportable {
    /**
     * The value of this amount.
     */
    private BigDecimal value;

    /**
     * Constructor for {@code Amount}.
     *
     * @param   value
     *          The value of the new amount, as a {@link Double}.
     */
    public Amount(final double value) {
        this.setValue(BigDecimal.valueOf(value));
    }

    /**
     * Constructor for {@code Amount}.
     *
     * @param   value
     *          The value of the new amount, as a {@link BigDecimal}.
     */
    public Amount(final BigDecimal value) {
        this.setValue(value);
    }

    /**
     * Gets the value of this amount.
     *
     * @return  The value of this amount.
     */
    public BigDecimal getValue() {
        return this.value;
    }

    /**
     * Sets the value of the new amount, as a {@link BigDecimal}.
     *
     * @param   value
     *          The new value.
     */
    protected void setValue(final BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }

        this.value = value;
    }

    /**
     * {@inheritDoc}
     *
     * @return  The dollar value of this amount, as a {@link String} returned by
     *          {@link #toString()}.
     */
    @Override
    public String toIifString() {
        return this.toString();
    }

    /**
     * Gets this amount as a string, in format "XXXX.XX" (two decimal places, no
     * grouping characters).
     *
     * @return The value of this amount as a {@link String}.
     */
    @Override
    public String toString() {
        final NumberFormat formatter = NumberFormat.getInstance();

        formatter.setGroupingUsed(false);
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);

        return formatter.format(this.getValue());
    }
}
