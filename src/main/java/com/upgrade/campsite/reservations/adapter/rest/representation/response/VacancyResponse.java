package com.upgrade.campsite.reservations.adapter.rest.representation.response;

import java.time.LocalDate;

public record VacancyResponse(
        LocalDate date,
        boolean available
) {
}
