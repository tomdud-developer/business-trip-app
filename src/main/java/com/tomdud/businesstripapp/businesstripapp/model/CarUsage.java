package com.tomdud.businesstripapp.businesstripapp.model;

public class CarUsage {

    private double distance;
    private ReimbursementDetails reimbursementDetails;
    private double totalReimburse;

    public CarUsage(double distance, ReimbursementDetails reimbursementDetails) {
        this.distance = distance;
        this.reimbursementDetails = reimbursementDetails;

        if(reimbursementDetails.isEnableMileageLimit() && distance > reimbursementDetails.getMileageLimit())
            this.totalReimburse = reimbursementDetails.getMileageLimit() * reimbursementDetails.getPerKilometer();
        else this.totalReimburse = distance * reimbursementDetails.getPerKilometer();
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public ReimbursementDetails getReimbursement() {
        return reimbursementDetails;
    }

    public void setReimbursement(ReimbursementDetails reimbursementDetails) {
        this.reimbursementDetails = reimbursementDetails;
    }

    public double getTotalReimburse() {
        return totalReimburse;
    }

    public void setTotalReimburse(double totalReimburse) {
        this.totalReimburse = totalReimburse;
    }
}
