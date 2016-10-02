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
 * The type of transaction.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class TxnType
implements IifExportable {
    /**
     * The type of the transaction, wrapped in this object.
     */
    private Type value;

    /**
     * Constructor for {@code TxnType}.
     *
     * @param   value
     *          The transaction type being wrapped.
     */
    public TxnType(final Type value) {
        this.setValue(value);
    }

    /**
     * Gets the type of transaction.
     *
     * @return  The transaction type.
     */
    public Type getValue() {
        return this.value;
    }

    /**
     * Sets the type of transaction.
     *
     * @param   type
     *          The new transaction type.
     */
    protected void setValue(final Type type) {
        if (type == null) {
            throw new IllegalArgumentException("value cannot be null");
        }

        this.value = type;
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
        return IifUtils.escapeColumn(this.getValue().getQbIdentifier());
    }

    /**
     * An enumeration of the types of transaction types that QuickBooks
     * supports.
     */
    public enum Type {
        /**
         * Transactions that create a beginning balance in a balance sheet
         * account.
         */
        BEGIN_BALANCE_CHECK("BEGINBALCHECK"),

        /**
         * Bills from vendors.
         */
        BILL("BILL"),

        /**
         * Refunds from a vendor.
         */
        BILL_REFUND("BILL REFUND"),

        /**
         * Cash refunds you give to customers.
         */
        CASH_REFUND("CASH REFUND"),

        /**
         * Sales receipts.
         */
        CASH_SALE("CASH SALE"),

        /**
         * Refunds you receive on credit card charges.
         */
        CREDIT_CARD_REFUND("CCARD REFUND"),

        /**
         * Checks.
         */
        CHECK("CHECK"),

        /**
         * Charges you make on a credit card.
         */
        CREDIT_CARD("CREDIT CARD"),

        /**
         * Credit you give to customers for merchandise they return.
         */
        CREDIT_MEMO("CREDIT MEMO"),

        /**
         * Bank or money market deposits.
         */
        DEPOSIT("DEPOSIT"),

        /**
         * Estimates or bids.
         */
        ESTIMATE("ESTIMATES"),

        /**
         * General journal transactions.
         */
        GENERAL_JOURNAL("GENERAL JOURNAL"),

        /**
         * Invoices.
         */
        INVOICE("INVOICE"),

        /**
         * Customer payments.
         */
        PAYMENT("PAYMENT"),

        /**
         * Purchase orders.
         */
        PURCHASE_ORDER("PURCHORD"),

        /**
         * Transfers of funds from one balance sheet account to another.
         */
        TRANSFER("TRANSFER");

        /**
         * The internal identifier for this option in QuickBooks.
         */
        private final String qbIdentifier;

        /**
         * Private, enum constrcutor for @{link Type}
         *
         * @param   qbIdentifier
         *          The identifier for this type in QuickBooks.
         */
        Type(String qbIdentifier) {
            this.qbIdentifier = qbIdentifier;
        }

        /**
         * Gets the unique identifier for this type of transaction.
         *
         * @return  String
         *          The QuickBooks code for this transaction type.
         */
        public String getQbIdentifier() {
            return this.qbIdentifier;
        }
    }
}
