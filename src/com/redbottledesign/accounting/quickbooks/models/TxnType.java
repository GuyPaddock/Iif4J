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
    private Type type;

    public TxnType(final Type type) {
        this.setType(type);
    }

    public Type getType() {
        return this.type;
    }

    protected void setType(final Type type) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }

        this.type = type;
    }

    @Override
    public String toIifString() {
        return IifUtils.escapeColumn(this.getType().getQbIdentifier());
    }

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

        private final String qbIdentifier;

        Type(String qbIdentifer) {
            this.qbIdentifier = qbIdentifer;
        }

        public String getQbIdentifier() {
            return this.qbIdentifier;
        }
    }
}
