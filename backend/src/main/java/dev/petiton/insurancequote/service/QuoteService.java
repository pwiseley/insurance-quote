package dev.petiton.insurancequote.service;

import dev.petiton.insurancequote.dto.AutoQuoteRequest;
import dev.petiton.insurancequote.dto.HomeQuoteRequest;
import dev.petiton.insurancequote.dto.QuoteResponse;
import dev.petiton.insurancequote.dto.QuoteResponse.CostFactor;
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
        List<CostFactor> factors = new ArrayList<>();
        BigDecimal totalCost = computeAutoCost(request, factors);

        AutoQuote quote = new AutoQuote(request.coverageType(), request.driverAge(),
                request.yearsOfExperience(), request.pastAccidents());
        quote.setTotalCost(totalCost);

        return QuoteResponse.from(quoteRepository.save(quote), request,factors);
    }

    public QuoteResponse calculateHome(HomeQuoteRequest request) {
        List<CostFactor> factors = new ArrayList<>();
        BigDecimal totalCost = computeHomeCost(request, factors);

        HomeQuote quote = new HomeQuote(request.coverageType(), request.homeValue(),
                request.yearBuilt(), request.hasAlarm());
        quote.setTotalCost(totalCost);

        return QuoteResponse.from(quoteRepository.save(quote), request,factors);
    }

    private BigDecimal computeAutoCost(AutoQuoteRequest request, List<CostFactor> factors) {
        BigDecimal cost = baseCost(InsuranceType.AUTO, factors);
        cost = cost.add(ageFactor(request.driverAge(), factors));
        cost = cost.subtract(experienceFactor(request.yearsOfExperience(), factors));
        cost = cost.add(accidentFactor(request.pastAccidents(), factors));
        cost = applyCoverage(cost, request.coverageType(), factors);
        return round(cost);
    }

    private BigDecimal computeHomeCost(HomeQuoteRequest request, List<CostFactor> factors) {
        BigDecimal cost = baseCost(InsuranceType.HOME, factors);
        cost = cost.add(homeValueFactor(request.homeValue(), factors));
        cost = cost.add(buildingAgeFactor(request.yearBuilt(), factors));
        cost = cost.subtract(alarmFactor(request.hasAlarm(), factors));
        cost = applyCoverage(cost, request.coverageType(), factors);
        return round(cost);
    }

    private BigDecimal baseCost(InsuranceType type, List<CostFactor> factors) {
        BigDecimal base = BigDecimal.valueOf(type.getBaseCost());
        factors.add(new CostFactor(RiskFactorCode.BASE_COST, base));
        return base;
    }

    private BigDecimal ageFactor(int driverAge, List<CostFactor> factors) {
        if (driverAge < 25) {
            factors.add(new CostFactor(RiskFactorCode.YOUNG_DRIVER, YOUNG_DRIVER_SURCHARGE));
            return YOUNG_DRIVER_SURCHARGE;
        }
        if (driverAge > 70) {
            factors.add(new CostFactor(RiskFactorCode.SENIOR_DRIVER, SENIOR_DRIVER_SURCHARGE));
            return SENIOR_DRIVER_SURCHARGE;
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal experienceFactor(int yearsOfExperience, List<CostFactor> factors) {
        if (yearsOfExperience >= 10) {
            factors.add(new CostFactor(RiskFactorCode.EXPERIENCED_DRIVER,
                    EXPERIENCED_DRIVER_DISCOUNT.negate()));
            return EXPERIENCED_DRIVER_DISCOUNT;
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal accidentFactor(int pastAccidents, List<CostFactor> factors) {
        if (pastAccidents <= 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal surcharge = ACCIDENT_SURCHARGE.multiply(BigDecimal.valueOf(pastAccidents));
        factors.add(new CostFactor(RiskFactorCode.PAST_ACCIDENTS, surcharge));
        return surcharge;
    }

    private BigDecimal homeValueFactor(BigDecimal homeValue, List<CostFactor> factors) {
        BigDecimal surcharge = homeValue.multiply(HOME_VALUE_RATE);
        factors.add(new CostFactor(RiskFactorCode.HOME_VALUE, surcharge));
        return surcharge;
    }

    private BigDecimal buildingAgeFactor(int yearBuilt, List<CostFactor> factors) {
        if (Year.now().getValue() - yearBuilt > 30) {
            factors.add(new CostFactor(RiskFactorCode.OLD_BUILDING, OLD_BUILDING_SURCHARGE));
            return OLD_BUILDING_SURCHARGE;
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal alarmFactor(boolean hasAlarm, List<CostFactor> factors) {
        if (hasAlarm) {
            factors.add(new CostFactor(RiskFactorCode.ALARM_DISCOUNT, ALARM_DISCOUNT.negate()));
            return ALARM_DISCOUNT;
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal applyCoverage(BigDecimal cost, CoverageType coverage,
                                     List<CostFactor> factors) {
        BigDecimal result = cost.multiply(BigDecimal.valueOf(coverage.getMultiplier()));
        factors.add(new CostFactor(RiskFactorCode.COVERAGE_ADJUSTMENT, result.subtract(cost)));
        return result;
    }

    private BigDecimal round(BigDecimal cost) {
        return cost.setScale(2, RoundingMode.HALF_UP);
    }
}