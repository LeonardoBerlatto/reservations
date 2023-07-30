package com.upgrade.campsite.reservations.adapter.rest;

import com.upgrade.campsite.reservations.adapter.rest.representation.request.ReservationRequest;
import com.upgrade.campsite.reservations.adapter.rest.representation.response.ReservationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Reservations", description = "Reservations endpoints")
public interface ReservationsApi {

    @Operation(summary = "Create a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation created"),
            @ApiResponse(responseCode = "400", description = "Invalid payload"),
            @ApiResponse(responseCode = "409", description = "Reservation already exists for date(s)"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<ReservationResponse> createReservation(ReservationRequest reservationRequest);
}
