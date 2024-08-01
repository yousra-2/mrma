package com.mrm.app.repositories;

import com.mrm.app.entities.AppointmentEntity;
import com.mrm.app.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

   Optional<AppointmentEntity> findByUsername(String username);

    Optional<AppointmentEntity> findByDateAndDoctorId(OffsetDateTime date, String doctorId);
    Optional<AppointmentEntity> findById(String Id);
}
