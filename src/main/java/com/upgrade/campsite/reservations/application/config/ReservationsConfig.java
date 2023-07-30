package com.upgrade.campsite.reservations.application.config;

import com.upgrade.campsite.reservations.domain.repository.ReservationRepository;
import com.upgrade.campsite.reservations.domain.service.ReservationService;
import com.upgrade.campsite.reservations.domain.service.VacancyService;
import com.upgrade.campsite.reservations.domain.service.impl.ReservationServiceImpl;
import com.upgrade.campsite.reservations.domain.service.impl.VacancyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservationsConfig {

    @Bean
    public ReservationService reservationService(ReservationRepository reservationRepository) {
        return new ReservationServiceImpl(reservationRepository);
    }

    @Bean
    public VacancyService vacancyService(ReservationService reservationService) {
        return new VacancyServiceImpl(reservationService);
    }
}
