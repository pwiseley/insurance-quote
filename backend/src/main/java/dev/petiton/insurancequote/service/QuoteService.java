package dev.petiton.insurancequote.service;

import dev.petiton.insurancequote.dto.AutoQuoteRequest;
import dev.petiton.insurancequote.dto.AutoRatingParameters;
import dev.petiton.insurancequote.dto.HomeQuoteRequest;
import dev.petiton.insurancequote.dto.HomeRatingParameters;
import dev.petiton.insurancequote.dto.QuoteResponse;
import dev.petiton.insurancequote.dto.QuoteResponse.CostFactor;
import dev.petiton.insurancequote.entity.AutoQuote;
import dev.petiton.insurancequote.entity.CoverageType;
import dev.petiton.insurancequote.entity.HomeQuote;
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

    private final QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public QuoteResponse calculateAuto(AutoQuoteRequest request) {
        AutoRatingParameters params = request.parameters() != null
                ? request.parameters()
                : AutoRatingParameters.defaults();

        List<CostFactor> factors = new ArrayList<>();
        BigDecimal totalCost = computeAutoCost(request, params, factors);

        AutoQuote quote = new AutoQuote(request.coverageType(), request.driverAge(),
                request.yearsOfExperience(), request.pastAccidents());
        quote.setTotalCost(totalCost);

        return QuoteResponse.from(quoteRepository.save(quote), request, factors);
    }

    public QuoteResponse calculateHome(HomeQuoteRequest request) {
        HomeRatingParameters params = request.parameters() != null
                ? request.parameters()
                : HomeRatingParameters.defaults();

        List<CostFactor> factors = new ArrayList<>();
        BigDecimal totalCost = computeHomeCost(request, params, factors);

        HomeQuote quote = new HomeQuote(request.coverageType(), request.homeValue(),
                request.yearBuilt(), request.hasAlarm());
        quote.setTotalCost(totalCost);

        return QuoteResponse.from(quoteRepository.save(quote), request, factors);
    }

    private BigDecimal computeAutoCost(AutoQuoteRequest request, AutoRatingParameters params,
                                       List<CostFactor> factors) {
        BigDecimal cost = baseCost(params.baseCost(), factors);
        cost = cost.add(ageFactor(request.driverAge(), params, factors));
        cost = cost.subtract(experienceFactor(request.yearsOfExperience(), params, factors));
        cost = cost.add(accidentFactor(request.pastAccidents(), params, factors));
        cost = applyCoverage(cost, request.coverageType(), factors);
        return round(cost);
    }

    private BigDecimal computeHomeCost(HomeQuoteRequest request, HomeRatingParameters params,
                                       List<CostFactor> factors) {
        BigDecimal cost = baseCost(params.baseCost(), factors);
        cost = cost.add(homeValueFactor(request.homeValue(), params, factors));
        cost = cost.add(buildingAgeFactor(request.yearBuilt(), params, factors));
        cost = cost.subtract(alarmFactor(request.hasAlarm(), params, factors));
        cost = applyCoverage(cost, request.coverageType(), factors);
        return round(cost);
    }

    private BigDecimal baseCost(BigDecimal base, List<CostFactor> factors) {
        factors.add(new CostFactor(RiskFactorCode.BASE_COST, base));
        return base;
    }

    private BigDecimal ageFactor(int driverAge, AutoRatingParameters params,
                                 List<CostFactor> factors) {
        if (driverAge < params.youngDriverMaxAge()) {
            factors.add(new CostFactor(RiskFactorCode.YOUNG_DRIVER, params.youngDriverSurcharge()));
            return params.youngDriverSurcharge();
        }
        if (driverAge > params.seniorDriverMinAge()) {
            factors.add(new CostFactor(RiskFactorCode.SENIOR_DRIVER, params.seniorDriverSurcharge()));
            return params.seniorDriverSurcharge();
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal experienceFactor(int yearsOfExperience, AutoRatingParameters params,
                                        List<CostFactor> factors) {
        if (yearsOfExperience >= params.experiencedMinYears()) {
            factors.add(new CostFactor(RiskFactorCode.EXPERIENCED_DRIVER,
                    params.experiencedDriverDiscount().negate()));
            return params.experiencedDriverDiscount();
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal accidentFactor(int pastAccidents, AutoRatingParameters params,
                                      List<CostFactor> factors) {
        if (pastAccidents <= 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal surcharge = params.accidentSurcharge().multiply(BigDecimal.valueOf(pastAccidents));
        factors.add(new CostFactor(RiskFactorCode.PAST_ACCIDENTS, surcharge));
        return surcharge;
    }

    private BigDecimal homeValueFactor(BigDecimal homeValue, HomeRatingParameters params,
                                       List<CostFactor> factors) {
        BigDecimal surcharge = homeValue.multiply(params.homeValueRate());
        factors.add(new CostFactor(RiskFactorCode.HOME_VALUE, surcharge));
        return surcharge;
    }

    private BigDecimal buildingAgeFactor(int yearBuilt, HomeRatingParameters params,
                                         List<CostFactor> factors) {
        if (Year.now().getValue() - yearBuilt > params.oldBuildingThresholdYears()) {
            factors.add(new CostFactor(RiskFactorCode.OLD_BUILDING, params.oldBuildingSurcharge()));
            return params.oldBuildingSurcharge();
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal alarmFactor(boolean hasAlarm, HomeRatingParameters params,
                                   List<CostFactor> factors) {
        if (hasAlarm) {
            factors.add(new CostFactor(RiskFactorCode.ALARM_DISCOUNT, params.alarmDiscount().negate()));
            return params.alarmDiscount();
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