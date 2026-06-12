package com.brainspark.pulsereport.platform.patients.interfaces.rest.transform;

import com.brainspark.pulsereport.platform.patients.domain.model.aggregates.Patient;
import com.brainspark.pulsereport.platform.patients.interfaces.rest.resources.PatientResource;

public final class PatientResourceFromEntityAssembler {

    private PatientResourceFromEntityAssembler() {
    }

    public static PatientResource toResourceFromEntity(Patient entity) {
        return new PatientResource(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getFullName(),
                entity.getDocumentNumber(),
                entity.getBirthDate(),
                entity.getGender(),
                entity.getDiagnosis(),
                entity.getRoomNumber(),
                entity.getBedNumber(),
                entity.getAttendingPhysician(),
                entity.getStatus(),
                entity.getAdmissionDate()
        );
    }
}
