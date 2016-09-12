package com.redbottledesign.accounting.quickbooks.iif;

/**
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class SplitLine extends DataLine {
    @Override
    public String getLineType() {
        return "SPL";
    }
}
