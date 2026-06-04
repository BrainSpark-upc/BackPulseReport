package com.brainspark.pulsereport.platform.vitalsigns.domain.model.commands;

import com.brainspark.pulsereport.platform.vitalsigns.domain.model.valueobjects.BloodPressure;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateVitalSignRecordCommand(Long patientId, Long nurseId,
                                           Integer heartRate,
                                           Integer respiratoryRate,
                                           BloodPressure bloodPressure,
                                           Integer oxygenSaturation,
                                           BigDecimal temperature,
                                           LocalDateTime recordedAt) {
    public CreateVitalSignRecordCommand {
        if (patientId == null || patientId <= 0) {
            throw new IllegalArgumentException("Patient id must be greater than zero");
        }

        if (nurseId == null || nurseId <= 0) {
            throw new IllegalArgumentException("Nurse id must be greater than zero");
        }

        if (heartRate == null || heartRate <= 0) {
            throw new IllegalArgumentException("Heart rate must be greater than zero");
        }

        if (respiratoryRate == null || respiratoryRate <= 0) {
            throw new IllegalArgumentException("Respiratory rate must be greater than zero");
        }

        if (bloodPressure == null) {
            throw new IllegalArgumentException("Blood pressure is required");
        }

        if (oxygenSaturation == null || oxygenSaturation < 0 || oxygenSaturation > 100) {
            throw new IllegalArgumentException("Oxygen saturation must be between 0 and 100");
        }

        if (temperature == null || temperature.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Temperature must be greater than zero");
        }
    }
}
