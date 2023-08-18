package com.tomdud.businesstripapp.businesstripapp.service;

import com.tomdud.businesstripapp.businesstripapp.dto.ReimbursementUpdateRequestDTO;
import com.tomdud.businesstripapp.businesstripapp.model.*;
import com.tomdud.businesstripapp.businesstripapp.repository.ReimbursementDetailsRepository;
import com.tomdud.businesstripapp.businesstripapp.repository.ReimbursementSummaryRepository;
import com.tomdud.businesstripapp.businesstripapp.util.SampleDataGenerator;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReimbursementService {

    private static volatile ReimbursementService instance;

    private final ReimbursementDetailsRepository reimbursementDetailsRepository = ReimbursementDetailsRepository.getInstance();
    private final ReceiptService receiptService = ReceiptService.getInstance();
    private final DaysAllowanceService daysAllowanceService = DaysAllowanceService.getInstance();
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

    public List<ReimbursementDetails> getAll() {
        return reimbursementDetailsRepository.getAll()
                .stream()
                .sorted(Comparator.comparing(ReimbursementDetails::getSettingDate).reversed())
                .collect(Collectors.toList());
    }

    public ReimbursementDetails getLeast() {
        return reimbursementDetailsRepository.getAll()
                .stream()
                .sorted(Comparator.comparing(ReimbursementDetails::getSettingDate).reversed())
                .limit(1)
                .collect(Collectors.toList()).get(0);
    }

    public ReimbursementSummary calculateTotalReimbursement(
            TripDuration tripDuration,
            List<Receipt> receipts,
            CarUsage carUsage) {

        double sum = carUsage.getTotalReimburse()
                + receiptService.calculateTotalReimbursement(receipts)
                + daysAllowanceService.calculateTotalAllowance(tripDuration, getLeast());

        return new ReimbursementSummary(
                tripDuration,
                receipts,
                carUsage,
                getLeast(),
                sum
        );
    }

    public List<ReimbursementSummary> getAllReimbursementSummariesByUserId(long userId) {
        return reimbursementSummaryRepository.getAllByUserId(userId);
    }

    public ReimbursementSummary saveReimbursementSummary(ReimbursementSummary reimbursementSummary, long userId) {
        reimbursementSummary.setUserId(userId);
        return reimbursementSummaryRepository.save(reimbursementSummary);
    }


}
