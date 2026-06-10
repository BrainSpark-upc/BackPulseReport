package com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.transform;

import com.brainspark.pulsereport.platform.auditlogs.domain.model.commands.CreateAuditLogCommand;
import com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.resources.CreateAuditLogResource;

/**
 * Maps an inbound {@link CreateAuditLogResource} to a {@link CreateAuditLogCommand}.
 */
public final class CreateAuditLogCommandFromResourceAssembler {

    private CreateAuditLogCommandFromResourceAssembler() {}

    public static CreateAuditLogCommand toCommandFromResource(CreateAuditLogResource resource) {
        return new CreateAuditLogCommand(
                resource.responsibleUserId(),
                resource.auditedEntityType(),
                resource.auditedEntityId(),
                resource.actionType(),
                resource.occurredAt(),
                resource.detail()
        );
    }
}


