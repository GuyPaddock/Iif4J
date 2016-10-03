/*
 * Copyright (C) 2016 Red Bottle Design, LLC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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

    /**
     * {@inheritDoc}
     *
     * <p>Also appends a trailing newline, to signify the end of the file.
     * (Without this, QuickBooks will not process the last transaction in
     * the file.)</p>
     */
    @Override
    public String toIifString() {
        return super.toIifString() + "\n";
    }
}
