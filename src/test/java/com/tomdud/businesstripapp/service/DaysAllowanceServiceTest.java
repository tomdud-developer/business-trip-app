package com.tomdud.businesstripapp.service;

import com.tomdud.businesstripapp.exception.DaysAllowanceServiceException;
import com.tomdud.businesstripapp.model.TripDuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class DaysAllowanceServiceTest {

    DaysAllowanceService daysAllowanceService = DaysAllowanceService.getInstance();

    TripDuration tripDuration;

    @BeforeEach
    void setUp() {
        //given
        tripDuration = new TripDuration(
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                8,
                new HashSet<>()
        );
    }

    @Test
    void getInstanceShouldReturnTheSameObject() {
        assertEquals(DaysAllowanceService.getInstance(), DaysAllowanceService.getInstance());
    }

    @Test
    void modifyTripDurationBasedOnChangedDays() {
        //when
        daysAllowanceService.modifyTripDurationBasedOnChangedDays(tripDuration, 5);

        //then
        Assertions.assertEquals(LocalDate.now(), tripDuration.getStartDate());
        Assertions.assertEquals(LocalDate.now().plusDays(5 - 1), tripDuration.getEndDate());
        Assertions.assertEquals(5, tripDuration.getDuration());
    }

    @Test
    void modifyTripDurationBasedOnChangedEndDate() {
        //when
        daysAllowanceService.modifyTripDurationBasedOnChangedEndDate(tripDuration, LocalDate.now().plusDays(3));

        //then
        Assertions.assertEquals(LocalDate.now(), tripDuration.getStartDate());
        Assertions.assertEquals(LocalDate.now().plusDays(3), tripDuration.getEndDate());
        Assertions.assertEquals(4, tripDuration.getDuration());
    }

    @Test
    void modifyTripDurationBasedOnChangedStartDate() {
        //when
        daysAllowanceService.modifyTripDurationBasedOnChangedStartDate(tripDuration, LocalDate.now().plusDays(-3));

        //then
        Assertions.assertEquals(LocalDate.now().plusDays(-3), tripDuration.getStartDate());
        Assertions.assertEquals(LocalDate.now().plusDays(7), tripDuration.getEndDate());
        Assertions.assertEquals(11, tripDuration.getDuration());
    }

    @Test
    void modifyTripDurationBasedOnChangedStartDateThrowExceptionBecauseStartDateIsAfterEndDate() {

        LocalDate newStartDate = LocalDate.now().plusDays(50);

        Assertions.assertThrows(
                DaysAllowanceServiceException.class,
                () -> daysAllowanceService.modifyTripDurationBasedOnChangedStartDate(tripDuration, newStartDate));

        Assertions.assertEquals(LocalDate.now(), tripDuration.getStartDate());
        Assertions.assertEquals(LocalDate.now().plusDays(7), tripDuration.getEndDate());
        Assertions.assertEquals(8, tripDuration.getDuration());
    }

    @Test
    void isDateBetweenOrEqualsTheSameDatesShouldReturnTrue() {
        assertTrue(daysAllowanceService.isDateBetweenOrEquals(LocalDate.now(), LocalDate.now(), LocalDate.now()));
    }

    @Test
    void isDateBetweenOrEqualsOneDayAfterEndShouldReturnFalse() {
        assertFalse(daysAllowanceService.isDateBetweenOrEquals(LocalDate.now().plusDays(2), LocalDate.now(), LocalDate.now().plusDays(1)));
    }

    @Test
    void isDateBetweenOrEqualsOneDayBeforeStartShouldReturnFalse() {
        assertFalse(daysAllowanceService.isDateBetweenOrEquals(LocalDate.now().plusDays(-1), LocalDate.now(), LocalDate.now().plusDays(1)));
    }

    @Test
    void isDateBetweenOrEqualsEndDateBeforeStartShouldReturnFalse() {
        assertFalse(daysAllowanceService.isDateBetweenOrEquals(LocalDate.now().plusDays(-50), LocalDate.now(), LocalDate.now().plusDays(-100)));
    }

    @Test
    void clearDisabledDaysAfterModification() {
        tripDuration.getDisabledDays().add(LocalDate.now().plusDays(2));
        tripDuration.getDisabledDays().add(LocalDate.now().plusDays(3));
        tripDuration.getDisabledDays().add(LocalDate.now().plusDays(4));

        daysAllowanceService.modifyTripDurationBasedOnChangedEndDate(tripDuration, LocalDate.now().plusDays(2));

        Assertions.assertEquals(1, tripDuration.getDisabledDays().size());
    }
}