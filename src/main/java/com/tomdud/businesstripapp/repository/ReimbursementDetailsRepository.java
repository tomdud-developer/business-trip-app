package com.tomdud.businesstripapp.repository;

import com.tomdud.businesstripapp.model.ReimbursementDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReimbursementDetailsRepository {

    private static volatile ReimbursementDetailsRepository instance;

    private Map<Long, ReimbursementDetails> repository;

    private ReimbursementDetailsRepository() {
        repository = new HashMap<>();
        ReimbursementDetails defaultReimbursementDetails = getDefault();
        repository.put(0L, defaultReimbursementDetails);
    }

    public static ReimbursementDetailsRepository getInstance() {
        ReimbursementDetailsRepository result = instance;
        if (result == null) {
            synchronized (ReimbursementDetailsRepository.class) {
                result = instance;
                if (result == null) {
                    instance = result = new ReimbursementDetailsRepository();
                }
            }
        }
        return result;
    }

    public ReimbursementDetails add(ReimbursementDetails reimbursementDetails) {
        long newId = repository.size();
        reimbursementDetails.setId(newId);

        repository.put(newId, reimbursementDetails);

        return reimbursementDetails;
    }

    public List<ReimbursementDetails> getAll() {
        return new ArrayList<>(repository.values());
    }

    public ReimbursementDetails getDefault() {
        return new ReimbursementDetails(0.3, 15.0, LocalDateTime.now());
    }

}
