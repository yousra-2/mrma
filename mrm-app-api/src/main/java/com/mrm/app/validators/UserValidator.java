package com.mrm.app.validators;

import com.mrm.app.exceptions.ValidationException;
import com.mrm.app.models.User;

public class UserValidator {

    public static void validate(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new ValidationException("Username is missing!");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ValidationException("Email is missing!");
        }
    }
}
