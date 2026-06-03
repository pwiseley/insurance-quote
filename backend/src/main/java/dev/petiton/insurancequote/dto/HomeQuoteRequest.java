package dev.petiton.insurancequote.dto;

import dev.petiton.insurancequote.entity.CoverageType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import java.math.BigDecimal;

public record HomeQuoteRequest(

        @NotNull(message = "Home value is required")
        @Positive(message = "Home value must be positive")
        BigDecimal homeValue,

        @Min(value = 1850, message = "Year built seems too old")
        @Max(value = 2026, message = "Year built cannot be in the future")
        int yearBuilt,

        boolean hasAlarm,

        @NotNull(message = "Coverage type is required")
        CoverageType coverageType
) {}