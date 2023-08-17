package com.tomdud.businesstripapp.businesstripapp.entity;

public class ReceiptType {

    private String name;
    private boolean enableLimit;
    private double limit;

    public ReceiptType(String name, boolean enableLimit, double limit) {
        this.name = name;
        this.enableLimit = enableLimit;
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnableLimit() {
        return enableLimit;
    }

    public void setEnableLimit(boolean enableLimit) {
        this.enableLimit = enableLimit;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }
}
