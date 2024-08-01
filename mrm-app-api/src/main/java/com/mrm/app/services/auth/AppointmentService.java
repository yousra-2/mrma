package com.mrm.app.services.auth;

import com.mrm.app.entities.AppointmentEntity;
import com.mrm.app.entities.UserEntity;
import com.mrm.app.exceptions.ValidationException;
import com.mrm.app.models.AppointmentDateRequest;
import com.mrm.app.models.AppointmentRequest;
import com.mrm.app.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;



    public AppointmentEntity scheduleAppointment(AppointmentRequest appointmentRequest) {
        Optional <AppointmentEntity> optionalEntity = appointmentRepository.findByDateAndDoctorId(appointmentRequest.getDate(), appointmentRequest.getDoctorId());
        if (optionalEntity.isPresent()) {
            throw new ValidationException(String.format("an appointment already exists with the same date and doctor"));
        }
        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setDate(appointmentRequest.getDate());
        appointment.setDescription(appointmentRequest.getDescription());
        appointment.setUsername(appointmentRequest.getUsername());
        appointment.setDoctorId(appointmentRequest.getDoctorId());
        appointment.setStatus("planned");

        return appointmentRepository.save(appointment);
    }

    public Optional<AppointmentEntity> reschedule(String appointmentId, AppointmentDateRequest app) {

        Optional<AppointmentEntity> optionalEntity = findById(appointmentId);
        if (!optionalEntity.isPresent()) {
            return Optional.empty();
        }
        AppointmentEntity entity = optionalEntity.get();
        if (app.getDate() != null) {
            entity.setDate(app.getDate());
        }
        return Optional.of(appointmentRepository.save(entity));
    }

    private Optional<AppointmentEntity> findById(String appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    public List<AppointmentEntity> findUsing(String username, String appointmentId) {
        if (username == null && appointmentId == null) {
            return appointmentRepository.findAll();
        } else if (username != null) {
            Optional<AppointmentEntity> optional = findByUsername(username);
            return optional.map(Collections::singletonList)
                    .orElse(Collections.emptyList());
        }
        else {
            Optional<AppointmentEntity> optional = findById(appointmentId);
            return optional.map(Collections::singletonList)
                    .orElse(Collections.emptyList());

        }


    }

    private Optional<AppointmentEntity> findByUsername(String username) {
        return appointmentRepository.findByUsername(username);
    }


}
