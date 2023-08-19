package com.tomdud.businesstripapp.model;

public class Receipt {

    private final double value;
    private final ReceiptType receiptType;
    private final double reimbursement;

    public Receipt(double value, ReceiptType receiptType) {
        this.value = value;
        this.receiptType = receiptType;

        if (receiptType.isEnableLimit() && (value > receiptType.getLimit()))
            this.reimbursement = receiptType.getLimit();
        else
            this.reimbursement = value;
    }

    public double getValue() {
        return value;
    }

    public ReceiptType getReceiptType() {
        return receiptType;
    }

    public double getReimbursement() {
        return reimbursement;
    }
}
