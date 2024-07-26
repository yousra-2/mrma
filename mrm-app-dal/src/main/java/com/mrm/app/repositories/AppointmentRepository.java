package com.mrm.app.repositories;

import com.mrm.app.entities.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findByPatientId(String patientId);
    List<AppointmentEntity> findByDateAndDoctorId(LocalDateTime date, String doctorId);
}
