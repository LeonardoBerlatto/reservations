package com.upgrade.campsite.reservations.adapter.rest.controller;

import com.upgrade.campsite.reservations.adapter.mapper.ReservationMapper;
import com.upgrade.campsite.reservations.adapter.rest.ReservationsApi;
import com.upgrade.campsite.reservations.adapter.rest.representation.request.ReservationRequest;
import com.upgrade.campsite.reservations.adapter.rest.representation.response.ReservationResponse;
import com.upgrade.campsite.reservations.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationsController implements ReservationsApi {

    final ReservationService reservationService;

    final ReservationMapper reservationMapper;

    @Override
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody final ReservationRequest reservationRequest) {
        final var reservation = reservationService.createReservation(reservationMapper.toReservation(reservationRequest));

        return ResponseEntity
                .status(CREATED)
                .body(reservationMapper.toResponse(reservation));
    }
}
