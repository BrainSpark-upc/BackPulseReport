package com.brainspark.pulsereport.platform.criticalevents.domain.model.queries;

import com.brainspark.pulsereport.platform.criticalevents.domain.exceptions.InvalidAlertException;

public record GetAlertsByPatientIdQuery(Long patientId) {

    public GetAlertsByPatientIdQuery {
        if (patientId == null || patientId <= 0) {
            throw new InvalidAlertException("Patient id must be greater than zero");
        }
    }
}
