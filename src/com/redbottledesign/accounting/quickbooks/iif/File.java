package com.redbottledesign.accounting.quickbooks.iif;

import com.redbottledesign.accounting.quickbooks.models.Transaction;

import java.util.List;

/**
 * Top-level representation of an IIF file, which can contain zero or more
 * {@link Transaction} objects.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class File extends CompositeExportableList {
    /**
     * Adds the specified transaction to this file.
     *
     * <p>The transaction is cloned in the process. Consequently, any changes
     * made to the transaction should not have an impact on the information this
     * object exports.</p>
     *
     * @param   transaction
     *          The transaction to clone and add to this file.
     */
    public void addTransaction(final Transaction transaction) {
        this.getExportables().add(transaction.clone());
    }

    /**
     * {@inheritDoc}
     *
     * <p>A file header is automatically pre-pended to the output.</p>
     */
    @Override
    protected List<IifExportable> prepareExportables() {
        final List<IifExportable> result = super.prepareExportables();

        result.add(0, new FileHeader());

        return result;
    }
}
