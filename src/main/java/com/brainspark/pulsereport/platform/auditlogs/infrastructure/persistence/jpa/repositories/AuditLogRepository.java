package com.brainspark.pulsereport.platform.auditlogs.infrastructure.persistence.jpa.repositories;

import com.brainspark.pulsereport.platform.auditlogs.domain.model.aggregates.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}