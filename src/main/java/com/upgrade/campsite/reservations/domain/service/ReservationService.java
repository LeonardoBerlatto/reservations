package com.upgrade.campsite.reservations.domain.service;

import com.upgrade.campsite.reservations.domain.entity.Reservation;

import java.time.LocalDate;
import java.util.Set;

public interface ReservationService {
    Set<Reservation> getReservationsForPeriod(LocalDate startDate, LocalDate endDate);
}
