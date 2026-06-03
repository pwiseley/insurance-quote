package dev.petiton.insurancequote.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "home_quotes")
@DiscriminatorValue("HOME")
public class HomeQuote extends Quote {

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal homeValue;

    @Column(nullable = false)
    private int yearBuilt;

    @Column(nullable = false)
    private boolean hasAlarm;

    protected HomeQuote() {
    }

    public HomeQuote(CoverageType coverageType, BigDecimal homeValue,
                     int yearBuilt, boolean hasAlarm) {
        super(coverageType);
        this.homeValue = homeValue;
        this.yearBuilt = yearBuilt;
        this.hasAlarm = hasAlarm;
    }

    @Override
    public InsuranceType getInsuranceType() {
        return InsuranceType.HOME;
    }

    public BigDecimal getHomeValue() {
        return homeValue;
    }

    public int getYearBuilt() {
        return yearBuilt;
    }

    public boolean isHasAlarm() {
        return hasAlarm;
    }
}