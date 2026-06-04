package com.brainspark.pulsereport.platform.vitalsigns.domain.model.valueobjects;

public record BloodPressure(Integer systolic, Integer diastolic) {
    public BloodPressure {
        if (systolic == null || systolic <= 0) {
            throw new IllegalArgumentException("Systolic blood pressure must be a positive integer.");
        }
        if (diastolic == null || diastolic <= 0) {
            throw new IllegalArgumentException("Diastolic blood pressure must be a positive integer.");
        }
        if (systolic <= diastolic) {
            throw new IllegalArgumentException("Systolic pressure must be greater than diastolic pressure");
        }
    }
}
