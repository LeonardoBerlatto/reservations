package com.upgrade.campsite.reservations.domain.service.impl;

import com.upgrade.campsite.reservations.domain.entity.Reservation;
import com.upgrade.campsite.reservations.domain.repository.ReservationRepository;
import com.upgrade.campsite.reservations.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Set;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public Set<Reservation> getReservationsForPeriod(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.getReservationsForPeriod(startDate, endDate);
    }
}
