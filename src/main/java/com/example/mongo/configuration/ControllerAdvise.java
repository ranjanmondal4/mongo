package com.example.mongo.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvise {
    private final LocaleService localeService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Optional<FieldError> message = fieldErrors.stream().findFirst();
        return ResponseUtil.generate(false, null, message.isPresent() ? localeService.getMessage(message.get().getDefaultMessage())
                : "NA");
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        String message = "NA";
        for(ConstraintViolation constraintViolation : e.getConstraintViolations()){
            message = constraintViolation.getMessageTemplate();
        }
        return ResponseUtil.generate(false, null, localeService.getMessage(message));
    }

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException e) {
        return ResponseUtil.generate(false, null, localeService.getMessage(e.getMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        log.error("Exception {}", e);
        return ResponseUtil.generate(false, null, e.getMessage());
    }
}
