package com.upgrade.campsite.reservations.adapter.mapper;

import com.upgrade.campsite.reservations.adapter.rest.representation.UserInfoRepresentation;
import com.upgrade.campsite.reservations.domain.vo.User;
import org.springframework.stereotype.Component;

@Component
public class UserInfoMapper {

    public User toUser(final UserInfoRepresentation userInfoRepresentation) {
        return User.of(userInfoRepresentation.email(), userInfoRepresentation.name());
    }

    public UserInfoRepresentation toRepresentation(final User user) {
        return new UserInfoRepresentation(user.getEmail(), user.getName());
    }
}
