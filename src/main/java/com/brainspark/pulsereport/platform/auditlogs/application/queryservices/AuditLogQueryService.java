package com.brainspark.pulsereport.platform.auditlogs.application.queryservices;

import com.brainspark.pulsereport.platform.auditlogs.domain.model.aggregates.AuditLog;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.queries.GetAuditLogsQuery;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.queries.GetAuditLogByIdQuery;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.queries.GetPatientAuditTimelineQuery;
import com.brainspark.pulsereport.platform.shared.application.result.ApplicationError;
import com.brainspark.pulsereport.platform.shared.application.result.Result;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * This interface defines the contract for the query service responsible for handling audit log retrieval queries.
 */
public interface AuditLogQueryService {
    /**
     * Handles a paginated, filtered listing of audit log entries, ordered from most to least recent.
     * @param query the filter and pagination criteria
     * @return {@link Result.Success} containing a {@link Page} of {@link AuditLog} entries or {@link Result.Failure} with an {@link ApplicationError} on failure.
     */
    Result<Page<AuditLog>, ApplicationError> handle (GetAuditLogsQuery query);

    /**
     * Handle the retrieval of a single audit log entry by its identifier.
     * @param query the identifier of the audit log entry to retrieve
     * @return {@link Result.Success} containing the {@link AuditLog} entry or {@link Result.Failure} with an {@link ApplicationError#notFound(String, String)} when no entry exists with that id.
     */
    Result<AuditLog, ApplicationError> handle(GetAuditLogByIdQuery query);

    /**
     * Handles the retrieval of the full chronological audit timeline for a given patient.
     * Events are returned oldest to newest so our frontend can render them top to bottom in chronological order without a client side sort.
     * An empty list is a valid successful result when the patient has no recorded audit events.
     * @param query query the patient identifier and optional time window
     * @return {@link Result.Success} containing an ordered {@link List} of {@link AuditLog} entries, or {@link Result.Failure} with an {@link ApplicationError} or unexpected failure.
     */
    Result<List<AuditLog>, ApplicationError> handle(GetPatientAuditTimelineQuery query);
}
