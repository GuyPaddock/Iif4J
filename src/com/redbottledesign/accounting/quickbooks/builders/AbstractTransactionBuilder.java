package com.redbottledesign.accounting.quickbooks.builders;

import com.redbottledesign.accounting.quickbooks.models.*;

import java.util.List;

/**
 * Abstract base class for all transaction builder objects.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public abstract class AbstractTransactionBuilder {
    /**
     * Adds a new data line to the given list of transaction lines.
     *
     * <p>By convention, the amount is negative for a credit and positive for a
     * debit.</p>
     *
     * @param   account
     *          The name of the account that the line affects.
     *
     * @param   amount
     *          The amount of money being exchanged.
     *
     * @param   name
     *          The name (customer, vendor, contractor, etc.) to associate
     *          with the line.
     *
     * @param   memo
     *          A note to add to the line.
     *
     * @param   txnClass
     *          The transaction class.
     */
    protected void addLine(final List<DataLine> lines,
                           final Account account,
                           final Amount amount,
                           final Name name,
                           final Memo memo,
                           final TxnClass txnClass) {
        DataLine newLine;

        if (lines.isEmpty()) {
            newLine = new TransactionLine();
        }
        else {
            newLine = new SplitLine();
        }

        newLine.setAccount(account);
        newLine.setAmount(amount);
        newLine.setName(name);
        newLine.setMemo(memo);
        newLine.setTxnClass(txnClass);

        lines.add(newLine);
    }
}
