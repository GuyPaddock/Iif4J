package com.redbottledesign.accounting.quickbooks.util;

import java.math.BigDecimal;
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
     * appropriate rounding {@link BigDecimal#ROUND_HALF_UP}.
     *
     * <p>TODO: Support {@link BigDecimal#ROUND_HALF_EVEN} at some point.</p>
     *
     * @param   value
     *          The value to round to two decimal places.
     *
     * @return  A new {@link BigDecimal} value with the appropriate precision.
     */
    public static BigDecimal applyPrecision(BigDecimal value) {
        return value.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
