package com.tomdud.businesstripapp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ReimbursementSummary {

    private long id;
    private long userId;
    private LocalDateTime creationDateTime;
    private List<Receipt> receiptList;
    private CarUsage carUsage;
    private TripDuration tripDuration;

    private double totalAllowanceForTripDuration;
    private double totalAllowanceForTripExpenses;
    private double totalAllowanceForCarUsage;
    private double totalAllowance;

    private ReimbursementDetails reimbursementDetails;

    public ReimbursementSummary(
            TripDuration tripDuration,
            List<Receipt> receiptList,
            CarUsage carUsage,
            ReimbursementDetails reimbursementDetails
    ) {
        this.receiptList = receiptList;
        this.carUsage = carUsage;
        this.tripDuration = tripDuration;
        this.reimbursementDetails = reimbursementDetails;
    }

    public static ReimbursementSummary getInitialized(ReimbursementDetails reimbursementDetails) {
        return new ReimbursementSummary(
                new TripDuration(LocalDate.now(), LocalDate.now(), 1, new HashSet<>()),
                new ArrayList<>(),
                new CarUsage(0.0),
                reimbursementDetails
        );
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

    public double getTotalAllowanceForTripDuration() {
        return totalAllowanceForTripDuration;
    }

    public void setTotalAllowanceForTripDuration(double totalAllowanceForTripDuration) {
        this.totalAllowanceForTripDuration = totalAllowanceForTripDuration;
    }

    public double getTotalAllowanceForTripExpenses() {
        return totalAllowanceForTripExpenses;
    }

    public void setTotalAllowanceForTripExpenses(double totalAllowanceForTripExpenses) {
        this.totalAllowanceForTripExpenses = totalAllowanceForTripExpenses;
    }

    public double getTotalAllowanceForCarUsage() {
        return totalAllowanceForCarUsage;
    }

    public void setTotalAllowanceForCarUsage(double totalAllowanceForCarUsage) {
        this.totalAllowanceForCarUsage = totalAllowanceForCarUsage;
    }

    public double getTotalAllowance() {
        return totalAllowance;
    }

    public void setTotalAllowance(double totalAllowance) {
        this.totalAllowance = totalAllowance;
    }

    public ReimbursementDetails getReimbursementDetails() {
        return reimbursementDetails;
    }

    public void setReimbursementDetails(ReimbursementDetails reimbursementDetails) {
        this.reimbursementDetails = reimbursementDetails;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }
}
