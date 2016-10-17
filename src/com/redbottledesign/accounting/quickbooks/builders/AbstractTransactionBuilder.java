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
package com.redbottledesign.accounting.quickbooks.builders;

import com.redbottledesign.accounting.quickbooks.models.Account;
import com.redbottledesign.accounting.quickbooks.models.Amount;
import com.redbottledesign.accounting.quickbooks.models.DataLine;
import com.redbottledesign.accounting.quickbooks.models.DocNumber;
import com.redbottledesign.accounting.quickbooks.models.Memo;
import com.redbottledesign.accounting.quickbooks.models.Name;
import com.redbottledesign.accounting.quickbooks.models.SplitLine;
import com.redbottledesign.accounting.quickbooks.models.TransactionLine;
import com.redbottledesign.accounting.quickbooks.models.TxnClass;
import com.redbottledesign.util.Argument;

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
     * <p>The newly-added transaction line is also returned, for
     * convenience.</p>
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
     * @return  The newly-added line.
     */
    protected DataLine addLine(final List<DataLine> lines, final Account account,
                               final Amount amount, final Name name, final Memo memo) {
       return this.addLine(lines, account, amount, name, memo, TxnClass.EMPTY, DocNumber.EMPTY);
    }

    /**
     * Adds a new data line to the given list of transaction lines.
     *
     * <p>By convention, the amount is negative for a credit and positive for a
     * debit.</p>
     *
     * <p>The newly-added transaction line is also returned, for
     * convenience.</p>
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
     *
     * @return  The newly-added line.
     */
    protected DataLine addLine(final List<DataLine> lines, final Account account,
                               final Amount amount, final Name name, final Memo memo,
                               final TxnClass txnClass) {
       return this.addLine(lines, account, amount, name, memo, txnClass, DocNumber.EMPTY);
    }

    /**
     * Adds a new data line to the given list of transaction lines.
     *
     * <p>By convention, the amount is negative for a credit and positive for a
     * debit.</p>
     *
     * <p>For convenience, the newly-added transaction line is also
     * returned.</p>
     *
     * @param   lines
     *          The list to which the new data line will be added.
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
     *
     * @param   docNumber
     *          The document number to associate with this line. For payment
     *          and deposit types, this can typically be a check number; for
     *          other transaction types (general journal, etc) every line
     *          have the same value for this.
     *
     * @return  The newly-added line.
     */
    protected DataLine addLine(final List<DataLine> lines, final Account account,
                               final Amount amount, final Name name, final Memo memo,
                               final TxnClass txnClass, final DocNumber docNumber) {
        DataLine newLine;

        Argument.ensureNotNull(lines,       "lines");
        Argument.ensureNotNull(account,     "account");
        Argument.ensureNotNull(amount,      "amount");
        Argument.ensureNotNull(name,        "name");
        Argument.ensureNotNull(memo,        "memo");
        Argument.ensureNotNull(txnClass,    "txnClass");
        Argument.ensureNotNull(docNumber,   "docNumber");

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
        newLine.setDocNumber(docNumber);

        lines.add(newLine);

        return newLine;
    }
}
