package com.redbottledesign.accounting.quickbooks.iif;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public abstract class CompositeExportableList
extends CompositeExportable {
    private List<IifExportable> exportables;

    protected CompositeExportableList() {
        this(new LinkedList<>());
    }

    protected CompositeExportableList(final List<IifExportable> exportables) {
        this.setExportables(exportables);
    }

    protected List<IifExportable> getExportables() {
        return this.exportables;
    }

    protected void setExportables(final List<IifExportable> exportables) {
        this.exportables = exportables;
    }

    protected List<IifExportable> prepareExportables() {
        return new LinkedList<>(this.getExportables());
    }
}
