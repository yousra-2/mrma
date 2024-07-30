package com.mrm.app.validators;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.mrm.app.exceptions.ValidationException;
import com.mrm.app.models.RegistrationRequest;
import com.mrm.app.models.UpdateUserRequest;
import sun.util.locale.LocaleUtils;

@Service
public class UserValidator {
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public void validate(UpdateUserRequest user) {
        if (isEmpty(user.getUsername())) {
            throw new ValidationException("Username is missing!");
        }
        if (isEmpty(user.getEmail()) && isEmptyList(user.getRoles()) && user.getActive() == null) {
            throw new ValidationException("At least one filed should be provided!");
        }
        if (user.getEmail() != null && !isValidEmail(user.getEmail())) {
            throw new ValidationException("Provided email is not valid!");
        }
    }

    public void validate(RegistrationRequest user) {
        if (isEmpty(user.getUsername())) {
            throw new ValidationException("Username is missing!");
        }
        if (isEmpty(user.getPassword())) {
            throw new ValidationException("Password is missing!");
        }
        if (isEmpty(user.getEmail())) {
            throw new ValidationException("Email is missing!");
        }
        if (user.getEmail() != null && !isValidEmail(user.getEmail())) {
            throw new ValidationException("Provided email is not valid!");
        }
        if (isEmpty(user.getFirstname())) {
            throw new ValidationException("Firstname is missing!");
        }
        if (isEmpty(user.getLastname())) {
            throw new ValidationException("Lastname is missing!");
        }
        if (isEmpty(user.getRole())) {
            throw new ValidationException("Role is missing!");
        }
    }

    private static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    private static boolean isEmptyList(List<String> roles) {
        return roles == null || roles.isEmpty();
    }

    public boolean validate(String username) {
        if (!isEmpty(username)) {
           return TRUE ;
        } else {
            return FALSE;
        }
    }


   /* public void validate(String username) {
        if (isEmpty(username) ){
            users = userRepository.findByUsername(username.get());
        } else {
            users = userRepository.findAll();
        }
    }*/
}
