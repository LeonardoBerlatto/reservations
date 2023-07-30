package com.upgrade.campsite.reservations.adapter.rest.error;

import com.upgrade.campsite.reservations.adapter.rest.representation.response.ErrorResponse;
import com.upgrade.campsite.reservations.exception.RestException;
import com.upgrade.campsite.reservations.exception.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(RestException exception) {
        return buildResponseEntity(exception);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(final RestException exception) {
        final var response = new ErrorResponse(
                exception.getMessage(),
                exception.getHttpStatus().value(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(exception.getHttpStatus()).body(response);
    }

}