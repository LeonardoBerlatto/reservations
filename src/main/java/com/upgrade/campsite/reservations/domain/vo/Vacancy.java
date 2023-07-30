package com.upgrade.campsite.reservations.domain.vo;

import java.time.LocalDate;

public record Vacancy(
        LocalDate date,
        boolean available
) {
}
