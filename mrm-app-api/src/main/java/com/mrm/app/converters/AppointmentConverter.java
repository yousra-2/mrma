package com.mrm.app.converters;

import com.mrm.app.entities.AppointmentEntity;
import com.mrm.app.entities.UserEntity;
import com.mrm.app.models.Appointment;

import com.mrm.app.models.Appointments;
import com.mrm.app.models.User;
import com.mrm.app.models.Users;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentConverter {
    public Appointment convert(AppointmentEntity entity) {
        Appointment appointment = new Appointment();
        appointment.setDescription(entity.getDescription());
        appointment.setDoctorId(entity.getDoctorId());
        appointment.setUsername(entity.getUsername());
        appointment.setDate(entity.getDate());
        appointment.setStatus(entity.getStatus());
        return appointment;
    }
    public Appointments convert(List<AppointmentEntity> entities) {
        List<Appointment> appointments = entities.stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return new Appointments().appointments(appointments);
    }

}
