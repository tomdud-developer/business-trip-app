package com.tomdud.businesstripapp.businesstripapp.service;

import com.tomdud.businesstripapp.businesstripapp.dto.ReimbursementUpdateRequestDTO;
import com.tomdud.businesstripapp.businesstripapp.model.*;
import com.tomdud.businesstripapp.businesstripapp.repository.ReimbursementDetailsRepository;
import com.tomdud.businesstripapp.businesstripapp.repository.ReimbursementSummaryRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReimbursementService implements Calculator {

    private static volatile ReimbursementService instance;

    private final ReimbursementDetailsRepository reimbursementDetailsRepository = ReimbursementDetailsRepository.getInstance();
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

    public ReimbursementDetails add(ReimbursementUpdateRequestDTO reimbursementUpdateRequestDTO) {
        ReimbursementDetails reimbursementDetails = new ReimbursementDetails(
                reimbursementUpdateRequestDTO.getPerKilometer(),
                reimbursementUpdateRequestDTO.getPerDay(),
                reimbursementUpdateRequestDTO.isEnableMileageLimit(),
                reimbursementUpdateRequestDTO.getMileageLimit(),
                reimbursementUpdateRequestDTO.isEnableTotalReimbursementLimit(),
                reimbursementUpdateRequestDTO.getTotalReimbursementLimit(),
                LocalDateTime.now()
        );

        return reimbursementDetailsRepository.add(reimbursementDetails);
    }

    public List<ReimbursementDetails> getAllDetails() {
        return reimbursementDetailsRepository.getAll()
                .stream()
                .sorted(Comparator.comparing(ReimbursementDetails::getSettingDate).reversed())
                .collect(Collectors.toList());
    }

    public ReimbursementDetails getLeastDetails() {
        return reimbursementDetailsRepository.getAll()
                .stream()
                .sorted(Comparator.comparing(ReimbursementDetails::getSettingDate).reversed())
                .limit(1)
                .collect(Collectors.toList()).get(0);
    }

    @Override
    public void recalculateReimbursements(ReimbursementSummary reimbursementSummary) {
        reimbursementSummary.setReimbursementDetails(getLeastDetails());

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

    public ReimbursementSummary saveReimbursementSummary(ReimbursementSummary reimbursementSummary, long userId) {
        reimbursementSummary.setUserId(userId);
        return reimbursementSummaryRepository.save(reimbursementSummary);
    }


}
