package com.brainspark.pulsereport.platform.handover.application.commandservices;

import com.brainspark.pulsereport.platform.handover.domain.model.aggregates.Handover;
import com.brainspark.pulsereport.platform.handover.domain.model.commands.CreateHandoverCommand;
import com.brainspark.pulsereport.platform.shared.application.result.ApplicationError;
import com.brainspark.pulsereport.platform.shared.application.result.Result;

public interface HandoverCommandService {

    Result<Long, ApplicationError> handle(CreateHandoverCommand command);
}
