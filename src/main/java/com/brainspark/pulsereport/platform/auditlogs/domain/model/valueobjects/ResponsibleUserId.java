package com.brainspark.pulsereport.platform.auditlogs.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ResponsibleUserId(Long value) {
    public ResponsibleUserId{
        if (value == null || value <= 0){
            throw new IllegalArgumentException("ResponsibleUserId must be a positive non-null value");
        }
    }
}
