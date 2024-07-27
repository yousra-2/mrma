package com.mrm.app.validators;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.mrm.app.exceptions.ValidationException;
import com.mrm.app.models.RegistrationRequest;
import com.mrm.app.models.UpdateUserRequest;

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


}
