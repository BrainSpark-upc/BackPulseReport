package com.brainspark.pulsereport.platform.clinicalevents.application.internal.commandservices;

import com.brainspark.pulsereport.platform.clinicalevents.application.commandservices.ClinicalEventCommandService;
import com.brainspark.pulsereport.platform.clinicalevents.domain.model.aggregates.ClinicalEvent;
import com.brainspark.pulsereport.platform.clinicalevents.domain.model.commands.CreateClinicalEventCommand;
import com.brainspark.pulsereport.platform.clinicalevents.domain.repositories.ClinicalEventRepository;
import com.brainspark.pulsereport.platform.shared.application.result.ApplicationError;
import com.brainspark.pulsereport.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clinical event command service implementation.
 */
@Service
public class ClinicalEventCommandServiceImpl implements ClinicalEventCommandService {

    private final ClinicalEventRepository clinicalEventRepository;

    public ClinicalEventCommandServiceImpl(ClinicalEventRepository clinicalEventRepository) {
        this.clinicalEventRepository = clinicalEventRepository;
    }

    @Override
    @Transactional
    public Result<ClinicalEvent, ApplicationError> handle(CreateClinicalEventCommand command) {
        try {
            var clinicalEvent = new ClinicalEvent(command);
            return Result.success(clinicalEventRepository.save(clinicalEvent));
        } catch (RuntimeException exception) {
            return Result.failure(ApplicationError.businessRuleViolation(
                    "create clinical event",
                    exception.getMessage()
            ));
        }
    }
}
