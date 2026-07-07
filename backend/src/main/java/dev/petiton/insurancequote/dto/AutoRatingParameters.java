package dev.petiton.insurancequote.dto;

import java.math.BigDecimal;

public record AutoRatingParameters(
        BigDecimal baseCost,
        BigDecimal youngDriverSurcharge,
        BigDecimal seniorDriverSurcharge,
        BigDecimal experiencedDriverDiscount,
        BigDecimal accidentSurcharge,
        int youngDriverMaxAge,
        int seniorDriverMinAge,
        int experiencedMinYears
) {
    public static AutoRatingParameters defaults() {
        return new AutoRatingParameters(
                BigDecimal.valueOf(500),
                BigDecimal.valueOf(150),
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(80),
                BigDecimal.valueOf(120),
                25,
                70,
                10
        );
    }
}