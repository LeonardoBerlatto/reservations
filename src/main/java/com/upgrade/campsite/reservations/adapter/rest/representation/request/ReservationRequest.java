package com.upgrade.campsite.reservations.adapter.rest.representation.request;


import com.upgrade.campsite.reservations.adapter.rest.representation.UserInfoRepresentation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservationRequest(
        @Valid
        @NotNull(message = "User info is mandatory")
        UserInfoRepresentation userInfo,
        @NotNull(message = "Arrival date is mandatory")
        LocalDate arrivalDate,
        @NotNull(message = "Departure date is mandatory")
        LocalDate departureDate
) {
}