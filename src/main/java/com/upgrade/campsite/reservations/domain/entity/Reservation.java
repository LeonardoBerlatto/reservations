package com.upgrade.campsite.reservations.domain.entity;


import com.upgrade.campsite.reservations.domain.vo.UserInformation;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


@Data
@Builder
@Entity
@Table(name = "reservations")
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    private UUID id;
    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "user_name"))
    @AttributeOverride(name = "email", column = @Column(name = "user_email"))
    private UserInformation userInformation;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private boolean active;

    public boolean containsDate(LocalDate date) {
        return !date.isBefore(arrivalDate) && date.isBefore(departureDate);
    }
}
