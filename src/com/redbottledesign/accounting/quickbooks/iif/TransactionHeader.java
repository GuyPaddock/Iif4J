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

import java.util.Arrays;
import java.util.List;

/**
 * A representation of the three lines that appear at the top of a transaction
 * table in an IIF file.
 *
 * <p>Each header line indicates to QuickBooks which columns are included in
 * each of the following lines of the IIF file, as well as what order they
 * appear in.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class TransactionHeader
extends CompositeExportable {
    /**
     * {@inheritDoc}
     *
     * @return  Each of the file header lines.
     */
    protected List<IifExportable> prepareExportables() {
        List<IifExportable> result = Arrays.asList(
            new HeaderLine(HeaderLine.Type.TRANSACTION),
            new HeaderLine(HeaderLine.Type.TRANSACTION_SPLIT),
            new HeaderLine(HeaderLine.Type.TRANSACTION_TERMINATION)
        );

        return result;
    }
}
