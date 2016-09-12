package com.redbottledesign.accounting.quickbooks.util;

import com.redbottledesign.accounting.quickbooks.iif.IifExportable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class IifUtils {
    /**
     * Private constructor to ensure this class is static.
     */
    private IifUtils() {
    }

    public static String escapeColumn(final String value) {
        final String strippedString = StringUtils.stripQuotes(value),
                     escapedString  = strippedString.replaceAll("\"", "\\\"");

        return '"' + escapedString + '"';
    }

    public static String exportToString(final IifExportable[] columns) {
        return exportToString(new String[] {}, columns);
    }

    public static String exportToString(final String[] prefixColumns, final IifExportable[] columns) {
        return exportToString(prefixColumns, columns, new String[] {});
    }

    public static String exportToString(final String[] prefixColumns, final IifExportable[] columns, final String[] suffixColumns) {
        final List<String> line = new LinkedList<>();

        if (columns == null) {
            throw new IllegalArgumentException("columns must not be null");
        }

        if (prefixColumns == null) {
            throw new IllegalArgumentException("prefixColumns must not be null");
        }

        if (suffixColumns == null) {
            throw new IllegalArgumentException("suffixColumns must not be null");
        }

        line.addAll(Arrays.asList(prefixColumns));

        for (IifExportable column : columns) {
            line.add(column.toIifString());
        }

        line.addAll(Arrays.asList(suffixColumns));

        return StringUtils.joinColumns(line);
    }
}
