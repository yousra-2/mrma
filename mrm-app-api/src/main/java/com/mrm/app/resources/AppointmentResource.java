package com.mrm.app.resources;

import com.mrm.app.converters.AppointmentConverter;
import com.mrm.app.entities.AppointmentEntity;
import com.mrm.app.validators.AppointmentValidator;
import com.mrm.app.validators.PermissionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mrm.app.handlers.AppointmentsApi;
import com.mrm.app.models.Appointment;
import com.mrm.app.models.AppointmentDateRequest;
import com.mrm.app.models.AppointmentRequest;

import com.mrm.app.models.Appointments;
import com.mrm.app.repositories.AppointmentRepository;
import com.mrm.app.services.auth.AppointmentService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
public class AppointmentResource implements AppointmentsApi {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PermissionValidator permissionValidator;
    @Autowired
    private AppointmentValidator appointmentValidator;

    @Autowired
    private AppointmentConverter appointmentConverter;

    private static final Logger log = LoggerFactory.getLogger(AppointmentResource.class);
    @Override
    public ResponseEntity<Appointment> scheduleAppointment(AppointmentRequest appointmentRequest) {
        permissionValidator.onlyAdmin(getCurrentHttpRequest());
        appointmentValidator.validate(appointmentRequest);
        AppointmentEntity entity = appointmentService.scheduleAppointment(appointmentRequest);
        return ResponseEntity.ok(appointmentConverter.convert(entity));
    }

    @Override
    public ResponseEntity<Appointments> getUserAppointments(String username, String appointmentId) {
        permissionValidator.onlyAdmin(getCurrentHttpRequest());
        appointmentValidator.validateNotEmpty(username,appointmentId);
        List<AppointmentEntity> entities = appointmentService.findUsing(username,appointmentId);
        return ResponseEntity.ok(appointmentConverter.convert(entities));
    }

    @Override
    public ResponseEntity<Appointment> rescheduleAppointment(String appointmentId, AppointmentDateRequest appointmentDateRequest) {
        permissionValidator.onlyAdmin(getCurrentHttpRequest());
        appointmentValidator.validate(appointmentDateRequest);
        Optional<AppointmentEntity> updated = appointmentService.reschedule(appointmentId,appointmentDateRequest);
        if (!updated.isPresent()) {
            log.warn("Trying to update non-existing appointment {}!", appointmentId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(appointmentConverter.convert(updated.get()));
    }
    private HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            return attrs.getRequest();
        }
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
