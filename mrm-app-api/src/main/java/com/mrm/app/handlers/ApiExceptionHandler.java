package com.mrm.app.handlers;

import com.mrm.app.exceptions.PermissionException;
import com.mrm.app.exceptions.ValidationException;
import com.mrm.app.models.FaultMessage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(ValidationException exception) {
        return ResponseEntity.badRequest()
                .body(FaultMessage.from(BAD_REQUEST.value(), BAD_REQUEST.getReasonPhrase(), exception.getMessage()));
    }

    @ExceptionHandler(value = PermissionException.class)
    protected ResponseEntity<Object> handlePermissionException(PermissionException exception) {
        return ResponseEntity.status(UNAUTHORIZED)
            .body(FaultMessage.from(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase(), exception.getMessage()));
    }
}
