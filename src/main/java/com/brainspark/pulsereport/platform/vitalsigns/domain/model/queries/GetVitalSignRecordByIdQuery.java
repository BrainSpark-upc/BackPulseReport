package com.brainspark.pulsereport.platform.vitalsigns.domain.model.queries;

public record GetVitalSignRecordByIdQuery(Long vitalSignRecordId) {
    public GetVitalSignRecordByIdQuery {
        if (vitalSignRecordId == null || vitalSignRecordId <= 0) {
            throw new IllegalArgumentException("Vital sign record id must be greater than zero");
        }
    }
}
