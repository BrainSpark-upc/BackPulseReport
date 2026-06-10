package com.brainspark.pulsereport.platform.auditlogs.application.internal.commandservices;

import com.brainspark.pulsereport.platform.auditlogs.application.commandservices.AuditLogCommandService;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.aggregates.AuditLog;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.commands.CreateAuditLogCommand;
import com.brainspark.pulsereport.platform.auditlogs.infrastructure.persistence.jpa.repositories.AuditLogRepository;
import com.brainspark.pulsereport.platform.shared.application.result.ApplicationError;
import com.brainspark.pulsereport.platform.shared.application.result.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link AuditLogCommandService} responsible for handling
 * creation of audit log records through command handling pattern.
 *
 * <p>This service validates incoming {@link CreateAuditLogCommand} commands,
 * creates {@link AuditLog} aggregate roots, and persists them to the repository.
 * All database operations are executed within a single transactional context.
 *
 * @author BackPulse Report
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuditLogCommandServiceImplementation implements AuditLogCommandService {
    private final AuditLogRepository auditLogRepository;

    /**
     * Handles the creation of a new audit log entry based on the provided command.
     *
     * <p>Performs comprehensive validation of all command parameters before
     * attempting to create and persist the audit log. Returns a {@code Result}
     * containing either the successfully saved audit log or an application error.
     *
     * @param command the {@link CreateAuditLogCommand} containing audit log data
     * @return {@code Result.success()} with persisted audit log, or
     *         {@code Result.failure()} with validation/unexpected errors
     */
    @Transactional
    @Override
    public Result<AuditLog, ApplicationError> handle(CreateAuditLogCommand command) {
        // Validate responsible user ID - must be a positive number
        if (command.responsibleUserId() == null || command.responsibleUserId() <= 0) {
            return Result.failure(ApplicationError.validationError("responsibleUserId", "Must be a positive number"));
        }

        // Validate audited entity ID - must be a positive number
        if(command.auditedEntityId() == null || command.auditedEntityId() <= 0) {
            return Result.failure(ApplicationError.validationError("auditedEntityId", "Must be a positive number"));
        }

        // Validate audited entity type - must not be null
        if(command.auditedEntityType() == null) {
            return Result.failure(ApplicationError.validationError("auditedEntityType", "Must not be null"));
        }

        // Validate action type - must not be null
        if (command.actionType() == null) {
            return Result.failure(ApplicationError.validationError("actionType", "Must not be null"));
        }

        // Validate detail field - optional but must not exceed 500 characters if provided
        if (command.detail() != null && command.detail().length() > 500) {
            return Result.failure(ApplicationError.validationError("detail", "Must not exceed 500 characters"));
        }

        // Create, persist and return the audit log
        try {
            var auditLog = new AuditLog(command);
            var saved = auditLogRepository.save(auditLog);
            log.info("AuditLog created: id={}, action={}, entity={}",
                    saved.getId(), saved.getActionType(), saved.getAuditedEntityType());
            return Result.success(saved);
        } catch (Exception ex) {
            // Log unexpected errors and return failure result
            log.error("Unexpected error creating AuditLog", ex);
            return Result.failure(ApplicationError.unexpected(
                    "AuditLogCommandServiceImpl", ex.getMessage()));
        }
    }
}
