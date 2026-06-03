package dev.petiton.insurancequote.exception;

public class QuoteNotFoundException extends RuntimeException {

    public QuoteNotFoundException(Long id) {
        super("No quote found for the id: " + id);
    }
}
