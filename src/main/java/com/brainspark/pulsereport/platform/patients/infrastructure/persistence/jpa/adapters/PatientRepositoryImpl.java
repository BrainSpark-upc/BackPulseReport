package com.brainspark.pulsereport.platform.patients.infrastructure.persistence.jpa.adapters;

import com.brainspark.pulsereport.platform.patients.domain.model.aggregates.Patient;
import com.brainspark.pulsereport.platform.patients.domain.repositories.PatientRepository;
import com.brainspark.pulsereport.platform.patients.infrastructure.persistence.jpa.assemblers.PatientPersistenceAssembler;
import com.brainspark.pulsereport.platform.patients.infrastructure.persistence.jpa.repositories.PatientPersistenceRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PatientRepositoryImpl implements PatientRepository {

    private final PatientPersistenceRepository patientPersistenceRepository;

    public PatientRepositoryImpl(PatientPersistenceRepository patientPersistenceRepository) {
        this.patientPersistenceRepository = patientPersistenceRepository;
    }

    @Override
    public Patient save(Patient patient) {
        var entity = PatientPersistenceAssembler.toPersistenceFromDomain(patient);
        var savedEntity = patientPersistenceRepository.save(entity);

        return PatientPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return patientPersistenceRepository.existsByDocumentNumber(documentNumber);
    }
}
