package com.brainspark.pulsereport.platform.patients.domain.repositories;

import com.brainspark.pulsereport.platform.patients.domain.model.aggregates.Patient;

import java.util.Optional;

public interface PatientRepository {

    Patient save(Patient patient);

    boolean existsByDocumentNumber(String documentNumber);

    Optional<Patient> findById(Long id);
}
