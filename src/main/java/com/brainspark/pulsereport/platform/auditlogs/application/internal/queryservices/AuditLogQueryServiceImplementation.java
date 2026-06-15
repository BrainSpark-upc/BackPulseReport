package com.brainspark.pulsereport.platform.auditlogs.application.internal.queryservices;

import com.brainspark.pulsereport.platform.auditlogs.application.queryservices.AuditLogQueryService;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.aggregates.AuditLog;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.queries.GetAuditLogsQuery;
import com.brainspark.pulsereport.platform.auditlogs.infrastructure.persistence.jpa.assemblers.AuditLogSpecificationAssembler;
import com.brainspark.pulsereport.platform.auditlogs.infrastructure.persistence.jpa.repositories.AuditLogRepository;
import com.brainspark.pulsereport.platform.shared.application.result.Result;
import com.brainspark.pulsereport.platform.shared.application.result.ApplicationError;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditLogQueryServiceImplementation implements AuditLogQueryService {
    private final AuditLogRepository auditLogRepository;

    @Override
    @Transactional(readOnly = true)
    public Result<Page<AuditLog>, ApplicationError> handle(GetAuditLogsQuery query){
        try{
            var specification = AuditLogSpecificationAssembler.fromQuery(query);
            var pageable = PageRequest.of(
                    query.pageOrDefault(),
                    query.sizeOrDefault(),
                    Sort.by(Sort.Direction.DESC, "performedAt")
            );
            Page<AuditLog> result = auditLogRepository.findAll(specification, pageable);
            return Result.success(result);
        } catch(Exception ex) {
            log.error("Unexpected error while querying audit log entries", ex);
            return Result.failure(ApplicationError.unexpected("AuditLogQueryService", ex.getMessage())
            );
        }
    }
}
