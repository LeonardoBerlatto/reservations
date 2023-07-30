package com.upgrade.campsite.reservations.domain.service;

import com.upgrade.campsite.reservations.domain.vo.Vacancy;

import java.time.LocalDate;
import java.util.List;

public interface VacancyService {
    List<Vacancy> getAvailabilityForPeriod(LocalDate startDate, LocalDate endDate);
}
