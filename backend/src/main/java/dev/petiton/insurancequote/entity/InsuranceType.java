package dev.petiton.insurancequote.entity;

public enum InsuranceType {

    AUTO(500.0),
    HOME(800.0);

    private final double basePremium;

    InsuranceType(double basePremium) {
        this.basePremium = basePremium;
    }

    public double getBasePremium() {
        return basePremium;
    }
}