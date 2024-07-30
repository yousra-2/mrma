package com.mrm.app.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mrm.app.entities.UserEntity;
import com.mrm.app.models.User;
import com.mrm.app.models.Users;

@Service
public class UserConverter {

    public User convert(UserEntity entity) {
        User user = new User();
        user.setActive(entity.isActive());
        user.setEmail(entity.getEmail());
        user.setUsername(entity.getUsername());
        user.setRole(entity.getRole());
        user.setFirstname(entity.getFirstName());
        user.setLastname(entity.getLastName());
        return user;
    }

    public Users convert(List<UserEntity> entities) {
        List<User> users = entities.stream()
            .map(this::convert)
            .collect(Collectors.toList());
        return new Users().users(users);
    }
}
