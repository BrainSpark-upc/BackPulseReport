package com.brainspark.pulsereport.platform.auditlogs.infrastructure.persistence.jpa.assemblers;

import com.brainspark.pulsereport.platform.auditlogs.domain.model.aggregates.AuditLog;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.queries.GetAuditLogsQuery;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * This class builds a composable {@link Specification} for {@link AuditLog} from a {@link GetAuditLogsQuery}.
 * Each filter is applied only when present, so any combination of filters (included none) is fully supported.
 * This keeps the query service free of conditional branching and allows the same specification to be reused by future features of auditlogs
 */
public final class AuditLogSpecificationAssembler {
    private AuditLogSpecificationAssembler(){}

    public static Specification<AuditLog> fromQuery(GetAuditLogsQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query.patientId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("patientId"), query.patientId()));
            }
            if (query.entityType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("entityType"), query.entityType()));
            }
            if (query.entityId() != null && !query.entityId().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("entityId"), query.entityId()));
            }
            if (query.actionType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("actionType"), query.actionType()));
            }
            if (query.performedBy() != null && !query.performedBy().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("performedBy"), query.performedBy()));
            }
            if (query.from() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("performedAt"), query.from()));
            }
            if (query.to() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("performedAt"), query.to()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}