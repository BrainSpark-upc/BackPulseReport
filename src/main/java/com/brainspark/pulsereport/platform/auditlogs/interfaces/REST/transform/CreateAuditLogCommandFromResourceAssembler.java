package com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.transform;

import com.brainspark.pulsereport.platform.auditlogs.domain.model.commands.CreateAuditLogCommand;
import com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.resources.CreateAuditLogResource;

/**
 * Stateless assembler that maps an inbound {@link CreateAuditLogResource} to a domain command.
 */
public final class CreateAuditLogCommandFromResourceAssembler {

    private CreateAuditLogCommandFromResourceAssembler() {}

    public static CreateAuditLogCommand toCommandFromResource(CreateAuditLogResource resource) {
        return new CreateAuditLogCommand(
                resource.patientId(),
                resource.entityType(),
                resource.entityId(),
                resource.actionType(),
                resource.performedBy(),
                resource.performedAt(),
                resource.metadata()
        );
    }
}