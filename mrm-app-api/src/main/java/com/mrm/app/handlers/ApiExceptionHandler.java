package com.mrm.app.handlers;

import com.mrm.app.exceptions.ValidationException;
import com.mrm.app.models.FaultMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(ValidationException exception) {
        return ResponseEntity.badRequest()
                .body(FaultMessage.from(BAD_REQUEST.value(), BAD_REQUEST.getReasonPhrase(), exception.getMessage()));
    }
}
