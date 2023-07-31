package com.upgrade.campsite.reservations.integration.data;

import com.upgrade.campsite.reservations.adapter.rest.representation.UserInfoRepresentation;
import com.upgrade.campsite.reservations.adapter.rest.representation.request.ReservationRequest;

import java.time.LocalDate;

import static com.upgrade.campsite.reservations.integration.data.UserInfoTestData.aUserInfo;

public class ReservationTestData {

    public static ReservationRequest aReservationRequestForDates(LocalDate arrivalDate, LocalDate departureDate) {
        return new ReservationRequest(aUserInfo(), arrivalDate, departureDate);
    }

    public static ReservationRequest anInvalidReservationRequest() {
        return new ReservationRequest(
                new UserInfoRepresentation("", ""),
                null,
                null
        );
    }
}
