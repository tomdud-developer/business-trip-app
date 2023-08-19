package com.tomdud.businesstripapp.businesstripapp.service;

import com.tomdud.businesstripapp.businesstripapp.model.*;
import com.tomdud.businesstripapp.businesstripapp.repository.ReimbursementDetailsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReimbursementServiceTest {

    ReimbursementService reimbursementService = ReimbursementService.getInstance();
    ReimbursementDetailsRepository reimbursementDetailsRepository = ReimbursementDetailsRepository.getInstance();
    ReimbursementDetailsService reimbursementDetailsService = ReimbursementDetailsService.getInstance();
    DaysAllowanceService daysAllowanceService = DaysAllowanceService.getInstance();

    ReimbursementSummary reimbursementSummary;

    @BeforeAll
    static void setUp() {
        ReimbursementDetailsRepository.getInstance().add(ReimbursementDetailsRepository.getInstance().getDefault());
    }

    @BeforeEach
    void beforeEach() {
        reimbursementSummary = ReimbursementSummary.getInitialized(
                reimbursementDetailsService.getLeastDetails()
        );
    }

    @Test
    void getInstance() {
        Assertions.assertEquals(ReimbursementService.getInstance(), ReimbursementService.getInstance());
    }

    @Test
    void recalculateReimbursementsInitialValue() {
        //when
        reimbursementService.recalculateReimbursements(reimbursementSummary);

        //then
        Assertions.assertEquals(
                reimbursementSummary.getReimbursementDetails().getPerDay(),
                reimbursementSummary.getTotalAllowance()
        );
    }

    @Test
    void recalculateReimbursementsChangeDuration() {
        //given
        TripDuration tripDuration = reimbursementSummary.getTripDuration();

        //when
        daysAllowanceService.modifyTripDurationBasedOnChangedDays(tripDuration,20);
        reimbursementService.recalculateReimbursements(reimbursementSummary);


        //then
        Assertions.assertEquals(
                reimbursementSummary.getReimbursementDetails().getPerDay() * tripDuration.getDuration(),
                reimbursementSummary.getTotalAllowanceForTripDuration(),0.01
        );
        Assertions.assertEquals(
                reimbursementSummary.getReimbursementDetails().getPerDay() * tripDuration.getDuration(),
                reimbursementSummary.getTotalAllowance(),0.01
        );
        Assertions.assertEquals(
                reimbursementSummary.getTotalAllowanceForTripExpenses()
                        + reimbursementSummary.getTotalAllowanceForTripDuration()
                        + reimbursementSummary.getTotalAllowanceForCarUsage(),
                reimbursementSummary.getTotalAllowance(),0.01
        );
    }

    @Test
    void recalculateReimbursementsChangeDurationAndAddedDisabledDays() {
        //given
        TripDuration tripDuration = reimbursementSummary.getTripDuration();

        //when
        daysAllowanceService.modifyTripDurationBasedOnChangedDays(tripDuration,20);
        tripDuration.getDisabledDays().add(LocalDate.now().plusDays(5));
        tripDuration.getDisabledDays().add(LocalDate.now().plusDays(6));
        tripDuration.getDisabledDays().add(LocalDate.now().plusDays(10));

        reimbursementService.recalculateReimbursements(reimbursementSummary);

        //then
        Assertions.assertEquals(
                reimbursementSummary.getReimbursementDetails().getPerDay() * (tripDuration.getDuration() - 3),
                reimbursementSummary.getTotalAllowanceForTripDuration(),0.01
        );
        Assertions.assertEquals(
                reimbursementSummary.getReimbursementDetails().getPerDay() * (tripDuration.getDuration() - 3),
                reimbursementSummary.getTotalAllowance(),0.01
        );
        Assertions.assertEquals(
                reimbursementSummary.getTotalAllowanceForTripExpenses()
                        + reimbursementSummary.getTotalAllowanceForTripDuration()
                        + reimbursementSummary.getTotalAllowanceForCarUsage(),
                reimbursementSummary.getTotalAllowance(),0.01
        );
    }

    @Test
    void recalculateReimbursementsAddReceipts() {
        //given
        Receipt receipt1 = new Receipt(20.0, new ReceiptType("name1", false, 0.0));
        Receipt receipt2 = new Receipt(100.0, new ReceiptType("name2", true, 1000.0));
        Receipt receipt3 = new Receipt(100.0, new ReceiptType("name3", true, 50.0));

        //when
        reimbursementSummary.getReceiptList().addAll(List.of(receipt1, receipt2, receipt3));
        reimbursementService.recalculateReimbursements(reimbursementSummary);

        //then
        Assertions.assertEquals(
                20.0 + 100.0 + 50.0, // Last 50.0 because of enabled limit
                reimbursementSummary.getTotalAllowanceForTripExpenses(),0.01
        );
        Assertions.assertEquals(
                reimbursementSummary.getTotalAllowanceForTripExpenses()
                        + reimbursementSummary.getTotalAllowanceForTripDuration()
                            + reimbursementSummary.getTotalAllowanceForCarUsage(),
                reimbursementSummary.getTotalAllowance(),0.01
        );
    }

    @Test
    void recalculateReimbursementsOnCarUsageChangeWithoutDistanceLimit() {
        //given
        reimbursementSummary.getReimbursementDetails().setEnableMileageLimit(false);

        //when
        reimbursementSummary.getCarUsage().setDistance(200.0);
        reimbursementService.recalculateReimbursements(reimbursementSummary);

        //then
        Assertions.assertEquals(
                200.0 * reimbursementSummary.getReimbursementDetails().getPerKilometer(),
                reimbursementSummary.getTotalAllowanceForCarUsage(),0.01
        );
        Assertions.assertEquals(
                reimbursementSummary.getTotalAllowanceForTripExpenses()
                        + reimbursementSummary.getTotalAllowanceForTripDuration()
                        + reimbursementSummary.getTotalAllowanceForCarUsage(),
                reimbursementSummary.getTotalAllowance(),0.01
        );
    }

    @Test
    void recalculateReimbursementsOnCarUsageChangeWithDistanceLimit() {
        //given
        reimbursementSummary.getReimbursementDetails().setEnableMileageLimit(true);
        reimbursementSummary.getReimbursementDetails().setMileageLimit(100.0);

        //when
        reimbursementSummary.getCarUsage().setDistance(200.0);
        reimbursementService.recalculateReimbursements(reimbursementSummary);

        //then
        Assertions.assertEquals(
                100.0 * reimbursementSummary.getReimbursementDetails().getPerKilometer(),
                reimbursementSummary.getTotalAllowanceForCarUsage(),0.01
        );
        Assertions.assertEquals(
                reimbursementSummary.getTotalAllowanceForTripExpenses()
                        + reimbursementSummary.getTotalAllowanceForTripDuration()
                        + reimbursementSummary.getTotalAllowanceForCarUsage(),
                reimbursementSummary.getTotalAllowance(),0.01
        );
    }

    @Test
    void saveReimbursementSummary() {
        //when
        reimbursementService.saveReimbursementSummary(reimbursementSummary, 0);

        //then
        Assertions.assertTrue(reimbursementService.getAllReimbursementSummariesByUserId(0).size() > 0);
    }

    @Test
    void getAllFromAllUsers() {
        //given
        int currentLength = reimbursementService.getAllFromAllUsers().size();

        //when
        reimbursementService.saveReimbursementSummary(reimbursementSummary, 20L);
        this.beforeEach();
        reimbursementService.saveReimbursementSummary(reimbursementSummary, 100L);

        //then
        assertEquals(currentLength + 2, reimbursementService.getAllFromAllUsers().size());
    }

}