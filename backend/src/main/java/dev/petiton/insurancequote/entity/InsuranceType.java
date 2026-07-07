package dev.petiton.insurancequote.entity;

public enum InsuranceType {

    AUTO(500.0),
    HOME(800.0);

    private final double baseCost;

    InsuranceType(double baseCost) {
        this.baseCost = baseCost;
    }

    public double getBaseCost() {
        return baseCost;
    }
}