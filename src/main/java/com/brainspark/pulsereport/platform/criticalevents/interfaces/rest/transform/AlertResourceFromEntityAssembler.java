package com.brainspark.pulsereport.platform.criticalevents.interfaces.rest.transform;

import com.brainspark.pulsereport.platform.criticalevents.domain.model.aggregates.Alert;
import com.brainspark.pulsereport.platform.criticalevents.interfaces.rest.resources.AlertResource;

public final class AlertResourceFromEntityAssembler {

    private AlertResourceFromEntityAssembler() {
    }

    public static AlertResource toResourceFromEntity(Alert entity) {
        return new AlertResource(
                entity.getId(),
                entity.getPatientId(),
                entity.getType(),
                entity.getSeverity(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getTriggeredBy(),
                entity.getAttendedBy(),
                entity.getAttendedAt(),
                entity.getClosedBy(),
                entity.getResolutionNotes(),
                entity.getClosedAt()
        );
    }
}
