package com.tomdud.businesstripapp.businesstripapp.repository;

import com.tomdud.businesstripapp.businesstripapp.model.ReimbursementSummary;
import java.util.*;
import java.util.stream.Collectors;

public class ReimbursementSummaryRepository {

    private static volatile ReimbursementSummaryRepository instance;

    private Set<ReimbursementSummary> repository;

    private ReimbursementSummaryRepository() {
        repository = new HashSet<>();
    }

    public static ReimbursementSummaryRepository getInstance() {
        ReimbursementSummaryRepository result = instance;
        if (result == null) {
            synchronized (ReimbursementSummaryRepository.class) {
                result = instance;
                if (result == null) {
                    instance = result = new ReimbursementSummaryRepository();
                }
            }
        }
        return result;
    }

    public ReimbursementSummary save(ReimbursementSummary reimbursementSummary) {
        reimbursementSummary.setId(repository.size());
        repository.add(reimbursementSummary);
        return reimbursementSummary;
    }

    public List<ReimbursementSummary> getAllByUserId(long userId) {
        return repository.stream()
                .filter(reimbursementSummary -> reimbursementSummary.getUserId() == userId)
                .collect(Collectors.toList());
    }

    public List<ReimbursementSummary> getAllFromAllUsers() {
        return new ArrayList<>(repository);
    }

}
