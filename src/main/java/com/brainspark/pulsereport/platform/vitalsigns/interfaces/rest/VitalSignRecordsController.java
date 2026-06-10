package com.brainspark.pulsereport.platform.vitalsigns.interfaces.rest;

import com.brainspark.pulsereport.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.brainspark.pulsereport.platform.vitalsigns.application.commandservices.VitalSignRecordCommandService;
import com.brainspark.pulsereport.platform.vitalsigns.interfaces.rest.resources.CreateVitalSignRecordResource;
import com.brainspark.pulsereport.platform.vitalsigns.interfaces.rest.transform.CreateVitalSignRecordCommandFromResourceAssembler;
import com.brainspark.pulsereport.platform.vitalsigns.interfaces.rest.transform.VitalSignRecordResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/vital-sign-records", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Vital Sign Records", description = "Vital sign record management endpoints")
public class VitalSignRecordsController {

    private final VitalSignRecordCommandService vitalSignRecordCommandService;

    public VitalSignRecordsController(VitalSignRecordCommandService vitalSignRecordCommandService) {
        this.vitalSignRecordCommandService = vitalSignRecordCommandService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createVitalSignRecord(@RequestBody @Valid CreateVitalSignRecordResource resource) {
        var command = CreateVitalSignRecordCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = vitalSignRecordCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                VitalSignRecordResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}