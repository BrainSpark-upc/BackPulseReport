package com.brainspark.pulsereport.platform.patients.application.queryservices;

import com.brainspark.pulsereport.platform.patients.domain.model.aggregates.Patient;
import com.brainspark.pulsereport.platform.patients.domain.model.queries.GetAllPatientsQuery;
import com.brainspark.pulsereport.platform.patients.domain.model.queries.GetPatientByIdQuery;
import com.brainspark.pulsereport.platform.shared.application.result.ApplicationError;
import com.brainspark.pulsereport.platform.shared.application.result.Result;

import java.util.List;

public interface PatientQueryService {

    Result<Patient, ApplicationError> handle(GetPatientByIdQuery query);

    Result<List<Patient>, ApplicationError> handle(GetAllPatientsQuery query);
}