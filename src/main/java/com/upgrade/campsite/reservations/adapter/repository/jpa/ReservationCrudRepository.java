package com.upgrade.campsite.reservations.adapter.repository.jpa;

import com.upgrade.campsite.reservations.domain.entity.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface ReservationCrudRepository extends CrudRepository<Reservation, UUID> {


    @Query("SELECT r FROM Reservation r " +
            "WHERE r.active = true " +
            "AND (r.arrivalDate <= :endDate OR r.departureDate <= :endDate) " +
            "AND (r.arrivalDate >= :startDate OR r.departureDate >= :startDate)")
    Set<Reservation> findByPeriod(LocalDate startDate, LocalDate endDate);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.active = true " +
            "AND (r.arrivalDate <= :departureDate OR r.departureDate <= :departureDate) " +
            "AND (r.arrivalDate >= :arrivalDate OR r.departureDate > :arrivalDate)")
    Set<Reservation> findConflictingReservations(LocalDate arrivalDate, LocalDate departureDate);
}
