package dev.petiton.insurancequote.entity;

public enum CoverageType {
    BASIC(1.0),
    STANDARD(1.3),
    PREMIUM(1.6);

    private final double multiplier;

    CoverageType(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
