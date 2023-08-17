package com.tomdud.businesstripapp.businesstripapp.service;

import com.tomdud.businesstripapp.businesstripapp.dto.ReimbursementPostRequestDTO;
import com.tomdud.businesstripapp.businesstripapp.entity.Reimbursement;
import com.tomdud.businesstripapp.businesstripapp.repository.ReimbursementRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReimbursementService {

    private static volatile ReimbursementService instance;

    private final ReimbursementRepository reimbursementRepository;

    private ReimbursementService() {
        reimbursementRepository = ReimbursementRepository.getInstance();
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

    public Reimbursement add(ReimbursementPostRequestDTO reimbursementPostRequestDTO) {
        Reimbursement reimbursement = new Reimbursement(
                reimbursementPostRequestDTO.getPerKilometer(),
                reimbursementPostRequestDTO.getPerDay(),
                LocalDateTime.now()
        );

        return reimbursementRepository.add(reimbursement);
    }

    public List<Reimbursement> getAll() {
        return reimbursementRepository.getAll();
    }

    public Reimbursement getLeast() {
        return reimbursementRepository.getAll()
                .stream()
                .sorted(Comparator.comparing(Reimbursement::getSettingDate))
                .limit(1)
                .collect(Collectors.toList()).get(0);
    }


}
