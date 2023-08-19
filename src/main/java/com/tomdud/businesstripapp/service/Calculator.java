package com.tomdud.businesstripapp.service;

import com.tomdud.businesstripapp.model.ReimbursementSummary;

public interface Calculator {
    void recalculateReimbursements(ReimbursementSummary reimbursementSummary);
}
