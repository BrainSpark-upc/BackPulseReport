package com.brainspark.pulsereport.platform.patients.interfaces.rest.transform;

import com.brainspark.pulsereport.platform.patients.domain.model.commands.UpdatePatientCommand;
import com.brainspark.pulsereport.platform.patients.interfaces.rest.resources.UpdatePatientResource;

public final class UpdatePatientCommandFromResourceAssembler {

    private UpdatePatientCommandFromResourceAssembler() {
    }

    public static UpdatePatientCommand toCommandFromResource(Long patientId, UpdatePatientResource resource) {
        return new UpdatePatientCommand(
                patientId,
                resource.firstName(),
                resource.lastName(),
                resource.documentNumber(),
                resource.birthDate(),
                resource.gender(),
                resource.diagnosis(),
                resource.roomNumber(),
                resource.bedNumber(),
                resource.attendingPhysician(),
                resource.status(),
                resource.admissionDate()
        );
    }
}