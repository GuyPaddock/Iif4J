package com.redbottledesign.accounting.quickbooks.iif;

import com.redbottledesign.accounting.quickbooks.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public abstract class CompositeExportable
implements IifExportable {
    protected abstract List<IifExportable> prepareExportables();

    @Override
    public String toIifString() {
        List<String> iifLines;

        iifLines = prepareExportables()
            .parallelStream()
            .map(IifExportable::toIifString)
            .collect(Collectors.toList());

        return StringUtils.joinLines(iifLines);
    }
}
