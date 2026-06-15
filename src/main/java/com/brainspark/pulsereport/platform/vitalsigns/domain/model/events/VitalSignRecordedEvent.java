package com.brainspark.pulsereport.platform.vitalsigns.domain.model.events;

import java.time.LocalDateTime;

public record VitalSignRecordedEvent(Long patientId,
                                     Long nurseId,
                                     LocalDateTime recordedAt) {
}
