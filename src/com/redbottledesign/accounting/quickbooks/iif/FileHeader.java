package com.redbottledesign.accounting.quickbooks.iif;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class FileHeader
implements IifExportable {
    protected List<IifExportable> getLines() {
        List<IifExportable> result = Arrays.asList(
            new HeaderLine(HeaderLine.Type.TRANSACTION),
            new HeaderLine(HeaderLine.Type.SPLIT),
            new HeaderLine(HeaderLine.Type.TERMINATION)
        );

        return result;
    }

    @Override
    public String toIifString() {
        List<String> iifLines;

        iifLines = this.getLines()
            .parallelStream()
            .map(IifExportable::toIifString)
            .collect(Collectors.toList());

        return String.join("\n", iifLines);
    }
}
