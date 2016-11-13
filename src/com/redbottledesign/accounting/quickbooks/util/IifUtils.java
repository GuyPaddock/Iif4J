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
package com.redbottledesign.accounting.quickbooks.util;

import com.redbottledesign.accounting.quickbooks.iif.IifExportable;
import com.redbottledesign.accounting.quickbooks.models.Transaction;
import com.redbottledesign.util.Argument;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Common IIF-related String utility methods.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class IifUtils {
    /**
     * Private constructor to ensure this class is static.
     */
    private IifUtils() {
    }

    /**
     * Removes any double quotes at the beginning or end of the provided value,
     * escapes any interior double quotes, and then wraps the string in double
     * quotes.
     *
     * @param   value
     *          The value to escape.
     *
     * @return  The escaped column value.
     */
    public static String escapeColumn(final String value) {
        final String strippedString = stripQuotes(value),
                     escapedString  = strippedString.replaceAll("\"", "\\\"");

        return '"' + escapedString + '"';
    }

    /**
     * Exports the provided IIF-exportable columns into a tab-separated string.
     *
     * @param   columns
     *          The columns to export into a string.
     *
     * @return  The columns as a tab-separated string.
     */
    public static String exportToString(final IifExportable[] columns) {
        return exportToString(new String[] {}, columns);
    }

    /**
     * Exports the provided IIF-exportable columns into a tab-separated string,
     * prefixed by the provided plain-text String columns.
     *
     * <p>The {@code prefixColumns} are prepended as-is, without any additional
     * processing.</p>
     *
     * @param   prefixColumns
     *          The columns to prepend to the string. Each will be
     *          tab-separated.
     *
     * @param   columns
     *          The columns to export into a string.
     *
     * @return  The columns as a tab-separated string.
     */
    public static String exportToString(final String[] prefixColumns, final IifExportable[] columns) {
        return exportToString(prefixColumns, columns, new String[] {});
    }

    /**
     * Exports the provided IIF-exportable columns into a tab-separated string,
     * prefixed and suffixed by the provided plain-text String columns.
     *
     * <p>The {@code prefixColumns} are prepended as-is, without any additional
     * processing.</p>
     *
     * <p>In addition, the {@code suffixColumns} are also appended as-is,
     * without any additional processing.</p>
     *
     * @param   prefixColumns
     *          The columns to prepend to the string. Each will be
     *          tab-separated.
     *
     * @param   columns
     *          The columns to export into a string.
     *
     * @param   suffixColumns
     *          The columns to prepend to the string. Each will be
     *          tab-separated.
     *
     * @return  The columns as a tab-separated string.
     */
    public static String exportToString(final String[] prefixColumns, final IifExportable[] columns, final String[] suffixColumns) {
        final List<String> line = new LinkedList<>();

        Argument.ensureNotNull(prefixColumns, "prefixColumns");
        Argument.ensureNotNull(columns, "columns");
        Argument.ensureNotNull(suffixColumns, "suffixColumns");

        line.addAll(Arrays.asList(prefixColumns));

        for (int columnIndex = 0; columnIndex < columns.length; ++columnIndex) {
            IifExportable column = columns[columnIndex];

            if (column == null) {
                throw new IllegalArgumentException(
                    String.format(
                        "Not all required IIF columns contain a value (`null` "+
                        "was encountered at one-based column index `%d`). " +
                        "All required columns must have a value.",
                        (columnIndex + 1)));
            }

            line.add(column.toIifString());
        }

        line.addAll(Arrays.asList(suffixColumns));

        return joinColumns(line);
    }

    /**
     * Removes any double quotes at the beginning or end of the provided value.
     *
     * <p>Double quotes in the middle of the string should not be affected.</p>
     *
     * @param   value
     *          The value to strip quotes from.
     *
     * @return  The value, with all leading and trailing double quotes removed.
     */
    public static String stripQuotes(String value) {
        Argument.ensureNotNull(value, "value");

        return value.replaceAll("^\"|\"$", "");
    }

    /**
     * Joins the provided columns of data together with tab characters.
     *
     * @param   columns
     *          The column values.
     *
     * @return  The column values, concatenated into a single, tab-separated
     *          string.
     */
    public static String joinColumns(String[] columns) {
        return joinColumns(Arrays.asList(columns));
    }

    /**
     * Joins the provided columns of data together with tab characters.
     *
     * @param   columns
     *          The column values.
     *
     * @return  The column values, concatenated into a single, tab-separated
     *          string.
     */
    public static String joinColumns(Iterable<String> columns) {
        return String.join("\t", columns);
    }

    /**
     * Joins the provided lines of data together with newline characters.
     *
     * @param   lines
     *          The lines.
     *
     * @return  The lines, concatenated into a single, newline-separated string.
     */
    public static String joinLines(Iterable<String> lines) {
        return String.join("\n", lines);
    }

    /**
     * Prints a summary of the given transaction out to the given output stream.
     *
     * <p>The summary renders:</p>
     * <ul>
     *   <li>A human-readable report of the lines in the transaction.</li>
     *   <li>How many lines are in the transaction.</li>
     *   <li>Whether the transaction is in balance.</li>
     *   <li>How much of a discrepancy there is between credits and debits.</li>
     *   <li>The total amount of credits.</li>
     *   <li>The total amount of debits.</li>
     * </ul>
     *
     * <p>The given stream is flushed, but not closed, by this method.</p>
     *
     * @param   transaction
     *          The IIF transaction to summarize.
     *
     * @param   output
     *          The stream to which the transaction will be written.
     */
    public static void printSummary(Transaction transaction, OutputStream output) {
        printSummary(transaction, new PrintWriter(output));
    }

    /**
     * Prints a summary of the given transaction out to the given writer.
     *
     * <p>The summary renders:</p>
     * <ul>
     *   <li>A human-readable report of the lines in the transaction.</li>
     *   <li>How many lines are in the transaction.</li>
     *   <li>Whether the transaction is in balance.</li>
     *   <li>How much of a discrepancy there is between credits and debits.</li>
     *   <li>The total amount of credits.</li>
     *   <li>The total amount of debits.</li>
     * </ul>
     *
     * <p>The given writer is flushed, but not closed, by this method.</p>
     *
     * @param   transaction
     *          The IIF transaction to summarize.
     *
     * @param   output
     *          The writer to which the transaction will be written.
     */
    public static void printSummary(Transaction transaction, Writer output) {
        PrintWriter printWriter = new PrintWriter(output);

        printWriter.println(transaction.asHumanReadableReport());
        printWriter.printf("%-16s %s\n", "Lines:",       transaction.getLines().size());
        printWriter.printf("%-16s %s\n", "In balance?:", transaction.isInBalance());
        printWriter.printf("%-16s %s\n", "Discrepancy:", transaction.calculateBalanceDiscrepancy());
        printWriter.printf("%-16s %s\n", "Debits:",      transaction.calculateDebitTotal());
        printWriter.printf("%-16s %s\n", "Credits:",     transaction.calculateCreditTotal());
        printWriter.println("----");
        printWriter.println();
        printWriter.flush();
    }
}
