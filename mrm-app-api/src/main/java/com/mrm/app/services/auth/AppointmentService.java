package com.mrm.app.services.auth;

import com.mrm.app.entities.AppointmentEntity;
import com.mrm.app.models.AppointmentRequest;
import com.mrm.app.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public AppointmentEntity createAppointment(AppointmentRequest appointmentRequest) {
        AppointmentEntity appointment = new AppointmentEntity();
        //appointment.setDate(appointmentRequest.getDate());
        appointment.setDescription(appointmentRequest.getDescription());
        appointment.setPatientId(appointmentRequest.getPatientId());
        appointment.setDoctorId(appointmentRequest.getDoctorId());
        System.out.println(appointmentRequest.getDoctorId());
        return appointmentRepository.save(appointment);
    }
}
