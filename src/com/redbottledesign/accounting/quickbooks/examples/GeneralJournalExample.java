/**
 * Copyright (C) 2016  Red Bottle Design, LLC
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
package com.redbottledesign.accounting.quickbooks.examples;

import com.redbottledesign.accounting.quickbooks.iif.*;
import com.redbottledesign.accounting.quickbooks.models.*;

import java.time.LocalDate;

/**
 * An example of building a general journal entry containing three lines and
 * then exporting it to IIF format.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class GeneralJournalExample {
    public static void main(String[] args) {
        File            file         = new File();
        Transaction     transaction  = new Transaction();
        DataLine        line1        = new TransactionLine(),
                        line2        = new SplitLine(),
                        line3        = new SplitLine();
        TxnType         txnType      = new TxnType(TxnType.Type.GENERAL_JOURNAL);
        Date            date         = new Date(LocalDate.of(2014, 1, 6));
        Account         rbdAr        = new Account("Accounts Receivable"),
                        rbdSalesCons = new Account("Sales Income:Sales - Consulting");
        Name            contoso      = new Name("Contoso, Inc.");
        TxnClass        bobClass     = new TxnClass("Contractor:Bob"),
                        jimClass     = new TxnClass("Contractor:Jim");
        DocNumber       docNumber    = new DocNumber("INV-893");

        line1.setType(txnType);
        line1.setDate(date);
        line1.setAccount(rbdAr);
        line1.setName(contoso);
        line1.setAmount(new Amount(3468));
        line1.setDocNumber(docNumber);
        line1.setMemo(new Memo("Invoice:893"));

        line2.setType(txnType);
        line2.setDate(date);
        line2.setAccount(rbdSalesCons);
        line2.setName(contoso);
        line2.setTxnClass(bobClass);
        line2.setAmount(new Amount(-1608));
        line2.setDocNumber(docNumber);
        line2.setMemo(
            new Memo(
                "Consulting:Team member: Bob Dole Location: Contoso HQ, " +
                "On-site work by a member of the RedBottle staff."));

        line3.setType(txnType);
        line3.setDate(date);
        line3.setAccount(rbdSalesCons);
        line3.setName(contoso);
        line3.setTxnClass(jimClass);
        line3.setAmount(new Amount(-1860));
        line3.setDocNumber(docNumber);
        line3.setMemo(
            new Memo(
                "Consulting:Team member: Jim Daniels Location: Contoso, HQ, " +
                "On-site work by a member of the RedBottle staff."));

        printSummary(transaction);

        transaction.addLine(line1);
        printSummary(transaction);

        transaction.addLine(line2);
        printSummary(transaction);

        transaction.addLine(line3);
        printSummary(transaction);

        file.addTransaction(transaction);

        System.out.println(file.toIifString());
    }

    /**
     * Prints a simple summary of the state of the transaction, for debugging
     * purposes.
     *
     * @param   transaction
     *          The transaction being summarized.
     */
    protected static void printSummary(Transaction transaction) {
        System.out.println("Lines: "       + transaction.getLines().size());
        System.out.println("In balance? "  + transaction.isInBalance());
        System.out.println("Discrepancy: " + transaction.calculateBalanceDiscrepancy());
        System.out.println("Debits: "      + transaction.calculateDebitTotal());
        System.out.println("Credits: "     + transaction.calculateCreditTotal());
        System.out.println();
    }
}
