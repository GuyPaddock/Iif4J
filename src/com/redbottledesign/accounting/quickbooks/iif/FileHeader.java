package com.redbottledesign.accounting.quickbooks.iif;

import java.util.Arrays;
import java.util.List;

/**
 * A representation of the three lines that appear at the top of IIF files.
 *
 * <p>Each header line indicates to QuickBooks which columns are included in
 * each of the following lines of the IIF file, as well as what order they
 * appear in.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class FileHeader
extends CompositeExportable {
    /**
     * {@inheritDoc}
     *
     * @return  Each of the file header lines.
     */
    protected List<IifExportable> prepareExportables() {
        List<IifExportable> result = Arrays.asList(
            new HeaderLine(HeaderLine.Type.TRANSACTION),
            new HeaderLine(HeaderLine.Type.SPLIT),
            new HeaderLine(HeaderLine.Type.TERMINATION)
        );

        return result;
    }
}
