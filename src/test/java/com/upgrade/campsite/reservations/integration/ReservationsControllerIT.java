package com.upgrade.campsite.reservations.integration;

import com.upgrade.campsite.reservations.domain.entity.Reservation;
import com.upgrade.campsite.reservations.domain.repository.ReservationRepository;
import com.upgrade.campsite.reservations.domain.vo.UserInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static com.upgrade.campsite.reservations.integration.IntegrationTestUtils.asJson;
import static com.upgrade.campsite.reservations.integration.data.ReservationTestData.aReservationRequestForDates;
import static com.upgrade.campsite.reservations.integration.data.ReservationTestData.anInvalidReservationRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationsControllerIT {

    private final MockMvc mockMvc;

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationsControllerIT(MockMvc mockMvc, ReservationRepository reservationRepository) {
        this.mockMvc = mockMvc;
        this.reservationRepository = reservationRepository;
    }

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
    }

    @Nested
    class CreateReservation {

        @Test
        @DisplayName("Should get 201 and the reservation when supplying valid data")
        void shouldGet201AndTheReservationWhenSupplyingValidData() throws Exception {
            final var request = aReservationRequestForDates(
                    LocalDate.now().plusDays(1),
                    LocalDate.now().plusDays(2)
            );

            mockMvc.perform(post("/reservations")
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(201))
                    .andExpect(jsonPath("$.id").isNotEmpty());

        }

        @Test
        @DisplayName("Should get 400 when supplying invalid data")
        void shouldGet400WhenSupplyingInvalidData() throws Exception {
            final var request = anInvalidReservationRequest();

            mockMvc.perform(post("/reservations")
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(400));

        }

        @Test
        @DisplayName("Should get 400 when supplying invalid period for reservation")
        void shouldGet400WhenSupplyingInvalidDateForReservation() throws Exception {
            final var request = aReservationRequestForDates(
                    LocalDate.now().plusDays(1),
                    LocalDate.now().plusDays(1)
            );

            mockMvc.perform(post("/reservations")
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(400))
                    .andExpect(jsonPath("$.message").value("Reservation must be at least 1 day long"));
        }

        @Test
        @DisplayName("Should get 409 when supplying a period that conflicts with an existing reservation")
        void shouldGet409WhenSupplyingAPeriodThatConflictsWithAnExistingReservation() throws Exception {
            final var request = aReservationRequestForDates(
                    LocalDate.now().plusDays(1),
                    LocalDate.now().plusDays(2)
            );

            mockMvc.perform(post("/reservations")
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(201))
                    .andExpect(jsonPath("$.id").isNotEmpty());

            mockMvc.perform(post("/reservations")
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(409))
                    .andExpect(jsonPath("$.message").value("Reservation not available for the selected period"));
        }
    }

    @Nested
    class UpdateReservation {

        @Test
        @DisplayName("Should get 200 and the updated reservation when supplying valid data")
        void shouldGet200AndTheUpdatedReservationWhenSupplyingValidData() throws Exception {
            final var reservationId = createReservation();
            final var request = aReservationRequestForDates(
                    LocalDate.now().plusDays(3),
                    LocalDate.now().plusDays(4)
            );

            mockMvc.perform(put("/reservations/{reservationId}", reservationId)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.id").value(reservationId.toString()))
                    .andExpect(jsonPath("$.arrivalDate").value(request.arrivalDate().toString()))
                    .andExpect(jsonPath("$.departureDate").value(request.departureDate().toString()));
        }

        @Test
        @DisplayName("Should get 400 when supplying invalid data")
        void shouldGet400WhenSupplyingInvalidData() throws Exception {
            final var reservationId = createReservation();
            final var request = anInvalidReservationRequest();

            mockMvc.perform(put("/reservations/{reservationId}", reservationId)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(400));
        }

        @Test
        @DisplayName("Should get 400 when supplying invalid period for reservation")
        void shouldGet400WhenSupplyingInvalidDateForReservation() throws Exception {
            final var reservationId = createReservation();
            final var request = aReservationRequestForDates(
                    LocalDate.now().plusDays(1),
                    LocalDate.now().plusDays(1)
            );

            mockMvc.perform(put("/reservations/{reservationId}", reservationId)
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(400))
                    .andExpect(jsonPath("$.message").value("Reservation must be at least 1 day long"));
        }

        @Test
        @DisplayName("Should get 404 when supplying an invalid reservation id")
        void shouldGet404WhenSupplyingAnInvalidReservationId() throws Exception {
            final var request = aReservationRequestForDates(
                    LocalDate.now().plusDays(1),
                    LocalDate.now().plusDays(2)
            );

            mockMvc.perform(put("/reservations/{reservationId}", UUID.randomUUID())
                            .content(asJson(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(404))
                    .andExpect(jsonPath("$.message").value("Reservation not found"));
        }

    }

    @Nested
    class CancelReservation {

        @Test
        @DisplayName("Should get 204 when supplying a valid reservation id")
        void shouldGet204WhenSupplyingAValidReservationId() throws Exception {
            final var reservationId = createReservation();

            mockMvc.perform(delete("/reservations/{reservationId}", reservationId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(204));
        }

        @Test
        @DisplayName("Should get 404 when supplying an invalid reservation id")
        void shouldGet404WhenSupplyingAnInvalidReservationId() throws Exception {
            mockMvc.perform(delete("/reservations/{reservationId}", UUID.randomUUID())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(404))
                    .andExpect(jsonPath("$.message").value("Reservation not found"));
        }

        @Test
        @DisplayName("Should get 400 when supplying an already cancelled reservation id")
        void shouldGet400WhenSupplyingAnAlreadyCancelledReservationId() throws Exception {
            final var reservationId = createReservation();

            mockMvc.perform(delete("/reservations/{reservationId}", reservationId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(204));

            mockMvc.perform(delete("/reservations/{reservationId}", reservationId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(400))
                    .andExpect(jsonPath("$.message").value("Reservation already cancelled"));
        }
    }

    private UUID createReservation() {
        final var reservation = Reservation.builder()
                .id(UUID.randomUUID())
                .arrivalDate(LocalDate.now().plusDays(1))
                .departureDate(LocalDate.now().plusDays(2))
                .active(true)
                .userInformation(UserInformation.of("test@email.com", "John Doe"))
                .build();

       return reservationRepository.save(reservation).getId();
    }

}
