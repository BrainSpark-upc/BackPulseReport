package com.brainspark.pulsereport.platform.auditlogs.domain.model.commands;

import com.brainspark.pulsereport.platform.auditlogs.domain.model.valueobjects.AuditActionType;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.valueobjects.AuditedEntityType;

import java.time.Instant;

public record CreateAuditLogCommand(
        Long responsibleUserId, AuditedEntityType auditedEntityType,
        Long auditedEntityId, AuditActionType actionType,
        Instant occurredAt, String detail) {
}
