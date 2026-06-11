package com.brainspark.pulsereport.platform.vitalsigns.application.queryservices;

import com.brainspark.pulsereport.platform.shared.application.result.ApplicationError;
import com.brainspark.pulsereport.platform.shared.application.result.Result;
import com.brainspark.pulsereport.platform.vitalsigns.domain.model.aggregates.VitalSignRecord;
import com.brainspark.pulsereport.platform.vitalsigns.domain.model.queries.GetAllVitalSignRecordsQuery;
import com.brainspark.pulsereport.platform.vitalsigns.domain.model.queries.GetVitalSignRecordByIdQuery;
import com.brainspark.pulsereport.platform.vitalsigns.domain.model.queries.GetVitalSignRecordsByPatientIdQuery;

import java.util.List;

public interface VitalSignRecordQueryService {

    Result<VitalSignRecord, ApplicationError> handle(GetVitalSignRecordByIdQuery query);

    Result<List<VitalSignRecord>, ApplicationError> handle(GetAllVitalSignRecordsQuery query);

    Result<List<VitalSignRecord>, ApplicationError> handle(GetVitalSignRecordsByPatientIdQuery query);
}