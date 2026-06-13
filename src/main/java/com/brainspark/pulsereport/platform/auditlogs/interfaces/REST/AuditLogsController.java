package com.brainspark.pulsereport.platform.auditlogs.interfaces.REST;

import com.brainspark.pulsereport.platform.auditlogs.domain.services.AuditLogCommandService;
import com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.resources.AuditLogResource;
import com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.resources.CreateAuditLogResource;
import com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.transform.AuditLogResourceFromEntityAssembler;
import com.brainspark.pulsereport.platform.auditlogs.interfaces.REST.transform.CreateAuditLogCommandFromResourceAssembler;
import com.brainspark.pulsereport.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for the auditlogs bounded context.
 */
@RestController
@RequestMapping(value = "/api/v1/audit-logs", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Audit Logs", description = "Clinical traceability (append-only audit trail)")
public class AuditLogsController {

    private final AuditLogCommandService auditLogCommandService;

    @PostMapping
    @Operation(
            summary = "Create an audit log entry",
            description = "Persists a new, immutable audit log entry for a clinical action."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Audit log entry created",
                    content = @Content(schema = @Schema(implementation = AuditLogResource.class))),
            @ApiResponse(responseCode = "400", description = "Validation error, missing or invalid fields",
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
}