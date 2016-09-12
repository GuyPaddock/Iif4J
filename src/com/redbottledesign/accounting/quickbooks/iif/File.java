package com.redbottledesign.accounting.quickbooks.iif;

import java.util.List;

/**
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class File extends CompositeExportableList {
    public void addTransaction(final Transaction transaction) {
        this.getExportables().add(transaction);
    }

    @Override
    protected List<IifExportable> prepareExportables() {
        final List<IifExportable> result = super.prepareExportables();

        result.add(0, new FileHeader());

        return result;
    }
}
