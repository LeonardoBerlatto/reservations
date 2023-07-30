package com.upgrade.campsite.reservations.adapter.rest.representation.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;


public record ErrorResponse (
        String message,
        int code,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime timestamp
){
}
