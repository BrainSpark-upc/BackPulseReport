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
}
