package com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.transform;

import com.brainspark.pulsereport.platform.auditlogs.domain.model.aggregates.AuditLog;
import com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.resources.AuditLogDetailResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.util.Map;

/**
 * This class represents a stateless assembler that maps a persisted {@link AuditLog} entity to the richer {@link AuditLogDetailResource} used by the single-entry detail endpoint.
 * Unlike {@link AuditLogResourceFromEntityAssembler}, this assembler parses the stored metadata JSON string back into a {@code Map<String, Object>} so the frontend can render
 * contextual details directly without an extra parsing step.
 */
@Slf4j
public final class AuditLogDetailResourceFromEntityAssembler {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private AuditLogDetailResourceFromEntityAssembler() {}

    public static AuditLogDetailResource toResourceFromEntity(AuditLog entity) {
        return new AuditLogDetailResource(
                entity.getId(),
                entity.getPatientId(),
                entity.getPatientId() != null,
                entity.getEntityType(),
                entity.getEntityId(),
                entity.getActionType(),
                entity.getPerformedBy(),
                entity.getPerformedAt(),
                deserializeMetadata(entity.getMetadata().getValue()),
                entity.getCreatedAt(),
                entity.getUpdateAt()
        );
    }

    /**
     * Parses the stored metadata JSON string into a {@code Map<String, Object>}.
     * Returns {@code null} when no metadata is present, and an empty map if the stored value is not a valid JSON object (the value is written by our own
     * assembler on creation, but this guards against any future direct writes).
     */
    @Nullable
    private static Map<String, Object> deserializeMetadata(@Nullable String metadataJson) {
        if (metadataJson == null || metadataJson.isBlank()) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(metadataJson, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            log.warn("Stored audit log metadata is not a valid JSON object... returning empty map");
            return Map.of();
        }
    }
}