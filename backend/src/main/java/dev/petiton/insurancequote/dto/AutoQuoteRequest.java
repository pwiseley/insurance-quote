package dev.petiton.insurancequote.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.petiton.insurancequote.entity.CoverageType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AutoQuoteRequest(
        @Min(value = 16, message = "Minimum age is 16 years old.")
        @Max(value = 100, message = "Maximum age is 100 years old.")
        int driverAge,

        @PositiveOrZero(message = "Years of Experience can't be negative.")
        int yearsOfExperience,

        @PositiveOrZero(message = "Past Accidents number can't be negative.")
        int pastAccidents,

        @NotNull(message = "Coverage Type is required.")
        CoverageType coverageType,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        AutoRatingParameters parameters
) implements QuoteRequest {}