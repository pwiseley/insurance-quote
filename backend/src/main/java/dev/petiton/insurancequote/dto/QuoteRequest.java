package dev.petiton.insurancequote.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        oneOf = { AutoQuoteRequest.class, HomeQuoteRequest.class },
        discriminatorProperty = "type"
)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AutoQuoteRequest.class, name = "AUTO"),
        @JsonSubTypes.Type(value = HomeQuoteRequest.class, name = "HOME")
})
public sealed interface QuoteRequest permits AutoQuoteRequest, HomeQuoteRequest {
}