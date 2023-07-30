package com.upgrade.campsite.reservations.domain.repository;

import com.upgrade.campsite.reservations.domain.entity.Reservation;

import java.time.LocalDate;
import java.util.Set;

public interface ReservationRepository {
    Set<Reservation> getReservationsForPeriod(LocalDate startDate, LocalDate endDate);

    Reservation save(Reservation reservation);

    Set<Reservation> getReservationsConflictingOnPeriod(LocalDate arrivalDate, LocalDate departureDate);
}
