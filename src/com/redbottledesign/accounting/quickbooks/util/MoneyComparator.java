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
package com.redbottledesign.accounting.quickbooks.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;

/**
 * A comparator for {@link BigDecimal} values that represent money values.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class MoneyComparator
implements Comparator<BigDecimal> {
    @Override
    public int compare(final BigDecimal first, final BigDecimal second) {
        BigDecimal  moneyFirst  = applyPrecision(first),
                    moneySecond = applyPrecision(second);

        return moneyFirst.compareTo(moneySecond);
    }

    /**
     * Applies the standard US dollar precision of two decimal places, with
     * appropriate rounding {@link RoundingMode#HALF_UP}.
     *
     * <p>TODO: Support {@link RoundingMode#HALF_EVEN} at some point.</p>
     *
     * @param   value
     *          The value to round to two decimal places.
     *
     * @return  A new {@link BigDecimal} value with the appropriate precision.
     */
    public static BigDecimal applyPrecision(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Compares two {@link BigDecimal} values for equality, treating both as
     * monetary values.
     *
     * @param   first
     *          The first monetary value.
     *
     * @param   second
     *          The second monetary value.
     *
     * @return  Whether or not the two values are equal when compared as
     *          monetary values.
     */
    public static boolean areAmountsEqual(final BigDecimal first, final BigDecimal second) {
        final MoneyComparator comparator = new MoneyComparator();

        return (comparator.compare(first, second) == 0);
    }
}
