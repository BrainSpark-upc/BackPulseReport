package com.brainspark.pulsereport.platform.criticalevents.domain.model.commands;

public record AttendAlertCommand(
        Long alertId,
        String attendedBy
) {
}
