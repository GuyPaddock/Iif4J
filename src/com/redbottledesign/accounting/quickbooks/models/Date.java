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

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Representation of a date in the format QuickBooks uses.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class Date
implements IifExportable {
    /**
     * The QuickBooks date format ({@code M/d/YYYY}).
     */
    public static final DateTimeFormatter DATE_FORMAT =
        DateTimeFormatter.ofPattern("M/d/yyyy");

    /**
     * The plain date wrapped by this object.
     */
    private LocalDate plainDate;

    /**
     * Constructor for {@code Date}, from an existing local date object.
     *
     * @param   plainDate
     *          The "plain" {@link LocalDate} to be wrapped by the new object.
     */
    public Date(LocalDate plainDate) {
        this.setPlainDate(plainDate);
    }

    /**
     * Constructor for {@code Date}, from a {@link String} in {@code M/d/YYYY}
     * format.
     *
     * @param   dateString
     *          The date to parse and wrap in thew new instance.
     */
    public Date(String dateString) throws ParseException {
        this.setPlainDate(LocalDate.parse(dateString, DATE_FORMAT));
    }

    /**
     * Gets the "plain" date this object wraps.
     *
     * @return  The date being wrapped.
     */
    public LocalDate getPlainDate() {
        return this.plainDate;
    }

    /**
     * Sets the "plain" date this object wraps.
     *
     * @param   plainDate
     *          The new date to wrap.
     */
    protected void setPlainDate(LocalDate plainDate) {
        if (plainDate == null) {
            throw new IllegalArgumentException("plainDate cannot be null");
        }

        this.plainDate = plainDate;
    }

    /**
     * {@inheritDoc}
     *
     * @return  The date, as a {@link String} returned by {@link #toString()}.
     */
    @Override
    public String toIifString() {
        return IifUtils.escapeColumn(this.toString());
    }

    /**
     * @inheritDoc
     *
     * @return The date, formatted as "M/d/yyyy"
     */
     @Override
     public String toString() {
         return DATE_FORMAT.format(this.getPlainDate());
     }
}
