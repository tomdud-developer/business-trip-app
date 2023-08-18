package com.tomdud.businesstripapp.businesstripapp.service;

import com.tomdud.businesstripapp.businesstripapp.model.Reimbursement;
import com.tomdud.businesstripapp.businesstripapp.model.TripDuration;

import java.time.LocalDate;

public class DaysAllowanceService {

    private static volatile DaysAllowanceService instance;

    public static DaysAllowanceService getInstance() {
        DaysAllowanceService result = instance;
        if (result == null) {
            synchronized (DaysAllowanceService.class) {
                result = instance;
                if (result == null) {
                    instance = result = new DaysAllowanceService();
                }
            }
        }
        return result;
    }

    private DaysAllowanceService() {}

    public double calculateTotalAllowance(TripDuration tripDuration, Reimbursement reimbursement) {
        return reimbursement.getPerDay() * (tripDuration.getDuration() - tripDuration.getDisabledDays().size());
    }


    public boolean isDateBetween(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return !date.isBefore(startDate) && !date.isAfter(endDate)
                || date.isEqual(startDate) || date.isEqual(endDate);
    }
}
