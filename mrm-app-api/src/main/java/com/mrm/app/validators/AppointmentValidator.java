package com.mrm.app.validators;

import com.mrm.app.exceptions.ValidationException;
import com.mrm.app.models.AppointmentDateRequest;
import com.mrm.app.models.AppointmentRequest;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class AppointmentValidator {

    public void validate(AppointmentRequest app) {
        if (app.getDate()== null) {
            throw new ValidationException("The date/time is not provided .");
        } else {
            OffsetDateTime now = OffsetDateTime.now();
            if (app.getDate().isBefore(OffsetDateTime.from(now))) {
                throw new ValidationException("The date/time has already passed.");
            }
        }
        if (isEmpty(app.getDoctorId())) {
            throw new ValidationException("Username is missing!");
        }
        if (isEmpty(app.getDoctorId())) {
                throw new ValidationException("Doctor id is missing!");
        }
        /*if (isEmpty(app.getPatientId())) {
            throw new ValidationException("Pietent id is missing!");
        }*/
        if (isEmpty(app.getDescription())) {
            throw new ValidationException("Description is missing!");
        }
    }

    public void validate(AppointmentDateRequest app) {
        if (app.getDate()== null) {
            throw new ValidationException("The date/time is not provided .");
        } else {
            OffsetDateTime now = OffsetDateTime.now();
            if (app.getDate().isBefore(OffsetDateTime.from(now))) {
                throw new ValidationException("The date/time has already passed.");
            }
        }
    }



    public void validateNotEmpty(String username, String appointmentId) {
        if (username != null && username.isEmpty() ) {
            throw new ValidationException("Optional username should not be empty!");
        }
        if (appointmentId != null && appointmentId.isEmpty() ) {
            throw new ValidationException("Optional appointment id should not be empty!");
        }

    }
}
