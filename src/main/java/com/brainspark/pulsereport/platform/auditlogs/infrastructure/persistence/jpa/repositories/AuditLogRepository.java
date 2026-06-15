package com.brainspark.pulsereport.platform.auditlogs.infrastructure.persistence.jpa.repositories;

import com.brainspark.pulsereport.platform.auditlogs.domain.model.aggregates.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link AuditLog} aggregate.
 * Append-only: no delete or update operations are exposed.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}