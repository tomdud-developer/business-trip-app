package com.tomdud.businesstripapp.businesstripapp.model;

import java.time.LocalDateTime;

public class ReimbursementDetails {

    private long id;
    private double perKilometer;
    private double perDay;

    private boolean enableMileageLimit;
    private double mileageLimit;

    private boolean enableTotalReimbursementLimit;
    private double totalReimbursementLimit;

    private LocalDateTime settingDate;

    public ReimbursementDetails(double perKilometer, double perDay, LocalDateTime settingDate) {
        this.perKilometer = perKilometer;
        this.perDay = perDay;
        this.settingDate = settingDate;

        this.enableMileageLimit = false;
        this.mileageLimit = 0.0;

        this.enableTotalReimbursementLimit = false;
        this.totalReimbursementLimit = 0.0;
    }

    public ReimbursementDetails(double perKilometer,
                                double perDay,
                                boolean enableMileageLimit,
                                double mileageLimit,
                                boolean enableTotalReimbursementLimit,
                                double totalReimbursementLimit,
                                LocalDateTime settingDate) {
        this.perKilometer = perKilometer;
        this.perDay = perDay;
        this.enableMileageLimit = enableMileageLimit;
        this.mileageLimit = mileageLimit;
        this.enableTotalReimbursementLimit = enableTotalReimbursementLimit;
        this.totalReimbursementLimit = totalReimbursementLimit;
        this.settingDate = settingDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDateTime getSettingDate() {
        return settingDate;
    }

    public void setSettingDate(LocalDateTime settingDate) {
        this.settingDate = settingDate;
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
