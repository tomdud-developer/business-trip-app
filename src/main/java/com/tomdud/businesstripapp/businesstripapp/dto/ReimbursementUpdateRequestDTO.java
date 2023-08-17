package com.tomdud.businesstripapp.businesstripapp.dto;


public class ReimbursementUpdateRequestDTO {

    private double perKilometer;
    private double perDay;

    public ReimbursementUpdateRequestDTO(double perKilometer, double perDay) {
        this.perKilometer = perKilometer;
        this.perDay = perDay;
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
}
