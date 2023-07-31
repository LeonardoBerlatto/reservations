package com.upgrade.campsite.reservations.adapter.rest.error;

import com.upgrade.campsite.reservations.adapter.rest.representation.response.ErrorResponse;
import com.upgrade.campsite.reservations.exception.ExistingResourceException;
import com.upgrade.campsite.reservations.exception.ResourceNotFoundException;
import com.upgrade.campsite.reservations.exception.RestException;
import com.upgrade.campsite.reservations.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(value = {
            ValidationException.class,
            ExistingResourceException.class,
            ResourceNotFoundException.class,
    })
    public ResponseEntity<ErrorResponse> handleRestException(RestException exception) {
        return buildResponseEntity(exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception exception) {
        final var response = new ErrorResponse(
                "Unexpected error, please refer to the logs for more information",
                500,
                LocalDateTime.now()
        );

        logger.error("Unexpected error", exception);


        return ResponseEntity.status(500).body(response);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        final var errors = exception.getBindingResult().getFieldErrors();

        final var validationException = new ValidationException(errors.get(0).getDefaultMessage());

        return buildResponseEntity(validationException);
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