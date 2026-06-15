package com.brainspark.pulsereport.platform.criticalevents.interfaces.rest;

import com.brainspark.pulsereport.platform.criticalevents.application.commandservices.AlertCommandService;
import com.brainspark.pulsereport.platform.criticalevents.interfaces.rest.resources.CreateAlertResource;
import com.brainspark.pulsereport.platform.criticalevents.interfaces.rest.transform.AlertResourceFromEntityAssembler;
import com.brainspark.pulsereport.platform.criticalevents.interfaces.rest.transform.CreateAlertCommandFromResourceAssembler;
import com.brainspark.pulsereport.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/alerts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Alerts", description = "Critical event alert management endpoints")
public class AlertsController {

    private final AlertCommandService alertCommandService;

    public AlertsController(AlertCommandService alertCommandService) {
        this.alertCommandService = alertCommandService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAlert(@RequestBody @Valid CreateAlertResource resource) {
        var command = CreateAlertCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = alertCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AlertResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
