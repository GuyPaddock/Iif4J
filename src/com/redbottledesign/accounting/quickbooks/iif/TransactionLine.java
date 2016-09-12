package com.redbottledesign.accounting.quickbooks.iif;

/**
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class TransactionLine extends DataLine {
    @Override
    public String getLineType() {
        return "TRNS";
    }
}
