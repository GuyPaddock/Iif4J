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
import com.redbottledesign.accounting.quickbooks.util.IifUtils;

import java.io.File;
import java.time.LocalDate;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * An example of building a general journal entry containing three lines and
 * then exporting it to IIF format.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class GeneralJournalTest {

    @Test
    public void main() throws Exception {
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

        transaction = builder.build(false);
        IifUtils.printSummary(transaction, System.out);

        builder.addLine(
                rbdAr,
                new Amount(3468),
                contoso,
                new Memo("Invoice:893"),
                TxnClass.EMPTY);

        transaction = builder.build(false);
        IifUtils.printSummary(transaction, System.out);

        builder.addLine(
            rbdSalesCons,
            new Amount(-1608),
            contoso,
            new Memo(
                "Consulting:Team member: Bob Dole Location: Contoso HQ, " +
                "On-site work by a member of the RedBottle staff."),
            bobClass);

        transaction = builder.build(false);
        IifUtils.printSummary(transaction, System.out);

        builder.addLine(
            rbdSalesCons,
            new Amount(-1860),
            contoso,
            new Memo(
                "Consulting:Team member: Jim Daniels Location: Contoso, HQ, " +
                "On-site work by a member of the RedBottle staff."),
            jimClass);

        transaction = builder.build(false);
        IifUtils.printSummary(transaction, System.out);

        file.addTransaction(transaction);
        file.addCustomerName(contoso);

        File expected = new File("src/test/resources/GeneralJournal.iif");
        File temp = File.createTempFile("temp", null);
        FileUtils.writeStringToFile(temp, file.toIifString());
        Assert.assertEquals("The GeneralJournal file differs!", FileUtils.readFileToString(expected, "utf-8"), FileUtils.readFileToString(temp, "utf-8"));
        temp.deleteOnExit();
    }
}
