package com.brainspark.pulsereport.platform.patients.interfaces.rest;

import com.brainspark.pulsereport.platform.patients.application.commandservices.PatientCommandService;
import com.brainspark.pulsereport.platform.patients.application.queryservices.PatientQueryService;
import com.brainspark.pulsereport.platform.patients.domain.model.queries.GetPatientByIdQuery;
import com.brainspark.pulsereport.platform.patients.interfaces.rest.resources.CreatePatientResource;
import com.brainspark.pulsereport.platform.patients.interfaces.rest.transform.CreatePatientCommandFromResourceAssembler;
import com.brainspark.pulsereport.platform.patients.interfaces.rest.transform.PatientResourceFromEntityAssembler;
import com.brainspark.pulsereport.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/patients", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Patients", description = "Patient management endpoints")
public class PatientsController {

    private final PatientCommandService patientCommandService;
    private final PatientQueryService patientQueryService;

    public PatientsController(
            PatientCommandService patientCommandService,
            PatientQueryService patientQueryService
    ) {
        this.patientCommandService = patientCommandService;
        this.patientQueryService = patientQueryService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPatient(@RequestBody @Valid CreatePatientResource resource) {
        var command = CreatePatientCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = patientCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                PatientResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<?> getPatientById(@PathVariable Long patientId) {
        var query = new GetPatientByIdQuery(patientId);
        var result = patientQueryService.handle(query);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                PatientResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}