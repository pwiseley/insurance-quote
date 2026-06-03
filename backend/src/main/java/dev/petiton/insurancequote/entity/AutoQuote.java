package dev.petiton.insurancequote.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "auto_quotes")
@DiscriminatorValue("AUTO")
public class AutoQuote extends Quote {

    @Column(nullable = false)
    private int driverAge;

    @Column(nullable = false)
    private int yearsOfExperience;

    @Column(nullable = false)
    private int pastAccidents;

    protected AutoQuote() {
    }

    public AutoQuote(CoverageType coverageType, int driverAge,
                     int yearsOfExperience, int pastAccidents) {
        super(coverageType);
        this.driverAge = driverAge;
        this.yearsOfExperience = yearsOfExperience;
        this.pastAccidents = pastAccidents;
    }

    @Override
    public InsuranceType getInsuranceType() {
        return InsuranceType.AUTO;
    }

    public int getDriverAge() {
        return driverAge;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public int getPastAccidents() {
        return pastAccidents;
    }
}