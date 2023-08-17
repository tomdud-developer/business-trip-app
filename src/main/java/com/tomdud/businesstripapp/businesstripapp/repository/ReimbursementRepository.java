package com.tomdud.businesstripapp.businesstripapp.repository;

import com.tomdud.businesstripapp.businesstripapp.entity.Reimbursement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReimbursementRepository {

    private static volatile ReimbursementRepository instance;

    private Map<Long, Reimbursement> repository;

    private ReimbursementRepository() {
        repository = new HashMap<>();
        Reimbursement defaultReimbursement = new Reimbursement(0.3, 15.0, LocalDateTime.now());
        repository.put(0L, defaultReimbursement);
    }

    public static ReimbursementRepository getInstance() {
        ReimbursementRepository result = instance;
        if (result == null) {
            synchronized (ReimbursementRepository.class) {
                result = instance;
                if (result == null) {
                    instance = result = new ReimbursementRepository();
                }
            }
        }
        return result;
    }

    public Reimbursement add(Reimbursement reimbursement) {
        long newId = repository.size();
        reimbursement.setId(newId);

        repository.put(newId, reimbursement);

        return reimbursement;
    }

    public List<Reimbursement> getAll() {
        return new ArrayList<>(repository.values());
    }

}
