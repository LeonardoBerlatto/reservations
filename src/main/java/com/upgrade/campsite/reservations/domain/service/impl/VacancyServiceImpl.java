package com.upgrade.campsite.reservations.domain.service.impl;

import com.upgrade.campsite.reservations.domain.entity.Reservation;
import com.upgrade.campsite.reservations.domain.service.ReservationService;
import com.upgrade.campsite.reservations.domain.service.VacancyService;
import com.upgrade.campsite.reservations.domain.vo.Vacancy;
import com.upgrade.campsite.reservations.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VacancyServiceImpl implements VacancyService {

    private final ReservationService reservationService;

    @Override
    public List<Vacancy> getAvailabilityForPeriod(LocalDate startDate,
                                                  LocalDate endDate) {
        if (startDate == null || endDate == null) {
            startDate = LocalDate.now();
            endDate = startDate.plusMonths(1);
        }

        if (startDate.isAfter(endDate)) {
            throw new ValidationException("Start date must be before end date");
        }

        final var reservationServiceForPeriod = reservationService.getForPeriod(startDate, endDate);

        List<LocalDate> datesBetween = startDate.datesUntil(endDate).toList();

        return datesBetween.stream()
                .map(date -> new Vacancy(date, !dateHasReservation(reservationServiceForPeriod, date)))
                .toList();
    }

    private boolean dateHasReservation(Set<Reservation> reservedDates, LocalDate date) {
        return reservedDates.stream()
                .anyMatch(reservation -> reservation.containsDate(date));
    }
}
