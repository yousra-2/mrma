package com.mrm.app.services.auth;

import com.mrm.app.entities.UserEntity;
import com.mrm.app.exceptions.ValidationException;
import com.mrm.app.models.RegistrationRequest;
import com.mrm.app.models.UpdateUserRequest;
import com.mrm.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserEntity> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserEntity> update(UpdateUserRequest user) {
        Optional<UserEntity> optionalEntity = findByUsername(user.getUsername());
        if (!optionalEntity.isPresent()) {
            return Optional.empty();
        }
        UserEntity entity = optionalEntity.get();
        if (user.getActive() != null) {
            entity.setActive(user.getActive());
        }
        if (user.getEmail() != null) {
            entity.setEmail(user.getEmail());
        }
        return Optional.of(save(entity));
    }

    public UserEntity addUser(RegistrationRequest user) {
        Optional<UserEntity> optionalEntity = findByUsername(user.getUsername());
        if (optionalEntity.isPresent()) {
            throw new ValidationException(String.format("Username %s already exists!", user.getUsername()));
        }
        Optional<UserEntity> optionalEnt = findByEmail(user.getEmail());
        if (optionalEnt.isPresent()) {
            throw new ValidationException(String.format("Email %s already exists!", user.getEmail()));
        }
        UserEntity entity = new UserEntity();
        entity.setEmail(user.getEmail());
        entity.setUsername(user.getUsername());
        entity.setActive(true);
        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        entity.setFirstName(user.getFirstname());
        entity.setLastName(user.getLastname());
        entity.setRole(user.getRole());
        save(entity);
        return entity;
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public List<UserEntity> findUsing(String username) {
        if (username == null) {
            return userRepository.findAll();
        }
        Optional<UserEntity> optional = findByUsername(username);
        return optional.map(Collections::singletonList)
                .orElse(Collections.emptyList());
    }
    private UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }


}
