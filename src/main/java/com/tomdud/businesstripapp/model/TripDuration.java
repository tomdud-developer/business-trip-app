package com.tomdud.businesstripapp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class TripDuration {

    private LocalDate  startDate;
    private LocalDate endDate;
    private int duration;
    private Set<LocalDate> disabledDays;

    public TripDuration(LocalDate  startDate, LocalDate  endDate, int duration, Set<LocalDate> disabledDays) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.disabledDays = disabledDays;
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

    public Set<LocalDate> getDisabledDays() {
        return disabledDays;
    }

    public void setDisabledDays(Set<LocalDate> disabledDays) {
        this.disabledDays = disabledDays;
    }
}
