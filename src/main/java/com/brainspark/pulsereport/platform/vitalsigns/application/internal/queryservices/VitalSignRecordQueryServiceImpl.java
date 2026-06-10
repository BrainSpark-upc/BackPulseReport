package com.brainspark.pulsereport.platform.vitalsigns.application.internal.queryservices;

import com.brainspark.pulsereport.platform.shared.application.result.ApplicationError;
import com.brainspark.pulsereport.platform.shared.application.result.Result;
import com.brainspark.pulsereport.platform.vitalsigns.application.queryservices.VitalSignRecordQueryService;
import com.brainspark.pulsereport.platform.vitalsigns.domain.model.aggregates.VitalSignRecord;
import com.brainspark.pulsereport.platform.vitalsigns.domain.model.queries.GetAllVitalSignRecordsQuery;
import com.brainspark.pulsereport.platform.vitalsigns.domain.model.queries.GetVitalSignRecordByIdQuery;
import com.brainspark.pulsereport.platform.vitalsigns.domain.repositories.VitalSignRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VitalSignRecordQueryServiceImpl implements VitalSignRecordQueryService {

    private final VitalSignRecordRepository vitalSignRecordRepository;

    public VitalSignRecordQueryServiceImpl(VitalSignRecordRepository vitalSignRecordRepository) {
        this.vitalSignRecordRepository = vitalSignRecordRepository;
    }

    @Override
    public Result<VitalSignRecord, ApplicationError> handle(GetVitalSignRecordByIdQuery query) {
        return vitalSignRecordRepository.findById(query.vitalSignRecordId())
                .<Result<VitalSignRecord, ApplicationError>>map(Result::success)
                .orElseGet(() -> Result.failure(ApplicationError.notFound(
                        "Vital sign record",
                        query.vitalSignRecordId().toString()
                )));
    }

    @Override
    public Result<List<VitalSignRecord>, ApplicationError> handle(GetAllVitalSignRecordsQuery query) {
        var vitalSignRecords = vitalSignRecordRepository.findAll();

        return Result.success(vitalSignRecords);
    }
}