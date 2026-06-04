package com.brainspark.pulsereport.platform.vitalsigns.domain.model.queries;

public record GetVitalSignRecordsByPatientIdQuery(Long patientId) {
    public GetVitalSignRecordsByPatientIdQuery {
        if (patientId == null || patientId <= 0) {
            throw new IllegalArgumentException("Patient id must be greater than zero");
        }
    }
}
