package com.upgrade.campsite.reservations.adapter.mapper;

import com.upgrade.campsite.reservations.adapter.rest.representation.UserInfoRepresentation;
import com.upgrade.campsite.reservations.domain.vo.UserInformation;
import org.springframework.stereotype.Component;

@Component
public class UserInfoMapper {

    public UserInformation toUser(final UserInfoRepresentation userInfoRepresentation) {
        return UserInformation.of(userInfoRepresentation.email(), userInfoRepresentation.name());
    }

    public UserInfoRepresentation toRepresentation(final UserInformation userInformation) {
        return new UserInfoRepresentation(userInformation.getEmail(), userInformation.getName());
    }
}
