package com.tomdud.businesstripapp.businesstripapp.model;

import java.util.List;

public class TotalReimbursement {

    private List<Receipt> receiptList;
    private CarUsage carUsage;
    private TripDuration tripDuration;


    public TotalReimbursement(List<Receipt> receiptList, CarUsage carUsage, TripDuration tripDuration) {
        this.receiptList = receiptList;
        this.carUsage = carUsage;
        this.tripDuration = tripDuration;
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
}
