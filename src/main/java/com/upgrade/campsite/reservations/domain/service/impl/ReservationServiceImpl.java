package com.upgrade.campsite.reservations.domain.service.impl;

import com.upgrade.campsite.reservations.domain.entity.Reservation;
import com.upgrade.campsite.reservations.domain.repository.ReservationRepository;
import com.upgrade.campsite.reservations.domain.service.ReservationService;
import com.upgrade.campsite.reservations.exception.ExistingResourceException;
import com.upgrade.campsite.reservations.exception.ResourceNotFoundException;
import com.upgrade.campsite.reservations.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public Set<Reservation> getForPeriod(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.getForPeriod(startDate, endDate);
    }

    @Override
    @Transactional
    public Reservation create(final Reservation reservation) {
        checkReservationDateConstraints(reservation.getArrivalDate(), reservation.getDepartureDate());

        reservationRepository.findConflictingByArrivalDateAndDepartureDate(reservation.getArrivalDate(), reservation.getDepartureDate())
                .stream()
                .findAny()
                .ifPresent(conflictingReservation -> {
                    throw new ExistingResourceException("Reservation not available for the selected period");
                });

        reservation.setId(UUID.randomUUID());
        reservation.setActive(true);

        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public Reservation update(final UUID id, final Reservation request) {
        final var existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));


        checkReservationDateConstraints(request.getArrivalDate(), request.getDepartureDate());

        reservationRepository.findConflictingByArrivalDateAndDepartureDate(request.getArrivalDate(), request.getDepartureDate())
                .stream()
                .filter(reservation -> !id.equals(reservation.getId()))
                .findAny()
                .ifPresent(reservation -> {
                    throw new ExistingResourceException("Reservation not available for the selected period");
                });

        existingReservation.setArrivalDate(request.getArrivalDate());
        existingReservation.setDepartureDate(request.getDepartureDate());
        existingReservation.setUserInformation(request.getUserInformation());

        return reservationRepository.save(existingReservation);
    }

    @Override
    @Transactional
    public Reservation markAsCancelled(final UUID id) {
        final var reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        if (!reservation.isActive()) {
            throw new ValidationException("Reservation already cancelled");
        }

        reservation.setActive(false);

        return reservationRepository.save(reservation);
    }

    private void checkReservationDateConstraints(final LocalDate arrivalDate, final LocalDate departureDate) {
        if (arrivalDate.isAfter(departureDate)) {
            throw new ValidationException("Start date must be before end date");
        }

        if (arrivalDate.isBefore(LocalDate.now().plusDays(1))) {
            throw new ValidationException("Start date must be at least 1 day in advance");
        }

        if (arrivalDate.isAfter(LocalDate.now().plusMonths(1))) {
            throw new ValidationException("Start date must be at most 1 month in advance");
        }

        if (arrivalDate.isAfter(departureDate.minusDays(1))) {
            throw new ValidationException("Reservation must be at least 1 day long");
        }

        if (arrivalDate.isBefore(departureDate.minusDays(3))) {
            throw new ValidationException("Reservation must be at most 3 days long");
        }
    }
}
