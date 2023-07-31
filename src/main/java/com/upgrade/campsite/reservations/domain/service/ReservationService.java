package com.upgrade.campsite.reservations.domain.service;

import com.upgrade.campsite.reservations.domain.entity.Reservation;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface ReservationService {
    Set<Reservation> getReservationsForPeriod(LocalDate startDate, LocalDate endDate);

    Reservation createReservation(Reservation reservation);

    Reservation markReservationAsCancelled(UUID id);
}
