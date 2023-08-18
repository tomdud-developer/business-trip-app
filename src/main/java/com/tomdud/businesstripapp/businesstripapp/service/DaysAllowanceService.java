package com.tomdud.businesstripapp.businesstripapp.service;

import com.tomdud.businesstripapp.businesstripapp.model.Reimbursement;
import com.tomdud.businesstripapp.businesstripapp.model.TripDuration;

public class DaysAllowanceService {

    private static volatile DaysAllowanceService instance;

    public static DaysAllowanceService getInstance() {
        DaysAllowanceService result = instance;
        if (result == null) {
            synchronized (DaysAllowanceService.class) {
                result = instance;
                if (result == null) {
                    instance = result = new DaysAllowanceService();
                }
            }
        }
        return result;
    }

    private DaysAllowanceService() {}

    public double calculateTotalAllowance(TripDuration tripDuration, Reimbursement reimbursement) {
        return reimbursement.getPerDay() * tripDuration.getDuration();
    }

}
