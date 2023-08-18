package com.tomdud.businesstripapp.businesstripapp.model;

public class Receipt {

    private double value;
    private ReceiptType receiptType;
    private double reimbursement;

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

    public void setValue(double value) {
        this.value = value;
    }

    public ReceiptType getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(ReceiptType receiptType) {
        this.receiptType = receiptType;
    }

    public double getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(double reimbursement) {
        this.reimbursement = reimbursement;
    }
}
