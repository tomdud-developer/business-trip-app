package com.tomdud.businesstripapp.businesstripapp.entity;

import java.time.LocalDateTime;

public class Reimbursement {

    private long id;
    private double perKilometer;
    private double perDay;
    private LocalDateTime settingDate;

    public Reimbursement(double perKilometer, double perDay, LocalDateTime settingDate) {
        this.perKilometer = perKilometer;
        this.perDay = perDay;
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
}
