package com.upgrade.campsite.reservations.domain.service.impl;

import com.upgrade.campsite.reservations.domain.entity.Reservation;
import com.upgrade.campsite.reservations.domain.repository.ReservationRepository;
import com.upgrade.campsite.reservations.domain.service.ReservationService;
import com.upgrade.campsite.reservations.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public Set<Reservation> getReservationsForPeriod(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.getReservationsForPeriod(startDate, endDate);
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        // check date constraints
        checkReservationDateConstraints(reservation);

        // check availability
        reservationRepository.getReservationsConflictingOnPeriod(reservation.getArrivalDate(), reservation.getDepartureDate())
                .stream()
                .findAny()
                .ifPresent(r -> {
                    throw new ValidationException("Reservation not available for the selected period");
                });

        reservation.setId(UUID.randomUUID());
        reservation.setActive(true);

        return reservationRepository.save(reservation);
    }

    private void checkReservationDateConstraints(Reservation reservation) {
        if (reservation.getArrivalDate().isAfter(reservation.getDepartureDate())) {
            throw new ValidationException("Start date must be before end date");
        }

        if (reservation.getArrivalDate().isBefore(LocalDate.now().plusDays(1))) {
            throw new ValidationException("Start date must be at least 1 day in advance");
        }

        if (reservation.getArrivalDate().isAfter(LocalDate.now().plusMonths(1))) {
            throw new ValidationException("Start date must be at most 1 month in advance");
        }

        if (reservation.getArrivalDate().isAfter(reservation.getDepartureDate().minusDays(1))) {
            throw new ValidationException("Reservation must be at least 1 day long");
        }

        if (reservation.getArrivalDate().isBefore(reservation.getDepartureDate().minusDays(3))) {
            throw new ValidationException("Reservation must be at most 3 days long");
        }
    }
}
