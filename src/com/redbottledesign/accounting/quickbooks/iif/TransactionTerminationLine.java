package com.redbottledesign.accounting.quickbooks.iif;

/**
 * Representation of the IIF line that indicates the end of one transaction and
 * the start of the next one.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class TransactionTerminationLine
implements IifExportable {
    /**
     * {@inheritDoc}
     */
    @Override
    public String toIifString() {
        String[] columns = { "ENDTRNS", "" };

        return String.join("\t", columns);
    }
}
