package com.redbottledesign.accounting.quickbooks.models;

import com.redbottledesign.accounting.quickbooks.iif.IifExportable;
import com.redbottledesign.accounting.quickbooks.util.IifUtils;

/**
 * Parent class for values that are represented internally as a {@link String}.
 *
 * <p>{@code null} or empty strings are not allowed. Use {@link #NULL} to
 * represent such as string.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class StringValue
implements IifExportable {
    public static final StringValue NULL = new StringValue("", true);

    /**
     * Whether or not {@code null} or empty values are accepted.
     *
     * The default is {@code false}; this is used internally to provide the
     * {@link #NULL} sentinel value.
     */
    private Boolean isNullOkay;

    /**
     * The {@link String} value inside this object.
     */
    private String value;

    /**
     * Constructor for {@link StringValue} that populates the new instance from
     * the provided {@link String}.
     *
     * @param   value
     *          The value to wrap.
     */
    public StringValue(String value) {
        this(value, false);
    }

    /**
     * Internal constructor for {@link StringValue} that permits a {@code null}
     * or empty {@link String} value.
     *
     * <p>This should only be used internally by subclasses, in order to
     * construct {@code NULL} sentinel values.</p>
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
        if (!isNullOkay && (value == null || value.isEmpty())) {
            throw new IllegalArgumentException("value cannot be null or empty.");
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
