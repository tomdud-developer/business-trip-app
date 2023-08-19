package com.tomdud.businesstripapp.businesstripapp.service;

import com.tomdud.businesstripapp.businesstripapp.model.TripDuration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

    public void modifyTripDurationBasedOnChangedDays(TripDuration oldTripDuration, int days) {
        oldTripDuration.setEndDate(oldTripDuration.getStartDate().plusDays(days - 1));
        oldTripDuration.setDuration(days);
    }

    public void modifyTripDurationBasedOnChangedEndDate(TripDuration oldTripDuration, LocalDate newEndDate) {
        oldTripDuration.setEndDate(newEndDate);
        oldTripDuration.setDuration((int) ChronoUnit.DAYS.between(oldTripDuration.getStartDate(), newEndDate) + 1);
    }

    public void modifyTripDurationBasedOnChangedStartDate(TripDuration oldTripDuration, LocalDate newStartDate) {
        oldTripDuration.setStartDate(newStartDate);
        oldTripDuration.setDuration((int)ChronoUnit.DAYS.between(newStartDate, oldTripDuration.getEndDate()) + 1);
    }

    public boolean isDateBetweenOrEquals(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return !date.isBefore(startDate) && !date.isAfter(endDate)
                || date.isEqual(startDate) || date.isEqual(endDate);
    }
}
