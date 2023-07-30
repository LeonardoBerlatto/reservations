package com.upgrade.campsite.reservations.adapter.rest.representation.response;

import com.upgrade.campsite.reservations.adapter.rest.representation.UserInfoRepresentation;

import java.time.LocalDate;
import java.util.UUID;


public record ReservationResponse(
        UUID id,
        LocalDate arrivalDate,
        LocalDate departureDate,
        UserInfoRepresentation userInfo,
        boolean active
) {
}
