package com.mrm.app.services.auth;

import com.mrm.app.entities.UserEntity;
import com.mrm.app.services.auth.models.DefaultUserDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    private Logger log = LoggerFactory.getLogger(DefaultUserDetailsService.class);
    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userService.findByUsername(username);
        if (user.isPresent() && !user.get().isActive()) {
            log.warn(username.concat(" is inactive"));
            throw new UsernameNotFoundException(username.concat(" is inactive"));
        }
        return user.map(DefaultUserDetailsService::from)
                .orElseThrow(() -> new UsernameNotFoundException(username.concat(" Not found")));
    }

    private static DefaultUserDetails from(UserEntity user) {
        return DefaultUserDetails.from(user.getUsername(),
                user.getPassword(),
                user.isActive(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAuthorities()
        );
    }
}
