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
 * The terms of an invoice (i.e. when payment for the invoice is requested).
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class PaymentTerms
extends StringValue {
    /**
     * The value to use when a {@link PaymentTerms} is not being provided.
     */
    public static final PaymentTerms EMPTY = new PaymentTerms();

    /**
     * Payment due upon receipt.
     */
    public static final PaymentTerms UPON_RECEIPT = new PaymentTerms("Due on receipt");

    /**
     * 7 day terms.
     */
    public static final PaymentTerms NET_7 = new PaymentTerms("Net 7");

    /**
     * 15 day terms.
     */
    public static final PaymentTerms NET_15 = new PaymentTerms("Net 15");

    /**
     * 30 day terms.
     */
    public static final PaymentTerms NET_30 = new PaymentTerms("Net 30");

    /**
     * 60 day terms.
     */
    public static final PaymentTerms NET_60 = new PaymentTerms("Net 60");

    /**
     * Constructor for a {@code PaymentTerms} to wrap the specified name.
     *
     * @param   value
     *          The terms to wrap.
     */
    public PaymentTerms(String value) {
        super(value);
    }

    /**
     * Private constructor used to instantiate the special {@link #EMPTY}
     * sentinel value.
     */
    private PaymentTerms() {
        super("", true);
    }
}
