package com.brainspark.pulsereport.platform.criticalevents.interfaces.rest.transform;

import com.brainspark.pulsereport.platform.criticalevents.domain.model.commands.CloseAlertCommand;
import com.brainspark.pulsereport.platform.criticalevents.interfaces.rest.resources.CloseAlertResource;

public final class CloseAlertCommandFromResourceAssembler {

    private CloseAlertCommandFromResourceAssembler() {
    }

    public static CloseAlertCommand toCommandFromResource(Long alertId, CloseAlertResource resource) {
        return new CloseAlertCommand(
                alertId,
                resource.closedBy(),
                resource.resolutionNotes()
        );
    }
}
