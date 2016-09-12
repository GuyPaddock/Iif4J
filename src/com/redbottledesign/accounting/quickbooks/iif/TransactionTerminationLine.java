package com.redbottledesign.accounting.quickbooks.iif;

/**
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class TransactionTerminationLine
implements IifExportable {
    @Override
    public String toIifString() {
        String[] columns = {
            "ENDTRNS",
            ""
        };

        return String.join("\t", columns);
    }
}
