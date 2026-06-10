package com.brainspark.pulsereport.platform.auditlogs.interfaces.REST;

import com.brainspark.pulsereport.platform.auditlogs.application.commandservices.AuditLogCommandService;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.aggregates.AuditLog;
import com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.resources.CreateAuditLogResource;
import com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.transform.CreateAuditLogCommandFromResourceAssembler;
import com.brainspark.pulsereport.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for the auditlogs bounded context.
 * Exposes append-only clinical audit log operations.
 */
@RestController
@RequestMapping(value = "/api/v1/audit-logs", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Audit Logs", description = "Clinical audit trail — append-only")
public class AuditLogsController {

    private final AuditLogCommandService auditLogCommandService;

    /**
     * Creates an immutable clinical audit log entry.
     *
     * @param resource validated request body
     * @return 201 Created with the persisted audit log id, or an error response
     */
    @PostMapping
    @Operation(
            summary = "Create audit log entry",
            description = "Records an immutable clinical audit log entry for a relevant action.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Audit log entry created"),
                    @ApiResponse(responseCode = "400", description = "Validation error"),
                    @ApiResponse(responseCode = "500", description = "Unexpected server error")
            }
    )
    public ResponseEntity<?> createAuditLog(@Valid @RequestBody CreateAuditLogResource resource) {
        var command = CreateAuditLogCommandFromResourceAssembler.toCommandFromResource(resource);
        var result  = auditLogCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AuditLogsController::toAuditLogResponse,
                HttpStatus.CREATED
        );
    }

    // Private helpers

    private static AuditLogResponse toAuditLogResponse(AuditLog log) {
        return new AuditLogResponse(
                log.getId(),
                log.getResponsibleUserId().value(),
                log.getAuditedEntityType().name(),
                log.getAuditedEntityId(),
                log.getActionType().name(),
                log.getOccurredAt().toString(),
                log.getDetail()
        );
    }

    /**
     * Inline response record — keeps the controller self-contained without
     * creating a resource class for read-only response data.
     */
    record AuditLogResponse(
            Long id,
            Long responsibleUserId,
            String auditedEntityType,
            Long auditedEntityId,
            String actionType,
            String occurredAt,
            String detail
    ) {}
}