package com.mrm.app.converters;

import java.util.Collections;

import com.mrm.app.entities.UserEntity;
import com.mrm.app.models.User;

public class UserConverter {

    public static User convert(UserEntity entity) {
        User user = new User();
        user.setActive(entity.isActive());
        user.setEmail(entity.getEmail());
        user.setUsername(entity.getUsername());
        user.setRole(entity.getRole());
        user.setFirstname(entity.getFirstName());
        user.setLastname(entity.getLastName());
        return user;
    }
}
