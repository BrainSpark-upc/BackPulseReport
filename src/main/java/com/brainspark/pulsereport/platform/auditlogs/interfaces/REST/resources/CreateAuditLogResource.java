package com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.resources;

import com.brainspark.pulsereport.platform.auditlogs.domain.model.valueobjects.AuditActionType;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.valueobjects.AuditedEntityType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.Instant;

/**
 * Request DTO for POST /api/v1/audit-logs.
 *
 * @param responsibleUserId  ID of the user who performed the action (required)
 * @param auditedEntityType  Type of clinical entity affected (required)
 * @param auditedEntityId    ID of the specific entity instance (required)
 * @param actionType         Type of clinical action (required)
 * @param occurredAt         Timestamp of the action; server uses Instant.now() if omitted
 * @param detail             Optional brief description (max 500 chars)
 */
public record CreateAuditLogResource(

        @NotNull(message = "responsibleUserId is required")
        @Positive(message = "responsibleUserId must be a positive number")
        Long responsibleUserId,

        @NotNull(message = "auditedEntityType is required")
        AuditedEntityType auditedEntityType,

        @NotNull(message = "auditedEntityId is required")
        @Positive(message = "auditedEntityId must be a positive number")
        Long auditedEntityId,

        @NotNull(message = "actionType is required")
        AuditActionType actionType,

        Instant occurredAt,

        @Size(max = 500, message = "detail must not exceed 500 characters")
        String detail
) {}