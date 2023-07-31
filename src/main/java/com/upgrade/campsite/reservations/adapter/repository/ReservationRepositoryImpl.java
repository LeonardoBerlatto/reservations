package com.upgrade.campsite.reservations.adapter.repository;

import com.upgrade.campsite.reservations.adapter.repository.jpa.ReservationCrudRepository;
import com.upgrade.campsite.reservations.domain.entity.Reservation;
import com.upgrade.campsite.reservations.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationCrudRepository reservationCrudRepository;

    @Override
    public Optional<Reservation> findById(UUID id) {
        return reservationCrudRepository.findById(id);
    }

    @Override
    public Set<Reservation> getReservationsForPeriod(LocalDate startDate, LocalDate endDate) {
        return reservationCrudRepository.findByPeriod(startDate, endDate);
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationCrudRepository.save(reservation);
    }


    @Override
    public Set<Reservation> getReservationsConflictingOnPeriod(LocalDate arrivalDate, LocalDate departureDate) {
        return reservationCrudRepository.findConflictingReservations(arrivalDate, departureDate);
    }

    @Override
    public void deleteAll() {
        reservationCrudRepository.deleteAll();
    }
}
