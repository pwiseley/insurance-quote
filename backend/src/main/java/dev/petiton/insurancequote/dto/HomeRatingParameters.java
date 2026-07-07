package dev.petiton.insurancequote.dto;

import java.math.BigDecimal;

public record HomeRatingParameters(
        BigDecimal baseCost,
        BigDecimal homeValueRate,
        BigDecimal oldBuildingSurcharge,
        BigDecimal alarmDiscount,
        int oldBuildingThresholdYears
) {
    public static HomeRatingParameters defaults() {
        return new HomeRatingParameters(
                BigDecimal.valueOf(800),
                BigDecimal.valueOf(0.002),
                BigDecimal.valueOf(200),
                BigDecimal.valueOf(100),
                30
        );
    }
}