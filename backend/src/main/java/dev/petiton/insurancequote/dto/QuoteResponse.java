package dev.petiton.insurancequote.dto;

import dev.petiton.insurancequote.entity.Quote;
import dev.petiton.insurancequote.entity.RiskFactorCode;

import java.math.BigDecimal;
import java.util.List;

public record QuoteResponse(
        Long quoteId,
        BigDecimal premium,
        List<PremiumFactor> factors
) {

    public record PremiumFactor(RiskFactorCode code, BigDecimal amount) {}

    public static QuoteResponse from(Quote quote, List<PremiumFactor> factors) {
        return new QuoteResponse(quote.getId(), quote.getPremium(), factors);
    }
}