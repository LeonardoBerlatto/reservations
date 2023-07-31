package com.upgrade.campsite.reservations.domain.repository;

import com.upgrade.campsite.reservations.domain.entity.Reservation;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ReservationRepository {
    Set<Reservation> getReservationsForPeriod(LocalDate startDate, LocalDate endDate);

    Reservation save(Reservation reservation);

    Set<Reservation> getReservationsConflictingOnPeriod(LocalDate arrivalDate, LocalDate departureDate);

    Optional<Reservation> findById(UUID id);

    void deleteAll();
}
