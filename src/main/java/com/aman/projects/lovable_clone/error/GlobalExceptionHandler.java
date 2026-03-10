package com.aman.projects.lovable_clone.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //This handles any BadRequestException when we throw ourselves
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIError> handleBadRequest(BadRequestException ex) {
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, ex.getMessage());
        log.error(apiError.toString(), ex);
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError> handleBadRequest(ResourceNotFoundException ex) {
        APIError apiError = new APIError(HttpStatus.NOT_FOUND, ex.getResourceName() + " with ID " + ex.getResourceId() + " not found ");
        log.error(apiError.toString(), ex);
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    //This gets caught by the springboot
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIError> handleInputValidationError(MethodArgumentNotValidException ex) {

        List<APIFieldError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new APIFieldError(error.getField(), error.getDefaultMessage()))
                .toList();

        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, "INPUT VALIDATION FAILED", errors);
        log.error(apiError.toString(), ex);
        return ResponseEntity.status(apiError.status()).body(apiError);
    }
}
