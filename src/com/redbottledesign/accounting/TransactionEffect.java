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
package com.redbottledesign.accounting;

/**
 * The effect that a particular transaction has on the current ledger.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public enum TransactionEffect {
    /**
     * A transaction type that transfers money from another account into the
     * ledger.
     */
    DEBIT(1),

    /**
     * A transaction type that has no effect on the ledger.
     */
    NONE(0),

    /**
     * A transaction type that transfers money to another account from the
     * ledger.
     */
    CREDIT(-1);

    private final int signNumber;

    /**
     * Enumeration constructor for {@code TransactionEffect}.
     *
     * @param   signNumber
     *          The sign of the transaction type.
     */
    TransactionEffect(int signNumber) {
        this.signNumber = signNumber;
    }

    /**
     * Gets a number that represents the "sign" of this type of transaction.
     *
     * @return   0 if the transaction has no effect on the ledger;
     *           1 if the transaction adds money to the ledger;
     *          -1 if the argument subtracts money from the ledger.
     */
    public int getSignNumber() {
        return this.signNumber;
    }
}
