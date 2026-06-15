package com.brainspark.pulsereport.platform.auditlogs.interfaces.REST;

import com.brainspark.pulsereport.platform.auditlogs.domain.services.AuditLogCommandService;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.queries.GetAuditLogsQuery;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.valueobjects.AuditActionType;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.valueobjects.AuditedEntityType;
import com.brainspark.pulsereport.platform.auditlogs.domain.services.AuditLogCommandService;
import com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.resources.AuditLogResource;
import com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.resources.CreateAuditLogResource;
import com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.resources.AuditLogFilterResource;
import com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.resources.PagedResult;
import com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.transform.AuditLogResourceFromEntityAssembler;
import com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.transform.CreateAuditLogCommandFromResourceAssembler;
import com.brainspark.pulsereport.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.brainspark.pulsereport.platform.auditlogs.application.queryservices.AuditLogQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

/**
 * REST controller for the auditlogs bounded context.
 */
@RestController
@RequestMapping(value = "/api/v1/audit-logs", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Audit Logs", description = "Clinical traceability (append-only audit trail)")
public class AuditLogsController {

    private final AuditLogCommandService auditLogCommandService;
    private final AuditLogQueryService auditLogQueryService;

    @PostMapping
    @Operation(
            summary = "Create an audit log entry",
            description = "Persists a new, immutable audit log entry for a clinical action."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Audit log entry created",
                    content = @Content(schema = @Schema(implementation = AuditLogResource.class))),
            @ApiResponse(responseCode = "400", description = "Validation error, missing or invalid field(s)",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content)
    })
    public ResponseEntity<?> createAuditLog(@Valid @RequestBody CreateAuditLogResource resource) {
        var command = CreateAuditLogCommandFromResourceAssembler.toCommandFromResource(resource);
        var result  = auditLogCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AuditLogResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(
            summary = "List audit log entries",
            description = "Returns a paginated, chronologically ordered list of audit log " +
                    "entries. All filters are optional and can be combined freely. This endpoint is designed " +
                    "to be reusable for a patient timeline or an entity timeline " +
                    "(filter by entityType/entityId)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paginated list of audit log entries",
                    content = @Content(schema = @Schema(implementation = PagedResult.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter or pagination parameters",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content)
    })
    public ResponseEntity<?> getAuditLogs(
            @Parameter(description = "Filter by patient identifier", example = "10")
            @RequestParam(required = false) @Nullable Long patientId,

            @Parameter(description = "Filter by audited entity type", example = "VITAL_SIGNS")
            @RequestParam(required = false) @Nullable AuditedEntityType entityType,

            @Parameter(description = "Filter by audited entity identifier", example = "9fr4l2")
            @RequestParam(required = false) @Nullable String entityId,

            @Parameter(description = "Filter by action type", example = "CREATE")
            @RequestParam(required = false) @Nullable AuditActionType actionType,

            @Parameter(description = "Filter by the staff member who performed the action", example = "nurse-user-31")
            @RequestParam(required = false) @Nullable String performedBy,

            @Parameter(description = "Inclusive lower bound for performedAt", example = "2026-02-14T00:00:00Z")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Nullable Instant from,

            @Parameter(description = "Inclusive upper bound for performedAt", example = "2026-12-31T23:59:59Z")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Nullable Instant to,

            @Parameter(description = "Zero based page index", example = "0")
            @RequestParam(required = false, defaultValue = "0") Integer page,

            @Parameter(description = "Page size (max 200)", example = "20")
            @RequestParam(required = false, defaultValue = "20") Integer size
    ) {
        var filter = new AuditLogFilterResource(
                patientId, entityType, entityId, actionType, performedBy, from, to, page, size
        );
        var query = toQueryFromFilter(filter);
        var result = auditLogQueryService.handle(query);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                resultPage -> PagedResult.from(resultPage, AuditLogResourceFromEntityAssembler::toResourceFromEntity),
                HttpStatus.OK
        );
    }

    private GetAuditLogsQuery toQueryFromFilter(AuditLogFilterResource filter) {
        return new GetAuditLogsQuery(
                filter.patientId(),
                filter.entityType(),
                filter.entityId(),
                filter.actionType(),
                filter.performedBy(),
                filter.from(),
                filter.to(),
                filter.page(),
                filter.size()
        );
    }
}