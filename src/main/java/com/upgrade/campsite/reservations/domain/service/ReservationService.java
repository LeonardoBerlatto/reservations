package com.upgrade.campsite.reservations.domain.service;

import com.upgrade.campsite.reservations.domain.entity.Reservation;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface ReservationService {
    Set<Reservation> getForPeriod(LocalDate startDate, LocalDate endDate);

    Reservation create(Reservation reservation);

    Reservation markAsCancelled(UUID id);

    Reservation update(UUID id, Reservation reservation);
}
