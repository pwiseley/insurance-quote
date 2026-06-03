package dev.petiton.insurancequote.service;

import dev.petiton.insurancequote.dto.AutoQuoteRequest;
import dev.petiton.insurancequote.dto.HomeQuoteRequest;
import dev.petiton.insurancequote.dto.QuoteResponse;
import dev.petiton.insurancequote.dto.QuoteResponse.PremiumFactor;
import dev.petiton.insurancequote.entity.AutoQuote;
import dev.petiton.insurancequote.entity.CoverageType;
import dev.petiton.insurancequote.entity.HomeQuote;
import dev.petiton.insurancequote.entity.InsuranceType;
import dev.petiton.insurancequote.entity.RiskFactorCode;
import dev.petiton.insurancequote.repository.QuoteRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuoteService {

    private static final BigDecimal YOUNG_DRIVER_SURCHARGE = BigDecimal.valueOf(150);
    private static final BigDecimal SENIOR_DRIVER_SURCHARGE = BigDecimal.valueOf(100);
    private static final BigDecimal EXPERIENCED_DRIVER_DISCOUNT = BigDecimal.valueOf(80);
    private static final BigDecimal ACCIDENT_SURCHARGE = BigDecimal.valueOf(120);
    private static final BigDecimal HOME_VALUE_RATE = BigDecimal.valueOf(0.002);
    private static final BigDecimal OLD_BUILDING_SURCHARGE = BigDecimal.valueOf(200);
    private static final BigDecimal ALARM_DISCOUNT = BigDecimal.valueOf(100);

    private final QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public QuoteResponse calculateAuto(AutoQuoteRequest request) {
        List<PremiumFactor> factors = new ArrayList<>();
        BigDecimal premium = computeAutoPremium(request, factors);

        AutoQuote quote = new AutoQuote(request.coverageType(), request.driverAge(),
                request.yearsOfExperience(), request.pastAccidents());
        quote.setPremium(premium);

        return QuoteResponse.from(quoteRepository.save(quote), factors);
    }

    public QuoteResponse calculateHome(HomeQuoteRequest request) {
        List<PremiumFactor> factors = new ArrayList<>();
        BigDecimal premium = computeHomePremium(request, factors);

        HomeQuote quote = new HomeQuote(request.coverageType(), request.homeValue(),
                request.yearBuilt(), request.hasAlarm());
        quote.setPremium(premium);

        return QuoteResponse.from(quoteRepository.save(quote), factors);
    }

    private BigDecimal computeAutoPremium(AutoQuoteRequest request, List<PremiumFactor> factors) {
        BigDecimal premium = basePremium(InsuranceType.AUTO, factors);
        premium = premium.add(ageFactor(request.driverAge(), factors));
        premium = premium.subtract(experienceFactor(request.yearsOfExperience(), factors));
        premium = premium.add(accidentFactor(request.pastAccidents(), factors));
        premium = applyCoverage(premium, request.coverageType(), factors);
        return round(premium);
    }

    private BigDecimal computeHomePremium(HomeQuoteRequest request, List<PremiumFactor> factors) {
        BigDecimal premium = basePremium(InsuranceType.HOME, factors);
        premium = premium.add(homeValueFactor(request.homeValue(), factors));
        premium = premium.add(buildingAgeFactor(request.yearBuilt(), factors));
        premium = premium.subtract(alarmFactor(request.hasAlarm(), factors));
        premium = applyCoverage(premium, request.coverageType(), factors);
        return round(premium);
    }

    private BigDecimal basePremium(InsuranceType type, List<PremiumFactor> factors) {
        BigDecimal base = BigDecimal.valueOf(type.getBasePremium());
        factors.add(new PremiumFactor(RiskFactorCode.BASE_PREMIUM, base));
        return base;
    }

    private BigDecimal ageFactor(int driverAge, List<PremiumFactor> factors) {
        if (driverAge < 25) {
            factors.add(new PremiumFactor(RiskFactorCode.YOUNG_DRIVER, YOUNG_DRIVER_SURCHARGE));
            return YOUNG_DRIVER_SURCHARGE;
        }
        if (driverAge > 70) {
            factors.add(new PremiumFactor(RiskFactorCode.SENIOR_DRIVER, SENIOR_DRIVER_SURCHARGE));
            return SENIOR_DRIVER_SURCHARGE;
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal experienceFactor(int yearsOfExperience, List<PremiumFactor> factors) {
        if (yearsOfExperience >= 10) {
            factors.add(new PremiumFactor(RiskFactorCode.EXPERIENCED_DRIVER,
                    EXPERIENCED_DRIVER_DISCOUNT.negate()));
            return EXPERIENCED_DRIVER_DISCOUNT;
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal accidentFactor(int pastAccidents, List<PremiumFactor> factors) {
        if (pastAccidents <= 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal surcharge = ACCIDENT_SURCHARGE.multiply(BigDecimal.valueOf(pastAccidents));
        factors.add(new PremiumFactor(RiskFactorCode.PAST_ACCIDENTS, surcharge));
        return surcharge;
    }

    private BigDecimal homeValueFactor(BigDecimal homeValue, List<PremiumFactor> factors) {
        BigDecimal surcharge = homeValue.multiply(HOME_VALUE_RATE);
        factors.add(new PremiumFactor(RiskFactorCode.HOME_VALUE, surcharge));
        return surcharge;
    }

    private BigDecimal buildingAgeFactor(int yearBuilt, List<PremiumFactor> factors) {
        if (Year.now().getValue() - yearBuilt > 30) {
            factors.add(new PremiumFactor(RiskFactorCode.OLD_BUILDING, OLD_BUILDING_SURCHARGE));
            return OLD_BUILDING_SURCHARGE;
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal alarmFactor(boolean hasAlarm, List<PremiumFactor> factors) {
        if (hasAlarm) {
            factors.add(new PremiumFactor(RiskFactorCode.ALARM_DISCOUNT, ALARM_DISCOUNT.negate()));
            return ALARM_DISCOUNT;
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal applyCoverage(BigDecimal premium, CoverageType coverage,
                                     List<PremiumFactor> factors) {
        BigDecimal result = premium.multiply(BigDecimal.valueOf(coverage.getMultiplier()));
        factors.add(new PremiumFactor(RiskFactorCode.COVERAGE_MULTIPLIER, result.subtract(premium)));
        return result;
    }

    private BigDecimal round(BigDecimal premium) {
        return premium.setScale(2, RoundingMode.HALF_UP);
    }
}