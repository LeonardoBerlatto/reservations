package com.upgrade.campsite.reservations.adapter.mapper;

import com.upgrade.campsite.reservations.adapter.rest.representation.request.ReservationRequest;
import com.upgrade.campsite.reservations.adapter.rest.representation.response.ReservationResponse;
import com.upgrade.campsite.reservations.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationMapper {

    private final UserInfoMapper userInfoMapper;

    public Reservation toReservation(ReservationRequest reservationRequest) {
        return Reservation.builder()
                .arrivalDate(reservationRequest.arrivalDate())
                .departureDate(reservationRequest.departureDate())
                .userInformation(userInfoMapper.toUser(reservationRequest.userInfo()))
                .build();
    }

    public ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getArrivalDate(),
                reservation.getDepartureDate(),
                userInfoMapper.toRepresentation(reservation.getUserInformation()),
                reservation.isActive()
        );
    }

}
