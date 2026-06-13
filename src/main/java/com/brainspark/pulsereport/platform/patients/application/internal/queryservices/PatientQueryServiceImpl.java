package com.brainspark.pulsereport.platform.patients.application.internal.queryservices;

import com.brainspark.pulsereport.platform.patients.application.queryservices.PatientQueryService;
import com.brainspark.pulsereport.platform.patients.domain.model.aggregates.Patient;
import com.brainspark.pulsereport.platform.patients.domain.model.queries.GetPatientByIdQuery;
import com.brainspark.pulsereport.platform.patients.domain.repositories.PatientRepository;
import com.brainspark.pulsereport.platform.shared.application.result.ApplicationError;
import com.brainspark.pulsereport.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class PatientQueryServiceImpl implements PatientQueryService {

    private final PatientRepository patientRepository;

    public PatientQueryServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Result<Patient, ApplicationError> handle(GetPatientByIdQuery query) {
        return patientRepository.findById(query.patientId())
                .map(Result::<Patient, ApplicationError>success)
                .orElseGet(() -> Result.failure(ApplicationError.notFound(
                        "Patient",
                        "Patient with id %s was not found.".formatted(query.patientId())
                )));
    }
}
