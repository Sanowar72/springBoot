package com.example.library_management.exception;

import com.example.library_management.dto.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1) Handle validation errors from @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        fe -> fe.getField(),
                        fe -> fe.getDefaultMessage(),
                        (existing, replacement) -> existing // if duplicates, keep first
                ));

        ApiResponse<Map<String, String>> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 2) Handle when JSON body cannot be read (malformed JSON)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleUnreadable(HttpMessageNotReadableException ex) {
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Malformed JSON request: " + ex.getMostSpecificCause().getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 3) Method not allowed
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String method = ex.getMethod(); // the method that was called (e.g. "POST")
        Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods(); // may be null

        String supported = "";
        if (supportedMethods != null && !supportedMethods.isEmpty()) {
            supported = supportedMethods.stream()
                    .map(HttpMethod::name)           // explicit, safe mapping to names
                    .collect(Collectors.joining(", "));
        }

        String msg;
        if (supported.isEmpty()) {
            msg = String.format("%s method is not supported for this endpoint.", method);
        } else {
            msg = String.format("%s method is not supported for this endpoint. Supported: %s", method, supported);
        }

        ApiResponse<?> response = new ApiResponse<>(HttpStatus.METHOD_NOT_ALLOWED.value(), msg, null);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    // 4) Resource not found (custom)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(ResourceNotFoundException ex) {
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // 5) Fallback / generic
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAll(Exception ex, WebRequest request) {
        // Log the exception (use your logger). Keep message generic in prod.
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong: " + ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
