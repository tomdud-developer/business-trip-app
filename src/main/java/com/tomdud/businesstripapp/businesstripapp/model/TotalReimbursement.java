package com.tomdud.businesstripapp.businesstripapp.model;

import java.time.LocalDate;
import java.util.List;

public class TotalReimbursement {

    private long id;
    private long userId;
    private List<Receipt> receiptList;
    private CarUsage carUsage;
    private TripDuration tripDuration;

    private Reimbursement reimbursement;
    private double totalReimbursement;

    public TotalReimbursement(
            TripDuration tripDuration,
            List<Receipt> receiptList,
            CarUsage carUsage,
            Reimbursement reimbursement,
            double totalReimbursement
    ) {
        this.receiptList = receiptList;
        this.carUsage = carUsage;
        this.tripDuration = tripDuration;
        this.reimbursement = reimbursement;
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

    public Reimbursement getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(Reimbursement reimbursement) {
        this.reimbursement = reimbursement;
    }

    public double getTotalReimbursement() {
        return totalReimbursement;
    }

    public void setTotalReimbursement(double totalReimbursement) {
        this.totalReimbursement = totalReimbursement;
    }
}
