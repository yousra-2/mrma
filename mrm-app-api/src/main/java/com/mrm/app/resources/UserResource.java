package com.mrm.app.resources;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mrm.app.converters.UserConverter;
import com.mrm.app.entities.UserEntity;
import com.mrm.app.handlers.UsersApi;
import com.mrm.app.models.AuthenticationRequest;
import com.mrm.app.models.AuthenticationResponse;
import com.mrm.app.models.InlineResponse200;
import com.mrm.app.models.RegistrationRequest;
import com.mrm.app.models.UpdateUserRequest;
import com.mrm.app.models.User;
import com.mrm.app.models.Users;
import com.mrm.app.repositories.UserRepository;
import com.mrm.app.services.auth.JwtService;
import com.mrm.app.services.auth.UserService;
import com.mrm.app.services.auth.models.Token;
import com.mrm.app.validators.PermissionValidator;
import com.mrm.app.validators.UserValidator;


@RestController
@CrossOrigin
public class UserResource implements UsersApi {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private PermissionValidator permissionValidator;
    private static final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest authRequest) {
        try {
            log.info("Trying to login {}", authRequest.getUsername());
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            log.warn("Bad credentials for {}", authRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        Token token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(
            new AuthenticationResponse()
                .token(token.getAccessToken())
            //.expiresAt(epochMillis(token.getExpiresAt()))
            //.expiresIn(token.getExpiresIn().toMillis())
        );
    }

    @Override
    public ResponseEntity<User> addUser(RegistrationRequest registrationRequest) {
        permissionValidator.onlyAdmin(getCurrentHttpRequest());
        userValidator.validate(registrationRequest);
        UserEntity entity = userService.addUser(registrationRequest);
        return ResponseEntity.ok(UserConverter.convert(entity));
    }

    @Override
    public ResponseEntity<User> updateUser(UpdateUserRequest updateUserRequest) {
        userValidator.validate(updateUserRequest);
        Optional<UserEntity> updated = userService.update(updateUserRequest);
        if (!updated.isPresent()) {
            log.warn("Trying to update non-existing user {}!", updateUserRequest.getUsername());
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserConverter.convert(updated.get()));
    }

    @Override
    public ResponseEntity<Users> findUsers(String username) {
        return null;
    }

    private static long epochMillis(LocalDateTime dateTime) {
        return dateTime
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli();
    }

    private HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            return attrs.getRequest();
        }
        return null;
    }
}
