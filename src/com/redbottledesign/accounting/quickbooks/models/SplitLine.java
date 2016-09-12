package com.redbottledesign.accounting.quickbooks.models;

/**
 * A data line that represents a distribution in a {@link Transaction} (all but
 * the first line).
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class SplitLine extends DataLine {
    /**
     * {@inheritDoc}
     *
     * @return {@code "SPL"}, always.
     */
    @Override
    public String getLineType() {
        return "SPL";
    }
}
