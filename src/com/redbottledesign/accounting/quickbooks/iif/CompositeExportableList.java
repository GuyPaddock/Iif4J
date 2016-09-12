package com.redbottledesign.accounting.quickbooks.iif;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A {@link CompositeExportable} that maintains a built-in list of
 * {@link IifExportable} objects.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public abstract class CompositeExportableList
extends CompositeExportable {
    /**
     * The list of {@link IifExportable} objects in this composite object.
     */
    private List<IifExportable> exportables;

    /**
     * Default constructor for {@link CompositeExportableList}.
     */
    protected CompositeExportableList() {
        this(Collections.emptyList());
    }

    /**
     * Constructor for {@link CompositeExportableList} that initializes the
     * new composite to contain the provided list of
     * {@link IifExportable} objects.
     *
     * <p>The contents of the list are copied into a new list.</p>
     *
     * @param   exportables
     *          The objects to populate this list with.
     */
    protected CompositeExportableList(final List<IifExportable> exportables) {
        this.setExportables(exportables);
    }

    /**
     * Gets the {@link IifExportable} objects inside this composite.
     *
     * @return  The list of exportables.
     */
    protected List<IifExportable> getExportables() {
        return this.exportables;
    }

    /**
     * Sets the list of {@link IifExportable} objects inside this composite.
     *
     * <p>The contents of the list are copied into a new list.</p>
     *
     * @param   exportables
     *          The objects to populate this list with.
     */
    protected void setExportables(final List<IifExportable> exportables) {
        this.exportables = new LinkedList<>(exportables);
    }

    /**
     * {@inheritDoc}
     */
    protected List<IifExportable> prepareExportables() {
        return new LinkedList<>(this.getExportables());
    }
}
