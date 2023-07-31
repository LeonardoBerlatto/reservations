package com.upgrade.campsite.reservations.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String email;
    private String name;

    public static User of(String email, String name) {
        return new User(email, name);
    }
}
