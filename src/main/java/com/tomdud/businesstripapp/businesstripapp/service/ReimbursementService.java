package com.tomdud.businesstripapp.businesstripapp.service;

import com.tomdud.businesstripapp.businesstripapp.dto.ReimbursementUpdateRequestDTO;
import com.tomdud.businesstripapp.businesstripapp.model.*;
import com.tomdud.businesstripapp.businesstripapp.repository.ReimbursementRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReimbursementService {

    private static volatile ReimbursementService instance;

    private final ReimbursementRepository reimbursementRepository;
    private final ReceiptService receiptService;
    private final DaysAllowanceService daysAllowanceService;

    private ReimbursementService() {

        reimbursementRepository = ReimbursementRepository.getInstance();
        receiptService = ReceiptService.getInstance();
        daysAllowanceService = DaysAllowanceService.getInstance();
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

    public Reimbursement add(ReimbursementUpdateRequestDTO reimbursementUpdateRequestDTO) {
        Reimbursement reimbursement = new Reimbursement(
                reimbursementUpdateRequestDTO.getPerKilometer(),
                reimbursementUpdateRequestDTO.getPerDay(),
                reimbursementUpdateRequestDTO.isEnableMileageLimit(),
                reimbursementUpdateRequestDTO.getMileageLimit(),
                reimbursementUpdateRequestDTO.isEnableTotalReimbursementLimit(),
                reimbursementUpdateRequestDTO.getTotalReimbursementLimit(),
                LocalDateTime.now()
        );

        return reimbursementRepository.add(reimbursement);
    }

    public List<Reimbursement> getAll() {
        return reimbursementRepository.getAll()
                .stream()
                .sorted(Comparator.comparing(Reimbursement::getSettingDate).reversed())
                .collect(Collectors.toList());
    }

    public Reimbursement getLeast() {
        return reimbursementRepository.getAll()
                .stream()
                .sorted(Comparator.comparing(Reimbursement::getSettingDate).reversed())
                .limit(1)
                .collect(Collectors.toList()).get(0);
    }

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
                (int)ChronoUnit.DAYS.between(oldTripDuration.getStartDate(), newEndDate) + 1,
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

    public TotalReimbursement calculateTotalReimbursement(
            TripDuration tripDuration,
            List<Receipt> receipts,
            CarUsage carUsage) {

        double sum = carUsage.getTotalReimburse()
                + receiptService.calculateTotalReimbursement(receipts)
                + daysAllowanceService.calculateTotalAllowance(tripDuration, getLeast());

        return new TotalReimbursement(
                tripDuration,
                receipts,
                carUsage,
                getLeast(),
                sum
        );
    }


}
