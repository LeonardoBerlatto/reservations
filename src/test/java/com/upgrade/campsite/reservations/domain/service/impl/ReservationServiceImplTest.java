package com.upgrade.campsite.reservations.domain.service.impl;

import com.upgrade.campsite.reservations.domain.entity.Reservation;
import com.upgrade.campsite.reservations.domain.repository.ReservationRepository;
import com.upgrade.campsite.reservations.exception.ExistingResourceException;
import com.upgrade.campsite.reservations.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Captor
    private ArgumentCaptor<Reservation> reservationCaptor;


    @Test
    @DisplayName("Should not allow reservation if arrival date is before departure date")
    void shouldNotAllowReservationIfStartDateIsAfterDepartureDate() {
        // arrange
        final var startDate = LocalDate.of(2023, 1, 2);
        final var endDate = LocalDate.of(2023, 1, 1);

        final var reservation = Reservation.builder()
                .arrivalDate(startDate)
                .departureDate(endDate)
                .build();

        // act
        final var exception = assertThrows(
                ValidationException.class,
                () -> reservationService.createReservation(reservation)
        );

        // assert
        assertEquals("Start date must be before end date", exception.getMessage());
    }

    @Test
    @DisplayName("Should not allow reservation if arrival date is not before today")
    void shouldNotAllowReservationIfStartDateIsNotBeforeToday() {
        // arrange
        final var startDate = LocalDate.now();
        final var endDate = LocalDate.now().plusDays(2);

        final var reservation = Reservation.builder()
                .arrivalDate(startDate)
                .departureDate(endDate)
                .build();

        // act
        final var exception = assertThrows(
                ValidationException.class,
                () -> reservationService.createReservation(reservation)
        );

        // assert
        assertEquals("Start date must be at least 1 day in advance", exception.getMessage());
    }

    @Test
    @DisplayName("Should not allow reservation if arrival date is more than 1 month in advance")
    void shouldNotAllowReservationIfStartDateIsMoreThanOneMonthInAdvance() {
        // arrange
        final var startDate = LocalDate.now().plusMonths(1).plusDays(1);
        final var endDate = LocalDate.now().plusMonths(1).plusDays(3);

        final var reservation = Reservation.builder()
                .arrivalDate(startDate)
                .departureDate(endDate)
                .build();

        // act
        final var exception = assertThrows(
                ValidationException.class,
                () -> reservationService.createReservation(reservation)
        );

        // assert
        assertEquals("Start date must be at most 1 month in advance", exception.getMessage());
    }

    @Test
    @DisplayName("Should not allow reservation if stay is less than 1 day")
    void shouldNotAllowReservationIfStayIsLessThanOneDay() {
        // arrange
        final var startDate = LocalDate.now().plusDays(1);
        final var endDate = LocalDate.now().plusDays(1);

        final var reservation = Reservation.builder()
                .arrivalDate(startDate)
                .departureDate(endDate)
                .build();

        // act
        final var exception = assertThrows(
                ValidationException.class,
                () -> reservationService.createReservation(reservation)
        );

        // assert
        assertEquals("Reservation must be at least 1 day long", exception.getMessage());
    }

    @Test
    @DisplayName("Should not allow reservation if stay is more than 3 days")
    void shouldNotAllowReservationIfStayIsMoreThanThreeDays() {
        // arrange
        final var startDate = LocalDate.now().plusDays(1);
        final var endDate = LocalDate.now().plusDays(5);

        final var reservation = Reservation.builder()
                .arrivalDate(startDate)
                .departureDate(endDate)
                .build();

        // act
        final var exception = assertThrows(
                ValidationException.class,
                () -> reservationService.createReservation(reservation)
        );

        // assert
        assertEquals("Reservation must be at most 3 days long", exception.getMessage());
    }

    @Test
    @DisplayName("Should not allow reservation if period is not available")
    void shouldNotAllowReservationIfPeriodIsNotAvailable() {
        // arrange
        final var startDate = LocalDate.now().plusDays(1);
        final var endDate = LocalDate.now().plusDays(3);

        when(reservationRepository.getReservationsConflictingOnPeriod(any(), any()))
                .thenReturn(Set.of(Reservation.builder().build()));

        final var reservation = Reservation.builder()
                .arrivalDate(startDate)
                .departureDate(endDate)
                .build();

        // act
        final var exception = assertThrows(
                ExistingResourceException.class,
                () -> reservationService.createReservation(reservation)
        );

        // assert
        assertEquals("Reservation not available for the selected period", exception.getMessage());
    }

    @Test
    @DisplayName("Should create reservation if period is available")
    void shouldCreateReservationIfPeriodIsAvailable() {
        // arrange
        final var startDate = LocalDate.now().plusDays(1);
        final var endDate = LocalDate.now().plusDays(3);

        when(reservationRepository.getReservationsConflictingOnPeriod(any(), any()))
                .thenReturn(Set.of());

        final var reservation = Reservation.builder()
                .arrivalDate(startDate)
                .departureDate(endDate)
                .build();

        when(reservationRepository.save(any())).thenReturn(reservation);

        // act
        final var result = reservationService.createReservation(reservation);

        // assert
        verify(reservationRepository).save(reservationCaptor.capture());

        final var savedReservation = reservationCaptor.getValue();
        assertNotNull(savedReservation.getId());
        assertTrue(savedReservation.isActive());
        assertNotNull(result);
    }

}