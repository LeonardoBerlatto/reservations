package com.upgrade.campsite.reservations.domain.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    @Test
    void shouldContainDateIfItsTheSameAsTheStartDate() {
        // arrange
        final var reservation = Reservation.builder()
                .arrivalDate(LocalDate.of(2020, 1, 1))
                .departureDate(LocalDate.of(2020, 1, 2))
                .build();

        // act
        final var containsDate = reservation.containsDate(LocalDate.of(2020, 1, 1));

        // assert
        assertTrue(containsDate);
    }

    @Test
    void shouldNotContainDateIfItsTheSameAsTheEndDate() {
        // arrange
        final var reservation = Reservation.builder()
                .arrivalDate(LocalDate.of(2020, 1, 1))
                .departureDate(LocalDate.of(2020, 1, 2))
                .build();

        // act
        final var containsDate = reservation.containsDate(LocalDate.of(2020, 1, 2));

        // assert
        assertFalse(containsDate);
    }

    @Test
    void shouldContainDateIfItsAfterTheStardDateAndBeforeEndDate() {
        // arrange
        final var reservation = Reservation.builder()
                .arrivalDate(LocalDate.of(2020, 1, 1))
                .departureDate(LocalDate.of(2020, 1, 3))
                .build();

        // act
        final var containsDate = reservation.containsDate(LocalDate.of(2020, 1, 2));

        // assert
        assertTrue(containsDate);
    }
}