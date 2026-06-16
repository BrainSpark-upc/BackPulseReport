package com.brainspark.pulsereport.platform.handover.domain.model.commands;

public record AcknowledgeHandoverCommand(Long handoverId, Long incomingNurseId, String additionalNotes) {

    public AcknowledgeHandoverCommand {
        if (handoverId == null) {
            throw new IllegalArgumentException("handoverId cannot be null");
        }
        if (incomingNurseId == null) {
            throw new IllegalArgumentException("incomingNurseId cannot be null");
        }
    }
}
