package com.tomdud.businesstripapp.service;

import com.tomdud.businesstripapp.exception.DaysAllowanceServiceException;
import com.tomdud.businesstripapp.model.TripDuration;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

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

    public void modifyTripDurationBasedOnChangedDays(TripDuration tripDuration, int days) throws DaysAllowanceServiceException {
        LocalDate newEndDate = tripDuration.getStartDate().plusDays(days - 1);
        checkIsStartDateIsBeforeOrEqualEndDateIfNotThrowException(tripDuration.getStartDate(), newEndDate);

        tripDuration.setEndDate(newEndDate);
        tripDuration.setDuration(days);
        clearDisabledDaysAfterModification(tripDuration);
    }

    public void modifyTripDurationBasedOnChangedEndDate(TripDuration tripDuration, LocalDate newEndDate) throws DaysAllowanceServiceException {
        checkIsStartDateIsBeforeOrEqualEndDateIfNotThrowException(tripDuration.getStartDate(), newEndDate);

        tripDuration.setEndDate(newEndDate);
        tripDuration.setDuration((int) ChronoUnit.DAYS.between(tripDuration.getStartDate(), newEndDate) + 1);
        clearDisabledDaysAfterModification(tripDuration);
    }

    public void modifyTripDurationBasedOnChangedStartDate(TripDuration tripDuration, LocalDate newStartDate) throws DaysAllowanceServiceException {
        checkIsStartDateIsBeforeOrEqualEndDateIfNotThrowException(newStartDate, tripDuration.getEndDate());

        tripDuration.setStartDate(newStartDate);
        tripDuration.setDuration((int)ChronoUnit.DAYS.between(newStartDate, tripDuration.getEndDate()) + 1);
        clearDisabledDaysAfterModification(tripDuration);
    }

    public boolean isDateBetweenOrEquals(LocalDate date, LocalDate startDate, LocalDate endDate) throws DaysAllowanceServiceException {
        return !date.isBefore(startDate) && !date.isAfter(endDate)
                || date.isEqual(startDate) || date.isEqual(endDate);
    }

    public void clearDisabledDaysAfterModification(TripDuration tripDuration) {
        Set<LocalDate> newSet = new HashSet<>();

        for (LocalDate date : tripDuration.getDisabledDays()) {
            if (isDateBetweenOrEquals(date, tripDuration.getStartDate(), tripDuration.getEndDate())) {
                newSet.add(date);
            }
        }

        tripDuration.setDisabledDays(newSet);
    }

    private void checkIsStartDateIsBeforeOrEqualEndDateIfNotThrowException(LocalDate startDate, LocalDate endDate) throws DaysAllowanceServiceException {
        if (startDate.isAfter(endDate) && !startDate.isEqual(endDate))
            throw new DaysAllowanceServiceException("Start date is after end date");
    }


}
