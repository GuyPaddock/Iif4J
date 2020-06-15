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

import com.redbottledesign.accounting.quickbooks.builders.VendorPaymentBuilder;
import com.redbottledesign.accounting.quickbooks.iif.IifFile;
import com.redbottledesign.accounting.quickbooks.models.Account;
import com.redbottledesign.accounting.quickbooks.models.Amount;
import com.redbottledesign.accounting.quickbooks.models.Date;
import com.redbottledesign.accounting.quickbooks.models.Memo;
import com.redbottledesign.accounting.quickbooks.models.Name;
import com.redbottledesign.accounting.quickbooks.models.Transaction;
import com.redbottledesign.accounting.quickbooks.util.IifUtils;

import java.time.LocalDate;

/**
 * An example of building a vendor bill payment and then exporting it to IIF
 * format.
 *
 * <p><strong>NOTE:</strong> There is currently no way to associate the bill
 * payment with the original bill. Vendor bill payments have to be imported as
 * checks written against Accounts Payable to a Vendor. This is a limitation of
 * QuickBooks 2006 and later for which there is no known workaround.</p>
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public class VendorPaymentExample {
    public static void main(String[] args) {
        IifFile                 file         = new IifFile();
        Transaction             transaction;
        VendorPaymentBuilder    builder      = new VendorPaymentBuilder();
        Account                 checking     = new Account("Checking Account");
        Name                    contoso      = new Name("Contoso, Inc.");

        builder
            .setVendor(contoso)
            .setAmount(new Amount(3468))
            .setDate(new Date(LocalDate.of(2014, 1, 6)))
            .setMemo(new Memo("Payment for Invoice #8675309"))
            .setChargeToAccount(checking);

        transaction = builder.build();
        IifUtils.printSummary(transaction, System.out);

        file.addTransaction(transaction);
        file.addVendorName(contoso);

        System.out.println(file.toIifString());
    }
}
