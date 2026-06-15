package com.brainspark.pulsereport.platform.criticalevents.application.commandservices;

import com.brainspark.pulsereport.platform.criticalevents.domain.model.aggregates.Alert;
import com.brainspark.pulsereport.platform.criticalevents.domain.model.commands.CreateAlertCommand;
import com.brainspark.pulsereport.platform.shared.application.result.ApplicationError;
import com.brainspark.pulsereport.platform.shared.application.result.Result;

public interface AlertCommandService {

    Result<Alert, ApplicationError> handle(CreateAlertCommand command);
}
