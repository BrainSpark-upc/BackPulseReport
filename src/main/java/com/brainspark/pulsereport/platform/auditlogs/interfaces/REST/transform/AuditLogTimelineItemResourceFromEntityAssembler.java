package com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.transform;

import com.brainspark.pulsereport.platform.auditlogs.domain.model.aggregates.AuditLog;
import com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.resources.AuditLogTimelineItemResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.util.Map;
@Slf4j
public final class AuditLogTimelineItemResourceFromEntityAssembler {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private AuditLogTimelineItemResourceFromEntityAssembler(){}

    public static AuditLogTimelineItemResource toResourceFromEntity(AuditLog entity){
        return new AuditLogTimelineItemResource(
                entity.getId(),
                entity.getEntityType(),
                entity.getEntityId(),
                entity.getActionType(),
                entity.getPerformedBy(),
                entity.getPerformedAt(),
                deserializeMetadata(entity.getMetadata().getValue())
        );
    }
    @Nullable
    private static Map<String, Object> deserializeMetadata(@Nullable String metadataJson){
        if (metadataJson == null || metadataJson.isBlank())
            return null;
        try{
            return OBJECT_MAPPER.readValue(metadataJson, new TypeReference<Map<String, Object>>(){});

        } catch (JsonProcessingException e){
            log.warn("Stored audit log metadata is not a valid JSON object. Returning empty map");
            return Map.of();
        }
    }
}

