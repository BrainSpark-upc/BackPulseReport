package com.brainspark.pulsereport.platform.patients.domain.repositories;

import com.brainspark.pulsereport.platform.patients.domain.model.aggregates.Patient;

public interface PatientRepository {

    Patient save(Patient patient);

    boolean existsByDocumentNumber(String documentNumber);
}
