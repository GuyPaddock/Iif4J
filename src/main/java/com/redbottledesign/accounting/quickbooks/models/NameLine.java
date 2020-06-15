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
package com.redbottledesign.accounting.quickbooks.models;

import com.redbottledesign.accounting.quickbooks.iif.IifExportable;
import com.redbottledesign.accounting.quickbooks.util.IifUtils;

/**
 * The representation of a {@link Name} line in a QuickBooks IIF file.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class NameLine
implements IifExportable, Cloneable {
    /**
     * The type of name line ({@code CUST}, {@code VEND}, {@code OTHERNAME},
     * etc).
     */
    private final String lineType;

    /**
     * The name to export.
     */
    private final Name name;

    /**
     * Default constructor for {@code DataLine}.
     *
     * @param   lineType
     *          QuickBooks' unique code for the type of the new name line.
     *
     * @param   name
     *          The name to export.
     */
    public NameLine(final String lineType, final Name name) {
        this.lineType   = lineType;
        this.name       = name;
    }

    /**
     * Gets QuickBooks' unique code for the type of this name line.
     *
     * TODO: Make this an enum instead?
     *
     * @return  Either "CUST", "VEND", or "OTHERNAME".
     */
    public String getLineType() {
        return this.lineType;
    }

    /**
     * Gets the name that this line is wrapping.
     *
     * @return  The name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Creates a shallow copy of this {@code NameLine}.
     *
     * @return  A new {@code NameLine}, having all of the same field values and
     *          references as this instance.
     */
    @Override
    public NameLine clone() {
        try {
            return (NameLine)super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>All of the columns of this line are exported into a tab-separated IIF
     * line.</p>
     *
     * @return  A representation of the data in this line, in IIF format.
     */
    @Override
    public String toIifString() {
        IifExportable[] columns = new IifExportable[] { this.getName() };

        return IifUtils.exportToString(new String[] { this.getLineType() }, columns);
    }
}
