package dev.petiton.insurancequote.dto;

import dev.petiton.insurancequote.entity.CoverageType;
import dev.petiton.insurancequote.entity.Quote;
import dev.petiton.insurancequote.entity.RiskFactorCode;

import java.math.BigDecimal;
import java.util.List;

public record QuoteResponse(
        Long quoteId,
        QuoteRequest clientInput,
        BigDecimal totalCost,
        List<CostFactor> factors
) {

    public record CostFactor(RiskFactorCode code, BigDecimal amount) {}

    public static QuoteResponse from(Quote quote, QuoteRequest clientInput, List<CostFactor> factors) {
        return new QuoteResponse(quote.getId(), clientInput, quote.getTotalCost(), factors);
    }
}