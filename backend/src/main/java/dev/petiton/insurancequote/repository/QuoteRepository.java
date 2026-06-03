package dev.petiton.insurancequote.repository;

import dev.petiton.insurancequote.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
