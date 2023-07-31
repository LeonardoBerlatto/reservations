package com.upgrade.campsite.reservations.adapter.rest.representation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserInfoRepresentation(
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Invalid email")
        String email,
        @NotBlank(message = "Name is mandatory")
        String name
) {
}
