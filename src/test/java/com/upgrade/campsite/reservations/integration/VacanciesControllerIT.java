package com.upgrade.campsite.reservations.integration;

import com.upgrade.campsite.reservations.domain.entity.Reservation;
import com.upgrade.campsite.reservations.domain.repository.ReservationRepository;
import com.upgrade.campsite.reservations.domain.vo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VacanciesControllerIT {

    private final MockMvc mockMvc;

    private final ReservationRepository reservationRepository;


    @Autowired
    public VacanciesControllerIT(MockMvc mockMvc, ReservationRepository reservationRepository) {
        this.mockMvc = mockMvc;
        this.reservationRepository = reservationRepository;
    }

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
    }

    @Test
    @DisplayName("Should get 200 and 30 vacancies when not supplying any dates")
    void shouldGet200And30VacanciesWhenNotSupplyingAnyDates() throws Exception {

        mockMvc.perform(get("/vacancies"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(30)))
                .andExpect(jsonPath("$[0].available").value(true));
    }

    @Test
    @DisplayName("Should get 200 and the correct number of vacancies when supplying dates")
    void shouldGet200AndTheCorrectNumberOfVacanciesWhenSupplyingDates() throws Exception {
        // arrange
        final var startDate = LocalDate.now().plusDays(1).toString();
        final var endDate = LocalDate.now().plusDays(2).toString();


        mockMvc.perform(get("/vacancies?startDate=" + startDate + "&endDate=" + endDate))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].available").value(true));
    }

    @Test
    @DisplayName("Should get 200 and the correct number of vacancies when has a reservation on period")
    void shouldGet200AndTheCorrectNumberOfVacanciesWhenHasAReservationOnPeriod() throws Exception {
        // arrange
        final var startDate = LocalDate.now().plusDays(1);
        final var endDate = LocalDate.now().plusDays(2);

        reservationRepository.save(Reservation.builder()
                .id(UUID.randomUUID())
                .arrivalDate(startDate)
                .departureDate(endDate)
                .user(User.of("John Doe", "test@email.com"))
                .active(true)
                .build());

        mockMvc.perform(get("/vacancies?startDate=" + startDate + "&endDate=" + endDate))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].available").value(false));
    }
}
