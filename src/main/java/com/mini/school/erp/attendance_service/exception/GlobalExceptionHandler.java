package com.mini.school.erp.attendance_service.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessValidation(BusinessValidationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", ex.getMessage());

        if (ex.getMessage().toLowerCase().contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        if (ex.getMessage().toLowerCase().contains("access denied")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDb(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Duplicate or constraint violation"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.FORBIDDEN.value());
        response.put("error", "Access Denied");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthentication(AuthenticationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("error", "Unauthorized");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Invalid parameter");
        response.put("message", String.format(
                "Parameter '%s' has invalid value '%s'. Expected type: %s",
                ex.getName(),
                ex.getValue(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown"
        ));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}