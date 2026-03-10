package com.aman.projects.lovable_clone.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

public record APIError(
        HttpStatus status,
        String message,
        Instant timestamp,
        @JsonInclude(JsonInclude.Include.NON_NULL) List<APIFieldError> errors)
/*"Include this field in the JSON only if it is not null."
What does JsonInclude.Include.NON_NULL does
NON_NULL means:
If the value is null → do NOT include it in JSON
If the value has data → include it*/ {

    public APIError(HttpStatus status, String message) {
        this(status, message, Instant.now(), null);
    }

    public APIError(HttpStatus status, String message, List<APIFieldError> errors) {
        this(status, message, Instant.now(), errors);
    }
}

record APIFieldError(String field, String message) {
}
