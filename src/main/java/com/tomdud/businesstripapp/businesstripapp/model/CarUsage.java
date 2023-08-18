package com.tomdud.businesstripapp.businesstripapp.model;

public class CarUsage {

    private double distance;
    private Reimbursement reimbursement;
    private double totalReimburse;

    public CarUsage(double distance, Reimbursement reimbursement) {
        this.distance = distance;
        this.reimbursement = reimbursement;

        if(reimbursement.isEnableMileageLimit() && distance > reimbursement.getMileageLimit())
            this.totalReimburse = reimbursement.getMileageLimit() * reimbursement.getPerKilometer();
        else this.totalReimburse = distance * reimbursement.getPerKilometer();
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Reimbursement getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(Reimbursement reimbursement) {
        this.reimbursement = reimbursement;
    }

    public double getTotalReimburse() {
        return totalReimburse;
    }

    public void setTotalReimburse(double totalReimburse) {
        this.totalReimburse = totalReimburse;
    }
}
