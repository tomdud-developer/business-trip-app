package com.tomdud.businesstripapp.service;

import com.tomdud.businesstripapp.model.*;
import com.tomdud.businesstripapp.repository.ReimbursementSummaryRepository;
import com.tomdud.businesstripapp.model.Receipt;
import com.tomdud.businesstripapp.model.ReimbursementSummary;
import com.tomdud.businesstripapp.model.TripDuration;

import java.time.LocalDateTime;
import java.util.List;

public class ReimbursementService implements Calculator {

    private static volatile ReimbursementService instance;

    private final ReimbursementDetailsService reimbursementDetailsService = ReimbursementDetailsService.getInstance();
    private final ReimbursementSummaryRepository reimbursementSummaryRepository = ReimbursementSummaryRepository.getInstance();


    private ReimbursementService() {
    }

    public static ReimbursementService getInstance() {
        ReimbursementService result = instance;
        if (result == null) {
            synchronized (ReimbursementService.class) {
                result = instance;
                if (result == null) {
                    instance = result = new ReimbursementService();
                }
            }
        }
        return result;
    }

    @Override
    public void recalculateReimbursements(ReimbursementSummary reimbursementSummary) {
        reimbursementSummary.setReimbursementDetails(reimbursementDetailsService.getLeastDetails());

        double totalAllowanceForTripDuration = calculateAllowanceForTripDuration(reimbursementSummary);
        double totalAllowanceForTripExpenses = calculateAllowanceForTripExpenses(reimbursementSummary);
        double totalAllowanceForCarUsage = calculateAllowanceForCarUsage(reimbursementSummary);

        reimbursementSummary.setTotalAllowanceForTripDuration(totalAllowanceForTripDuration);
        reimbursementSummary.setTotalAllowanceForTripExpenses(totalAllowanceForTripExpenses);
        reimbursementSummary.setTotalAllowanceForCarUsage(totalAllowanceForCarUsage);

        double total = totalAllowanceForTripDuration + totalAllowanceForTripExpenses + totalAllowanceForCarUsage;
        double limit = reimbursementSummary.getReimbursementDetails().getTotalReimbursementLimit();
        boolean isLimitEnabled = reimbursementSummary.getReimbursementDetails().isEnableTotalReimbursementLimit();

        if (isLimitEnabled && total > limit)
            reimbursementSummary.setTotalAllowance(limit);
        else
            reimbursementSummary.setTotalAllowance(total);
    }

    public double calculateAllowanceForTripDuration(ReimbursementSummary reimbursementSummary) {
        TripDuration tripDuration = reimbursementSummary.getTripDuration();
        double rate = reimbursementSummary.getReimbursementDetails().getPerDay();

        return rate * (tripDuration.getDuration() - tripDuration.getDisabledDays().size());
    }

    public double calculateAllowanceForTripExpenses(ReimbursementSummary reimbursementSummary) {
        return reimbursementSummary.getReceiptList()
                .stream()
                .map(Receipt::getReimbursement)
                .reduce(0.0, Double::sum);
    }

    public double calculateAllowanceForCarUsage(ReimbursementSummary reimbursementSummary) {
        double distance = reimbursementSummary.getCarUsage().getDistance();
        boolean isLimitEnabled = reimbursementSummary.getReimbursementDetails().isEnableMileageLimit();
        double distanceLimit = reimbursementSummary.getReimbursementDetails().getMileageLimit();
        double rate = reimbursementSummary.getReimbursementDetails().getPerKilometer();

        if (isLimitEnabled && distance > distanceLimit)
            return distanceLimit * rate;
        else
            return distance * rate;
    }

    public List<ReimbursementSummary> getAllReimbursementSummariesByUserId(long userId) {
        return reimbursementSummaryRepository.getAllByUserId(userId);
    }

    public List<ReimbursementSummary> getAllFromAllUsers() {
        return reimbursementSummaryRepository.getAllFromAllUsers();
    }

    public void saveReimbursementSummary(ReimbursementSummary reimbursementSummary, long userId) {
        reimbursementSummary.setUserId(userId);
        reimbursementSummary.setCreationDateTime(LocalDateTime.now());
        reimbursementSummaryRepository.save(reimbursementSummary);
    }

}
