package com.redbottledesign.accounting.quickbooks.util;

import java.util.Arrays;

/**
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class StringUtils {
    /**
     * Private constructor to ensure this class is static.
     */
    private StringUtils() {
    }

    public static String stripQuotes(String value) {
        return value.replace("^\"|\"$", "");
    }

    public static String joinColumns(String[] columns) {
        return joinColumns(Arrays.asList(columns));
    }

    public static String joinColumns(Iterable<String> columns) {
        return String.join("\t", columns);
    }

    public static String joinLines(Iterable<String> lines) {
        return String.join("\n", lines);
    }
}

