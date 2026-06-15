package com.brainspark.pulsereport.platform.auditlogs.application.queryservices;

import com.brainspark.pulsereport.platform.auditlogs.domain.model.aggregates.AuditLog;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.queries.GetAuditLogsQuery;
import com.brainspark.pulsereport.platform.shared.application.result.ApplicationError;
import com.brainspark.pulsereport.platform.shared.application.result.Result;

import org.springframework.data.domain.Page;

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
}
