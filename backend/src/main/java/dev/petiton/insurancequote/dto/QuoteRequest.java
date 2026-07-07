package dev.petiton.insurancequote.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AutoQuoteRequest.class, name = "AUTO"),
        @JsonSubTypes.Type(value = HomeQuoteRequest.class, name = "HOME")
})
public sealed interface QuoteRequest permits AutoQuoteRequest, HomeQuoteRequest{
}
