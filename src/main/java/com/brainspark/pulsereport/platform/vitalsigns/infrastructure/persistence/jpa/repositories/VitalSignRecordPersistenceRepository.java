package com.brainspark.pulsereport.platform.vitalsigns.infrastructure.persistence.jpa.repositories;

import com.brainspark.pulsereport.platform.vitalsigns.infrastructure.persistence.jpa.entities.VitalSignRecordPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VitalSignRecordPersistenceRepository
        extends JpaRepository<VitalSignRecordPersistenceEntity, Long> {

    List<VitalSignRecordPersistenceEntity> findByPatientId(Long patientId);
}