package com.tomdud.businesstripapp.businesstripapp.model;


public class ReimbursementUpdateRequestDTO {

    private double perKilometer;
    private double perDay;

    private boolean enableMileageLimit;
    private double mileageLimit;

    private boolean enableTotalReimbursementLimit;
    private double totalReimbursementLimit;

    public ReimbursementUpdateRequestDTO(
            double perKilometer,
            double perDay,
            boolean enableMileageLimit,
            double mileageLimit,
            boolean enableTotalReimbursementLimit,
            double totalReimbursementLimit) {
        this.perKilometer = perKilometer;
        this.perDay = perDay;
        this.enableMileageLimit = enableMileageLimit;
        this.mileageLimit = mileageLimit;
        this.enableTotalReimbursementLimit = enableTotalReimbursementLimit;
        this.totalReimbursementLimit = totalReimbursementLimit;
    }

    public double getPerKilometer() {
        return perKilometer;
    }

    public void setPerKilometer(double perKilometer) {
        this.perKilometer = perKilometer;
    }

    public double getPerDay() {
        return perDay;
    }

    public void setPerDay(double perDay) {
        this.perDay = perDay;
    }

    public boolean isEnableMileageLimit() {
        return enableMileageLimit;
    }

    public void setEnableMileageLimit(boolean enableMileageLimit) {
        this.enableMileageLimit = enableMileageLimit;
    }

    public double getMileageLimit() {
        return mileageLimit;
    }

    public void setMileageLimit(double mileageLimit) {
        this.mileageLimit = mileageLimit;
    }

    public boolean isEnableTotalReimbursementLimit() {
        return enableTotalReimbursementLimit;
    }

    public void setEnableTotalReimbursementLimit(boolean enableTotalReimbursementLimit) {
        this.enableTotalReimbursementLimit = enableTotalReimbursementLimit;
    }

    public double getTotalReimbursementLimit() {
        return totalReimbursementLimit;
    }

    public void setTotalReimbursementLimit(double totalReimbursementLimit) {
        this.totalReimbursementLimit = totalReimbursementLimit;
    }
}
