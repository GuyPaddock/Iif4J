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
    public static final DateTimeFormatter DATE_FORMAT =
        DateTimeFormatter.ofPattern("M/d/yyyy");

    private LocalDate plainDate;

    public Date(LocalDate plainDate) {
        this.setPlainDate(plainDate);
    }

    public Date(String dateString) throws ParseException {
        this.setPlainDate(LocalDate.parse(dateString, DATE_FORMAT));
    }

    public LocalDate getPlainDate() {
        return this.plainDate;
    }

    public void setPlainDate(LocalDate plainDate) {
        if (plainDate == null) {
            throw new IllegalArgumentException("plainDate cannot be null");
        }

        this.plainDate = plainDate;
    }

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
