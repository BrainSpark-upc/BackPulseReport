package com.brainspark.pulsereport.platform.patients.application.queryservices;

import com.brainspark.pulsereport.platform.patients.domain.model.aggregates.Patient;
import com.brainspark.pulsereport.platform.patients.domain.model.queries.GetPatientByIdQuery;
import com.brainspark.pulsereport.platform.shared.application.result.ApplicationError;
import com.brainspark.pulsereport.platform.shared.application.result.Result;

public interface PatientQueryService {

    Result<Patient, ApplicationError> handle(GetPatientByIdQuery query);
}
