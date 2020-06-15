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
 * A method of paying for a transaction.
 *
 * <p>For example, "Cash", "Check", "Wire Transfer", "ACH Transfer", etc.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class PaymentMethod
extends StringValue {
    /**
     * The value to use when a {@link PaymentMethod} is not being provided.
     */
    public static final PaymentMethod EMPTY = new PaymentMethod();

    /**
     * Default QB payment method for cash payments.
     */
    public static final PaymentMethod CASH = new PaymentMethod("Cash");

    /**
     * Default QB payment method for payments by check.
     */
    public static final PaymentMethod CHECK = new PaymentMethod("Check");

    /**
     * Default QB payment method for electronic check payments.
     */
    public static final PaymentMethod E_CHECK = new PaymentMethod("E-Check");

    /**
     * Default QB payment method for payments by Automated Clearing House bank
     * transfers.
     */
    public static final PaymentMethod ACH_TRANSFER = new PaymentMethod("ACH Transfer");

    /**
     * Default QB payment method for payments by bank wire transfer.
     */
    public static final PaymentMethod WIRE_TRANSFER = new PaymentMethod("Wire Transfer");

    /**
     * Default QB payment method for payments by American Express card.
     */
    public static final PaymentMethod AMEX = new PaymentMethod("American Express");

    /**
     * Default QB payment method for payments by Discover card.
     */
    public static final PaymentMethod DISCOVER = new PaymentMethod("Discover");

    /**
     * Default QB payment method for payments by MasterCard.
     */
    public static final PaymentMethod MASTERCARD = new PaymentMethod("MasterCard");

    /**
     * Default QB payment method for payments by Visa card.
     */
    public static final PaymentMethod VISA = new PaymentMethod("Visa");

    /**
     * Default QB payment method for payments by debit card.
     */
    public static final PaymentMethod DEBIT_CARD = new PaymentMethod("Debit Card");

    /**
     * Default QB payment method for payments by gift card.
     */
    public static final PaymentMethod GIFT_CARD = new PaymentMethod("Gift Card");

    /**
     * Constructor for a {@code PaymentMethod} to wrap the specified payment method.
     *
     * @param   value
     *          The payment method to wrap.
     */
    public PaymentMethod(String value) {
        super(value);
    }

    /**
     * Private constructor used to instantiate the special {@link #EMPTY}
     * sentinel value.
     */
    private PaymentMethod() {
        super("", true);
    }
}
