/**
 * Copyright (C) 2016  Red Bottle Design, LLC
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

import com.redbottledesign.accounting.quickbooks.util.IifUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * An abstract parent class for {@link IifExportable} objects that wrap other
 * {@code IifExportable} objects.
 *
 * <p>For example, a composite exportable would include:</p>
 * <ul>
 *   <li>an IIF file, which wraps several transactions</li>
 *   <li>a transaction, which wraps various transaction lines</li>
 *   <li>a transaction line, which wraps various columns</li>
 * </ul>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public abstract class CompositeExportable
implements IifExportable {
    /**
     * Prepares and returned a list of {@link IifExportable} instances for
     * export.
     *
     * <p>The exportables are typically generated on-the-fly by subclasses,
     * and can include additional information / lines, above and beyond just the
     * exportables being wrapped by this object.</p>
     *
     * @return A list of {@code IifExportable} objects, ready for export.
     */
    protected abstract List<IifExportable> prepareExportables();

    /**
     * {@inheritDoc}
     *
     * @return IIF output of each exportable, as a newline-separated String.
     */
    @Override
    public String toIifString() {
        List<String> iifLines;

        iifLines = prepareExportables()
            .parallelStream()
            .map(IifExportable::toIifString)
            .collect(Collectors.toList());

        return IifUtils.joinLines(iifLines);
    }
}
