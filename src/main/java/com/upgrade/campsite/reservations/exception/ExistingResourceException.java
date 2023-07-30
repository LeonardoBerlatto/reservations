package com.upgrade.campsite.reservations.exception;

import org.springframework.http.HttpStatus;

public class ExistingResourceException extends RuntimeException implements RestException {

    public ExistingResourceException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
