package com.tomdud.businesstripapp.businesstripapp.model;

import java.util.List;

public class ReimbursementSummary {

    private long id;
    private long userId;
    private List<Receipt> receiptList;
    private CarUsage carUsage;
    private TripDuration tripDuration;

    private ReimbursementDetails reimbursementDetails;
    private double totalReimbursement;

    public ReimbursementSummary(
            TripDuration tripDuration,
            List<Receipt> receiptList,
            CarUsage carUsage,
            ReimbursementDetails reimbursementDetails,
            double totalReimbursement
    ) {
        this.receiptList = receiptList;
        this.carUsage = carUsage;
        this.tripDuration = tripDuration;
        this.reimbursementDetails = reimbursementDetails;
        this.totalReimbursement = totalReimbursement;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Receipt> getReceiptList() {
        return receiptList;
    }

    public void setReceiptList(List<Receipt> receiptList) {
        this.receiptList = receiptList;
    }

    public CarUsage getCarUsage() {
        return carUsage;
    }

    public void setCarUsage(CarUsage carUsage) {
        this.carUsage = carUsage;
    }

    public TripDuration getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(TripDuration tripDuration) {
        this.tripDuration = tripDuration;
    }

    public ReimbursementDetails getReimbursement() {
        return reimbursementDetails;
    }

    public void setReimbursement(ReimbursementDetails reimbursementDetails) {
        this.reimbursementDetails = reimbursementDetails;
    }

    public double getTotalReimbursement() {
        return totalReimbursement;
    }

    public void setTotalReimbursement(double totalReimbursement) {
        this.totalReimbursement = totalReimbursement;
    }

    public double sumReimbursementFromReceipts() {
        return receiptList.stream().map(Receipt::getReimbursement).reduce(0.0, Double::sum);
    }
}
