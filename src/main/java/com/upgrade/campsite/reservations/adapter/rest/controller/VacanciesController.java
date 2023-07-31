package com.upgrade.campsite.reservations.adapter.rest.controller;

import com.upgrade.campsite.reservations.adapter.mapper.VacancyMapper;
import com.upgrade.campsite.reservations.adapter.rest.VacanciesApi;
import com.upgrade.campsite.reservations.adapter.rest.representation.response.VacancyResponse;
import com.upgrade.campsite.reservations.domain.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vacancies")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VacanciesController implements VacanciesApi {

    private final VacancyService vacancyService;

    private final VacancyMapper vacancyMapper;

    @Override
    @GetMapping
    public ResponseEntity<List<VacancyResponse>> getPeriodAvailability(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        return ResponseEntity.ok(
                vacancyService.getAvailabilityForPeriod(startDate, endDate)
                .stream()
                .map(vacancyMapper::toVacancyResponse)
                .toList()
        );

    }
}
