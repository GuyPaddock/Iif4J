package com.redbottledesign.accounting.quickbooks.models;

/**
 * A data line that represents the first line of a {@link Transaction}.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class TransactionLine extends DataLine {
    /**
     * {@inheritDoc}
     *
     * @return {@code "TRNS"}, always.
     */
    @Override
    public String getLineType() {
        return "TRNS";
    }
}
