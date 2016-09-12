package com.redbottledesign.accounting.quickbooks.models;

import com.redbottledesign.accounting.quickbooks.iif.IifExportable;
import com.redbottledesign.accounting.quickbooks.util.IifUtils;

/**
 * Abstract parent class for values that are represented internally as
 * a {@link String}.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public abstract class StringValue
implements IifExportable {
    private String value;

    public StringValue(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return this.value;
    }

    protected void setValue(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("value cannot be null or empty.");
        }

        this.value = value;
    }

    @Override
    public String toIifString() {
        return IifUtils.escapeColumn(this.getValue());
    }
}
