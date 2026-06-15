package com.brainspark.pulsereport.platform.criticalevents.domain.model.commands;

public record CloseAlertCommand(
        Long alertId,
        String closedBy,
        String resolutionNotes
) {
}
