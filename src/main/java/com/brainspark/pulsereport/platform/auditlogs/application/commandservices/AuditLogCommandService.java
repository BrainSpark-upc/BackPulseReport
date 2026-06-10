package com.brainspark.pulsereport.platform.auditlogs.application.commandservices;

import com.brainspark.pulsereport.platform.auditlogs.domain.model.aggregates.AuditLog;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.commands.CreateAuditLogCommand;
import com.brainspark.pulsereport.platform.shared.application.result.ApplicationError;
import com.brainspark.pulsereport.platform.shared.application.result.Result;

public interface AuditLogCommandService {
    Result<AuditLog, ApplicationError> handle(CreateAuditLogCommand command);
}
