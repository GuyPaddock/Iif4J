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

import com.redbottledesign.accounting.quickbooks.builders.VendorBillBuilder;
import com.redbottledesign.accounting.quickbooks.iif.IifFile;
import com.redbottledesign.accounting.quickbooks.models.Account;
import com.redbottledesign.accounting.quickbooks.models.Amount;
import com.redbottledesign.accounting.quickbooks.models.Date;
import com.redbottledesign.accounting.quickbooks.models.DocNumber;
import com.redbottledesign.accounting.quickbooks.models.Memo;
import com.redbottledesign.accounting.quickbooks.models.Name;
import com.redbottledesign.accounting.quickbooks.models.PaymentTerms;
import com.redbottledesign.accounting.quickbooks.models.Transaction;
import com.redbottledesign.accounting.quickbooks.models.TxnClass;
import com.redbottledesign.accounting.quickbooks.util.IifUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * An example of building vendor bill and then exporting it to IIF format.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class VendorBillExample {
    public static void main(String[] args) {
        IifFile                 file                = new IifFile();
        Transaction             transaction;
        VendorBillBuilder       builder             = new VendorBillBuilder();
        Account                 widgetExpense       = new Account("Widget Expense");
        BigDecimal              widget1Cost         = new BigDecimal(250),
                                widget2Cost         = new BigDecimal(75);
        Name                    contoso             = new Name("Contoso, Inc."),
                                customerLenny       = new Name("Lenny"),
                                customerCarl        = new Name("Carl");
        TxnClass                reimbursableClass   = new TxnClass("Reimbursable");

        builder
            .setVendor(contoso)
            .setDate(new Date(LocalDate.of(2014, 1, 6)))
            .setDueDate(new Date(LocalDate.of(2014, 2, 6)))
            .setTerms(PaymentTerms.NET_30)
            .setReferenceNumber(new DocNumber("8675309"))
            .setMemo(new Memo("Thank you for your business!"));

        builder.addLineItem(
            widgetExpense,
            new Amount(widget1Cost),
            customerLenny,
            new Memo("Widget 1"),
            reimbursableClass);

        builder.addLineItem(
            widgetExpense,
            new Amount(widget2Cost),
            customerCarl,
            new Memo("Widget 2"),
            reimbursableClass);

        transaction = builder.build();
        IifUtils.printSummary(transaction, System.out);

        file.addTransaction(transaction);

        file.addVendorName(contoso);
        file.addCustomerName(customerLenny);
        file.addCustomerName(customerCarl);

        System.out.println(file.toIifString());
    }
}
