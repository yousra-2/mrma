package com.mrm.app.resources;

import com.mrm.app.handlers.UsersApi;
import com.mrm.app.models.AuthenticationRequest;
import com.mrm.app.models.AuthenticationResponse;
import com.mrm.app.models.InlineResponse200;
import com.mrm.app.models.InlineResponse2001;
import com.mrm.app.models.InlineResponse2002;
import com.mrm.app.models.InlineResponse2003;
import com.mrm.app.models.RegistrationRequest;
import com.mrm.app.models.UserEntity;
import com.mrm.app.repositories.UserRepository;
import com.mrm.app.services.auth.JwtService;
import com.mrm.app.services.auth.UserService;
import com.mrm.app.services.auth.models.Token;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;


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
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest authRequest) {
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            System.out.println(authRequest.getUsername() + authRequest.getPassword());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        Token token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(
            new AuthenticationResponse()
                .token(token.getAccessToken())
            .expiresAt(epochMillis(token.getExpiresAt()))
            .expiresIn(token.getExpiresIn().toMillis())
        );
    }

    @Override
    public ResponseEntity<InlineResponse200> addUser(RegistrationRequest registrationRequest) {
        return null;
    }

    @Override
    public ResponseEntity<InlineResponse2002> archiveUser(String username) {
        return null;
    }



    @Override
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return null;
    }

    @Override
    public ResponseEntity<List<UserEntity>> searchUsers(String query) {
        return null;
    }

    @Override
    public ResponseEntity<InlineResponse2003> unArchiveUser(String username) {
        return null;
    }

    @Override
    public ResponseEntity<InlineResponse2001> updateUser(String username, UserEntity userEntity) {
        return null;
    }

    /*@PostMapping("/users")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        // Check if username or email already exists
        Optional<UserEntity> existingUserByUsername = userRepository.findByUsername(registrationRequest.getUsername());
        Optional<UserEntity> existingUserByEmail = userRepository.findByEmail(registrationRequest.getEmail());

        if (existingUserByUsername.isPresent() || existingUserByEmail.isPresent()) {
            return ResponseEntity.badRequest().body("Username or email already exists");
        }

        // Create new user entity
        UserEntity user = new UserEntity();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword())); // Encrypt password
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setEmail(registrationRequest.getEmail());
        user.setActive(true); // Optionally set user as active
        user.setRole(registrationRequest.getRole()); // Set role from registration request

        // Save user to database
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }


    @PutMapping("/users/{username}")
    public ResponseEntity<String> updateUser(@PathVariable String username, @Valid @RequestBody UserEntity userDetails) {
        Optional<UserEntity> existingUserOptional = userService.findByUsername(username);

        if (existingUserOptional.isPresent()) {
            UserEntity existingUser = existingUserOptional.get();

            // Check if password is provided and not empty before setting it
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            }

            // Update other fields
            existingUser.setFirstName(userDetails.getFirstName());
            existingUser.setLastName(userDetails.getLastName());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setActive(userDetails.isActive());
            existingUser.setAuthorities(userDetails.getAuthorities());
            existingUser.setRole(userDetails.getRole());

            userService.save(existingUser);
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PutMapping("users/{username}/archive")
    public ResponseEntity<String> archiveUser(@PathVariable String username) {
        Optional<UserEntity> existingUser = userService.findByUsername(username);
        if (existingUser.isPresent()) {
            UserEntity user = existingUser.get();
            user.setActive(false);
            userService.save(user);
            return ResponseEntity.ok("User archived successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("users/{username}/unArchive")
    public ResponseEntity<String> unArchiveUser(@PathVariable String username) {
        Optional<UserEntity> existingUser = userService.findByUsername(username);
        if (existingUser.isPresent()) {
            UserEntity user = existingUser.get();
            user.setActive(true);
            userService.save(user);
            return ResponseEntity.ok("User unarchived successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        long totalUsers = users.size();

        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("totalUsers", totalUsers);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/users/search")
    public ResponseEntity<List<UserEntity>> searchUsers(@RequestParam String query) {

        List<UserEntity> users = userRepository.findAll().stream()
                .filter(user -> user.getUsername().toLowerCase().contains(query.toLowerCase()) ||
                        user.getFirstName().toLowerCase().contains(query.toLowerCase()) ||
                        user.getLastName().toLowerCase().contains(query.toLowerCase()) ||
                        user.getEmail().toLowerCase().contains(query.toLowerCase()) ||
                        user.getRole().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }



   */

    private static long epochMillis(LocalDateTime dateTime) {
        return dateTime
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli();
    }
}
