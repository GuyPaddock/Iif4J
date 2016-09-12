package com.redbottledesign.accounting.quickbooks.iif;

import com.redbottledesign.accounting.quickbooks.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class HeaderLine
implements IifExportable {
    private Type type;

    public HeaderLine(final Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    enum Type {
        TRANSACTION(new String[] {
            "!TRNS",  "TRNSID", "TRNSTYPE", "DATE", "ACCNT", "NAME", "CLASS",
            "AMOUNT", "DOCNUM", "MEMO" }),

        SPLIT(new String[] {
            "!SPL",   "SPLID",  "TRNSTYPE", "DATE", "ACCNT", "NAME", "CLASS",
            "AMOUNT", "DOCNUM", "MEMO" }),

        TERMINATION(new String[] {
            "!ENDTRNS", ""
        });

        private final List<String> columns;

        Type(String[] columns) {
            this.columns = Collections.unmodifiableList(Arrays.asList(columns));
        }

        public List<String> getColumns() {
            return this.columns;
        }
    }

    @Override
    public String toIifString() {
        return StringUtils.joinColumns(this.getType().getColumns());
    }
}
