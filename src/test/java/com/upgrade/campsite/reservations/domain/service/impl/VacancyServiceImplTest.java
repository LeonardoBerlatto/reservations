package com.upgrade.campsite.reservations.domain.service.impl;

import com.upgrade.campsite.reservations.domain.service.ReservationService;
import com.upgrade.campsite.reservations.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VacancyServiceImplTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private VacancyServiceImpl vacancyService;

    @Test
    @DisplayName("Should throw validation error when start date is ahead of end date")
    void shouldThrowValidationErrorWhenStartDateIsAheadOfEndDate() {
        // arrange
        final var startDate = LocalDate.now().plusDays(1);
        final var endDate = LocalDate.now();

        // act and assert
        assertThrows(ValidationException.class, () -> vacancyService.getAvailabilityForPeriod(startDate, endDate));
    }

    @Test
    @DisplayName("Should return a list of vacancies")
    void shouldReturnAListOfVacancies() {
        // arrange
        final var startDate = LocalDate.now();
        final var endDate = LocalDate.now().plusDays(1);

        when(reservationService.getReservationsForPeriod(any(), any()))
                .thenReturn(Set.of());

        // act
        final var vacancies = vacancyService.getAvailabilityForPeriod(startDate, endDate);

        // assert
        verify(reservationService).getReservationsForPeriod(startDate, endDate);
        assertNotNull(vacancies);
    }

    @Test
    @DisplayName("Should return a list of vacancies when start date and end date are null")
    void shouldReturnAListOfVacanciesWhenStartDateAndEndDateAreNull() {
        // arrange
        when(reservationService.getReservationsForPeriod(any(), any()))
                .thenReturn(Set.of());

        // act
        final var vacancies = vacancyService.getAvailabilityForPeriod(null, null);

        // assert
        verify(reservationService).getReservationsForPeriod(any(), any());
        assertNotNull(vacancies);
    }
}