package com.upgrade.campsite.reservations.exception;


import org.springframework.http.HttpStatus;

public interface RestException {

    String getMessage();

    HttpStatus getHttpStatus();
}
