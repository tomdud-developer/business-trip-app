package com.tomdud.businesstripapp.businesstripapp.service;

import com.tomdud.businesstripapp.businesstripapp.model.Reimbursement;
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

    public TripDuration getNewTripDurationBasedOnChangedDays(TripDuration oldTripDuration, int days) {
        return new TripDuration(
                oldTripDuration.getStartDate(),
                oldTripDuration.getStartDate().plusDays(days - 1),
                days,
                oldTripDuration.getDisabledDays()
        );
    }

    public TripDuration getNewTripDurationBasedOnChangedEndDate(TripDuration oldTripDuration, LocalDate newEndDate) {
        return new TripDuration(
                oldTripDuration.getStartDate(),
                newEndDate,
                (int) ChronoUnit.DAYS.between(oldTripDuration.getStartDate(), newEndDate) + 1,
                oldTripDuration.getDisabledDays()
        );
    }

    public TripDuration getNewTripDurationBasedOnChangedStartDate(TripDuration oldTripDuration, LocalDate newStartDate) {
        return new TripDuration(
                newStartDate,
                oldTripDuration.getEndDate(),
                (int)ChronoUnit.DAYS.between(newStartDate, oldTripDuration.getEndDate()) + 1,
                oldTripDuration.getDisabledDays()
        );
    }

    public double calculateTotalAllowance(TripDuration tripDuration, Reimbursement reimbursement) {
        return reimbursement.getPerDay() * (tripDuration.getDuration() - tripDuration.getDisabledDays().size());
    }

    public boolean isDateBetweenOrEquals(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return !date.isBefore(startDate) && !date.isAfter(endDate)
                || date.isEqual(startDate) || date.isEqual(endDate);
    }
}
