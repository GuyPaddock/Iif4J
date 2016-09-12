package com.redbottledesign.accounting.quickbooks.models;

import com.redbottledesign.accounting.quickbooks.iif.IifExportable;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * A representation of an value in a transaction.
 *
 * Amounts are limited to two decimal places, in USD.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class Amount
implements IifExportable {
    private BigDecimal value;

    public Amount(final double value) {
        this.setValue(BigDecimal.valueOf(value));
    }

    public Amount(final BigDecimal value) {
        this.setValue(value);
    }

    public BigDecimal getValue() {
        return this.value;
    }

    protected void setValue(final BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }

        this.value = value;
    }

    @Override
    public String toIifString() {
        return this.toString();
    }

    @Override
    public String toString() {
        final NumberFormat formatter = NumberFormat.getInstance();

        formatter.setGroupingUsed(false);
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);

        return formatter.format(this.getValue());
    }
}
