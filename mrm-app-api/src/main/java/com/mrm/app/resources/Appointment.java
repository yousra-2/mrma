package com.mrm.app.resources;

import com.mrm.app.entities.AppointmentEntity;
import com.mrm.app.handlers.AppointmentsApi;
import com.mrm.app.models.AppointmentDateRequest;
import com.mrm.app.models.AppointmentRequest;
import com.mrm.app.models.AppointmentResponse;
import com.mrm.app.models.Appointments;
import com.mrm.app.repositories.AppointmentRepository;
import com.mrm.app.services.auth.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@Validated
public class Appointment implements AppointmentsApi {
    @Autowired
    private final AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    public ResponseEntity<AppointmentResponse> addAppointment(AppointmentRequest appointmentRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Appointments> getUserappointments(String username, String appointmentId) {
        return null;
    }

    @Override
    public ResponseEntity<com.mrm.app.models.Appointment> updateAppointmentDate(String appointmentId, AppointmentDateRequest appointmentDateRequest) {
        return null;
    }

    /*
    @PostMapping
    public ResponseEntity<String> addAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        List<AppointmentEntity> existingAppointments = new ArrayList<>();//appointmentRepository.findByDateAndDoctorId(appointmentRequest.getDate(), appointmentRequest.getDoctorId());

        if (!existingAppointments.isEmpty()) {
            return ResponseEntity.badRequest().body("Appointment already exists on this date for the selected doctor. Please choose another date.");
        }

        AppointmentEntity appointment = appointmentService.createAppointment(appointmentRequest);

        // Assuming response.getId() will give you the ID of the newly created appointment
        return ResponseEntity.created(URI.create("/appointments/" + appointment.getId()))
                .body("Appointment booked successfully.");
    }


    @Override
    @PutMapping("/{appointmentId}")
    public ResponseEntity<String> updateAppointmentDate(String appointmentId, AppointmentDateRequest appointmentDateRequest) {
        // Retrieve the appointment by ID
        AppointmentEntity existingAppointment = appointmentRepository.findById(Long.valueOf(appointmentId))
                .orElse(null);

        if (existingAppointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Appointment not found.");
        }

        // Check for conflicts with other appointments
        List<AppointmentEntity> conflictingAppointments = new ArrayList<>(); //appointmentRepository.findByDateAndDoctorId(appointmentDateRequest.getDate(), existingAppointment.getDoctorId());

        if (!conflictingAppointments.isEmpty()) {
            return ResponseEntity.badRequest().body("Appointment already exists on this date for the selected doctor. Please choose another date.");
        }

        // Update the appointment date
        //existingAppointment.setDate(appointmentDateRequest.getDate());
        appointmentRepository.save(existingAppointment);

        // Return success message
        return ResponseEntity.ok("Appointment date updated successfully.");
    }
*/
}
