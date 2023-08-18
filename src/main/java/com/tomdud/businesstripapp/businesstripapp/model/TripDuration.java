package com.tomdud.businesstripapp.businesstripapp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TripDuration {

    private LocalDate  startDate;
    private LocalDate endDate;
    private int duration;


    public TripDuration(LocalDate  startDate, LocalDate  endDate, int duration) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
    }

    public LocalDate  getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate  startDate) {
        this.startDate = startDate;
    }

    public LocalDate  getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate  endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
