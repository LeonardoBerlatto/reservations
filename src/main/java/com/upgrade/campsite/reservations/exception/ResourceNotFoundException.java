package com.upgrade.campsite.reservations.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException implements RestException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
