package com.upgrade.campsite.reservations.adapter.rest.representation.request;


import com.upgrade.campsite.reservations.adapter.rest.representation.UserInfoRepresentation;

import java.time.LocalDate;

public record ReservationRequest(
        UserInfoRepresentation userInfo,
        LocalDate arrivalDate,
        LocalDate departureDate
) {
}