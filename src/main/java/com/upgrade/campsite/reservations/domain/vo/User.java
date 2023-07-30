package com.upgrade.campsite.reservations.domain.vo;


import lombok.Getter;

public record User(
        String email,
        String name
) {
    public static User of(String email, String name) {
        return new User(email, name);
    }
}
