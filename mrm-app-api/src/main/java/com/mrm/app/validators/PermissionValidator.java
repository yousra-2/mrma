package com.mrm.app.validators;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrm.app.constants.Headers;
import com.mrm.app.constants.Roles;
import com.mrm.app.entities.UserEntity;
import com.mrm.app.exceptions.PermissionException;
import com.mrm.app.exceptions.ValidationException;
import com.mrm.app.services.auth.JwtService;
import com.mrm.app.services.auth.UserService;

@Service
public class PermissionValidator {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    public void onlyAdmin(HttpServletRequest request) {
        String username = extractUsername(request);
        Optional<UserEntity> optionalUser = userService.findByUsername(username);
        String role = optionalUser.get().getRole();
        if (Roles.fromRaw(role) != Roles.HR) {
            throw new PermissionException("HR role required!");
        }
    }


    private String extractUsername(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(Headers.AUTHORIZATION_HEADER);
        String jwt = Headers.extractJwt(authorizationHeader);
        return jwtService.extractUsername(jwt);
    }
}
