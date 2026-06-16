package com.brainspark.pulsereport.platform.handover.interfaces.rest;

import com.brainspark.pulsereport.platform.handover.application.commandservices.HandoverCommandService;
import com.brainspark.pulsereport.platform.handover.application.queryservices.HandoverQueryService;
import com.brainspark.pulsereport.platform.handover.domain.model.aggregates.Handover;
import com.brainspark.pulsereport.platform.handover.domain.model.queries.GetHandoverByIdQuery;
import com.brainspark.pulsereport.platform.handover.interfaces.rest.resources.CreateHandoverResource;
import com.brainspark.pulsereport.platform.handover.interfaces.rest.resources.HandoverResource;
import com.brainspark.pulsereport.platform.handover.interfaces.rest.transform.CreateHandoverCommandFromResourceAssembler;
import com.brainspark.pulsereport.platform.handover.interfaces.rest.transform.HandoverResourceFromEntityAssembler;
import com.brainspark.pulsereport.platform.shared.application.result.ApplicationError;
import com.brainspark.pulsereport.platform.shared.application.result.Result;
import com.brainspark.pulsereport.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes handover resources and handover administration endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/handovers", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Handovers", description = "Handover management endpoints")
public class HandoversController {
    private final HandoverCommandService handoverCommandService;
    private final HandoverQueryService handoverQueryService;

    /**
     * Constructor
     *
     * @param handoverCommandService The {@link HandoverCommandService} instance
     * @param handoverQueryService   The {@link HandoverQueryService} instance
     */
    public HandoversController(HandoverCommandService handoverCommandService, HandoverQueryService handoverQueryService) {
        this.handoverCommandService = handoverCommandService;
        this.handoverQueryService = handoverQueryService;
    }

    /**
     * Create a new handover
     *
     * @param resource The {@link CreateHandoverResource} instance
     * @return The {@link HandoverResource} resource for the created handover
     */
    @PostMapping
    @Operation(summary = "Create a new handover", description = "Creates a new handover with title and description.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Handover created successfully",
                    content = @Content(schema = @Schema(implementation = HandoverResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Handover with the same title already exists")
    })
    public ResponseEntity<?> createHandover(@RequestBody CreateHandoverResource resource) {
        var createHandoverCommand = CreateHandoverCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = handoverCommandService.handle(createHandoverCommand);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                id -> id,
                HttpStatus.CREATED
        );
    }

    /**
     * Get handovers by patient ID
     *
     * @param patientId The patient ID
     * @param startDate Optional start date for filtering (YYYY-MM-DD)
     * @param endDate   Optional end date for filtering (YYYY-MM-DD)
     * @return List of {@link HandoverResource}
     */
    @GetMapping("/patients/{patientId}")
    @Operation(summary = "Get handovers by patient ID", description = "Gets all handovers for a specific patient, with optional date filtering.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Handovers retrieved successfully",
                    content = @Content(schema = @Schema(implementation = HandoverResource.class))
            )
    })
    public ResponseEntity<java.util.List<HandoverResource>> getHandoversByPatientId(
            @org.springframework.web.bind.annotation.PathVariable Long patientId,
            @org.springframework.web.bind.annotation.RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.util.Date startDate,
            @org.springframework.web.bind.annotation.RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.util.Date endDate) {
        
        var query = new com.brainspark.pulsereport.platform.handover.domain.model.queries.GetAllHandoversByPatientIdQuery(patientId, startDate, endDate);
        var handovers = handoverQueryService.handle(query);
        var resources = handovers.stream().map(HandoverResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Get a specific handover by its ID
     *
     * @param handoverId The handover ID
     * @return The detailed {@link com.brainspark.pulsereport.platform.handover.interfaces.rest.resources.HandoverDetailedResource}
     */
    @GetMapping("/{handoverId}")
    @Operation(summary = "Get specific handover details", description = "Gets the complete information of a particular SBAR handover.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Handover details retrieved successfully",
                    content = @Content(schema = @Schema(implementation = com.brainspark.pulsereport.platform.handover.interfaces.rest.resources.HandoverDetailedResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Handover not found")
    })
    public ResponseEntity<com.brainspark.pulsereport.platform.handover.interfaces.rest.resources.HandoverDetailedResource> getHandoverById(
            @org.springframework.web.bind.annotation.PathVariable Long handoverId) {

        var query = new GetHandoverByIdQuery(handoverId);
        var handover = handoverQueryService.handle(query);

        if (handover.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = com.brainspark.pulsereport.platform.handover.interfaces.rest.transform.HandoverDetailedResourceFromEntityAssembler.toResourceFromEntity(handover.get());
        return ResponseEntity.ok(resource);
    }

    /**
     * Acknowledge a handover
     *
     * @param handoverId The handover ID
     * @param resource   The acknowledge resource with incoming nurse ID and notes
     * @return The updated {@link HandoverResource}
     */
    @org.springframework.web.bind.annotation.PatchMapping("/{handoverId}/acknowledge")
    @Operation(summary = "Acknowledge a handover", description = "Allows the incoming nurse to confirm they have read and understood the handover.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Handover acknowledged successfully",
                    content = @Content(schema = @Schema(implementation = HandoverResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Handover not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<HandoverResource> acknowledgeHandover(
            @org.springframework.web.bind.annotation.PathVariable Long handoverId,
            @RequestBody com.brainspark.pulsereport.platform.handover.interfaces.rest.resources.AcknowledgeHandoverResource resource) {
        
        var command = com.brainspark.pulsereport.platform.handover.interfaces.rest.transform.AcknowledgeHandoverCommandFromResourceAssembler.toCommandFromResource(handoverId, resource);
        var result = handoverCommandService.handle(command);
        
        if (result.isFailure()) {
            if (result.error().type().equals(com.brainspark.pulsereport.platform.shared.application.result.ApplicationErrorType.NOT_FOUND)) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
        
        var responseResource = HandoverResourceFromEntityAssembler.toResourceFromEntity(result.value());
        return ResponseEntity.ok(responseResource);
    }
}
