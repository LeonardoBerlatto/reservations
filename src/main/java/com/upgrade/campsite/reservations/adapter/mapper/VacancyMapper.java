package com.upgrade.campsite.reservations.adapter.mapper;

import com.upgrade.campsite.reservations.adapter.rest.representation.response.VacancyResponse;
import com.upgrade.campsite.reservations.domain.vo.Vacancy;
import org.springframework.stereotype.Component;

@Component
public class VacancyMapper {

    public VacancyResponse toVacancyResponse(final Vacancy vacancy) {
        return new VacancyResponse(
                vacancy.date(),
                vacancy.available()
        );
    }
}
