package com.redbottledesign.accounting.quickbooks.iif;

import com.redbottledesign.accounting.quickbooks.models.*;
import com.redbottledesign.accounting.quickbooks.util.IifUtils;

/**
 * The abstract representation of a single line in a QuickBooks IIF file.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public abstract class DataLine
implements IifExportable {
    private TxnIdentifier id;
    private TxnType type;
    private Date date;
    private Account account;
    private Name name;
    private TxnClass txnClass;
    private Amount amount;
    private DocNumber docNumber;
    private Memo memo;

    public DataLine() {
        this.setId(TxnIdentifier.NULL);
        this.setTxnClass(TxnClass.NULL);
        this.setDocNumber(DocNumber.NULL);
        this.setMemo(Memo.NULL);
    }

    public TxnIdentifier getId() {
        return this.id;
    }

    public void setId(final TxnIdentifier id) {
        this.id = id;
    }

    public TxnType getType() {
        return this.type;
    }

    public void setType(final TxnType type) {
        this.type = type;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public Name getName() {
        return this.name;
    }

    public void setName(final Name name) {
        this.name = name;
    }

    public TxnClass getTxnClass() {
        return this.txnClass;
    }

    public void setTxnClass(final TxnClass txnClass) {
        this.txnClass = txnClass;
    }

    public Amount getAmount() {
        return this.amount;
    }

    public void setAmount(final Amount amount) {
        this.amount = amount;
    }

    public DocNumber getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(final DocNumber docNumber) {
        this.docNumber = docNumber;
    }

    public Memo getMemo() {
        return this.memo;
    }

    public void setMemo(final Memo memo) {
        this.memo = memo;
    }

    public abstract String getLineType();

    @Override
    public String toIifString() {
        IifExportable[] columns = new IifExportable[] {
            this.getId(),
            this.getType(),
            this.getDate(),
            this.getAccount(),
            this.getName(),
            this.getTxnClass(),
            this.getAmount(),
            this.getDocNumber(),
            this.getMemo(),
        };

        return IifUtils.exportToString(
            new String[] { this.getLineType() },
            columns);
    }
}
