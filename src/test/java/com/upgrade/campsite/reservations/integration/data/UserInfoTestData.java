package com.upgrade.campsite.reservations.integration.data;

import com.upgrade.campsite.reservations.adapter.rest.representation.UserInfoRepresentation;

public class UserInfoTestData {

    public static final String USER_NAME = "John Doe";
    public static final String USER_EMAIL = "test@email.com";

    public static UserInfoRepresentation aUserInfo() {
        return new UserInfoRepresentation(USER_EMAIL, USER_NAME);
    }
}
