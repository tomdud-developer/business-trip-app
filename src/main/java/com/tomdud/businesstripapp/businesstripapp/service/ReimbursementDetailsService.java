package com.tomdud.businesstripapp.businesstripapp.service;

import com.tomdud.businesstripapp.businesstripapp.dto.ReimbursementUpdateRequestDTO;
import com.tomdud.businesstripapp.businesstripapp.model.Receipt;
import com.tomdud.businesstripapp.businesstripapp.model.ReimbursementDetails;
import com.tomdud.businesstripapp.businesstripapp.model.ReimbursementSummary;
import com.tomdud.businesstripapp.businesstripapp.model.TripDuration;
import com.tomdud.businesstripapp.businesstripapp.repository.ReimbursementDetailsRepository;
import com.tomdud.businesstripapp.businesstripapp.repository.ReimbursementSummaryRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReimbursementDetailsService {

    private static volatile ReimbursementDetailsService instance;

    private final ReimbursementDetailsRepository reimbursementDetailsRepository = ReimbursementDetailsRepository.getInstance();

    private ReimbursementDetailsService() {
    }

    public static ReimbursementDetailsService getInstance() {
        ReimbursementDetailsService result = instance;
        if (result == null) {
            synchronized (ReimbursementDetailsService.class) {
                result = instance;
                if (result == null) {
                    instance = result = new ReimbursementDetailsService();
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
}
