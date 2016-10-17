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
package com.redbottledesign.accounting.quickbooks.examples;

import com.redbottledesign.accounting.quickbooks.builders.GeneralJournalBuilder;
import com.redbottledesign.accounting.quickbooks.iif.IifFile;
import com.redbottledesign.accounting.quickbooks.models.Account;
import com.redbottledesign.accounting.quickbooks.models.Amount;
import com.redbottledesign.accounting.quickbooks.models.Date;
import com.redbottledesign.accounting.quickbooks.models.DocNumber;
import com.redbottledesign.accounting.quickbooks.models.Memo;
import com.redbottledesign.accounting.quickbooks.models.Name;
import com.redbottledesign.accounting.quickbooks.models.Transaction;
import com.redbottledesign.accounting.quickbooks.models.TxnClass;

import java.time.LocalDate;

/**
 * An example of building a general journal entry containing three lines and
 * then exporting it to IIF format.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class GeneralJournalExample {
    public static void main(String[] args) {
        IifFile                 file         = new IifFile();
        Transaction             transaction;
        GeneralJournalBuilder   builder      = new GeneralJournalBuilder();
        Account                 rbdAr        = new Account("Accounts Receivable"),
                                rbdSalesCons = new Account("Sales Income:Sales - Consulting");
        Name                    contoso      = new Name("Contoso, Inc.");
        TxnClass                bobClass     = new TxnClass("Contractor:Bob"),
                                jimClass     = new TxnClass("Contractor:Jim");

        builder
            .setDate(new Date(LocalDate.of(2014, 1, 6)))
            .setEntryNumber(new DocNumber("INV-893"));

        transaction = builder.build();
        printSummary(transaction);

        builder.addLine(
                rbdAr,
                new Amount(3468),
                contoso,
                new Memo("Invoice:893"),
                TxnClass.EMPTY);

        transaction = builder.build();
        printSummary(transaction);

        builder.addLine(
            rbdSalesCons,
            new Amount(-1608),
            contoso,
            new Memo(
                "Consulting:Team member: Bob Dole Location: Contoso HQ, " +
                "On-site work by a member of the RedBottle staff."),
            bobClass);

        transaction = builder.build();
        printSummary(transaction);

        builder.addLine(
            rbdSalesCons,
            new Amount(-1860),
            contoso,
            new Memo(
                "Consulting:Team member: Jim Daniels Location: Contoso, HQ, " +
                "On-site work by a member of the RedBottle staff."),
            jimClass);

        transaction = builder.build();
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
