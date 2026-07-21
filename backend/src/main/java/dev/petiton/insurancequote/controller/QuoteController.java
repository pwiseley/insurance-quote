package dev.petiton.insurancequote.controller;

import dev.petiton.insurancequote.dto.AutoQuoteRequest;
import dev.petiton.insurancequote.dto.HomeQuoteRequest;
import dev.petiton.insurancequote.dto.QuoteResponse;
import dev.petiton.insurancequote.service.QuoteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @PostMapping("/auto")
    public ResponseEntity<QuoteResponse> createAutoQuote(@Valid @RequestBody AutoQuoteRequest request) {
        QuoteResponse response = quoteService.calculateAuto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/home")
    public ResponseEntity<QuoteResponse> createHomeQuote(@Valid @RequestBody HomeQuoteRequest request) {
        QuoteResponse response = quoteService.calculateHome(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}