package com.mrm.app.services.auth;

import com.mrm.app.entities.UserEntity;

import java.util.Optional;

public interface IUserService {
    Optional<UserEntity> findById(String id);

    Optional<UserEntity> findByUsername(String username);
}
