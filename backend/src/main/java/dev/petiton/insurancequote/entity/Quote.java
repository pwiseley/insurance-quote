package dev.petiton.insurancequote.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "quotes")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "quote_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CoverageType coverageType;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal premium;

    @Column(nullable = false)
    private Instant createdAt;

    protected Quote() {
    }

    protected Quote(CoverageType coverageType) {
        this.coverageType = coverageType;
        this.createdAt = Instant.now();
    }

    public abstract InsuranceType getInsuranceType();


    public Long getId() {
        return id;
    }

    public CoverageType getCoverageType() {
        return coverageType;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}