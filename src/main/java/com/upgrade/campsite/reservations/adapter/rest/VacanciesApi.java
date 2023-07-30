package com.upgrade.campsite.reservations.adapter.rest;

import com.upgrade.campsite.reservations.adapter.rest.representation.response.VacancyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Vacancies", description = "Vacancies endpoints")
public interface VacanciesApi {

    @Operation(summary = "Get availability for a period of time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacancies for the period of time"),
            @ApiResponse(responseCode = "400", description = "Invalid date(s)"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<List<VacancyResponse>> getPeriodAvailability(LocalDate startDate, LocalDate endDate);
}
