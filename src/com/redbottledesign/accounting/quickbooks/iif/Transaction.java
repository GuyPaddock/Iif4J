package com.redbottledesign.accounting.quickbooks.iif;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class Transaction
extends CompositeExportable {
    private List<DataLine> lines;

    public Transaction() {
        this.setLines(new LinkedList<>());
    }

    public List<DataLine> getLines() {
        return Collections.unmodifiableList(lines);
    }

    protected void setLines(final List<DataLine> lines) {
        this.lines = lines;
    }

    public void addLine(final DataLine line) {
        this.lines.add(line);
    }

    public boolean isInBalance() {
        return (this.getBalanceDiscrepancy().signum() == 0);
    }

    public BigDecimal getBalanceDiscrepancy() {
        return this.getDebitTotal().subtract(this.getCreditTotal());
    }

    public BigDecimal getDebitTotal() {
        return this.getTotal(TotalType.DEBIT);
    }

    public BigDecimal getCreditTotal() {
        return this.getTotal(TotalType.CREDIT);
    }

    protected BigDecimal getTotal(TotalType type) {
        BigDecimal sum;

        sum = this.getLines()
            .parallelStream()
            .map(line -> line.getAmount().getValue())
            .filter(amount -> amount.signum() == type.getSignNumber())
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .abs();

        return sum;
    }

    @Override
    protected List<IifExportable> prepareExportables() {
        final List<IifExportable> result = new LinkedList<>(this.getLines());

        result.add(new TransactionTerminationLine());

        return result;
    }

    protected enum TotalType {
        DEBIT(1),
        CREDIT(-1);

        private final int signNumber;

        TotalType(int signNumber) {
            this.signNumber = signNumber;
        }

        public int getSignNumber() {
            return this.signNumber;
        }
    }
}
