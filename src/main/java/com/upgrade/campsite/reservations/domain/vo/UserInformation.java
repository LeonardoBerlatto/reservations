package com.upgrade.campsite.reservations.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInformation {

    private String email;
    private String name;

    public static UserInformation of(String email, String name) {
        return new UserInformation(email, name);
    }
}
