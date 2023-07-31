package com.upgrade.campsite.reservations.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException implements RestException {

    public ValidationException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
