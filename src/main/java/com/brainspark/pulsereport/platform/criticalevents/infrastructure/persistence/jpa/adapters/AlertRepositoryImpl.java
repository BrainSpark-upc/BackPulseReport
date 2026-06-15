package com.brainspark.pulsereport.platform.criticalevents.infrastructure.persistence.jpa.adapters;

import com.brainspark.pulsereport.platform.criticalevents.domain.model.aggregates.Alert;
import com.brainspark.pulsereport.platform.criticalevents.domain.repositories.AlertRepository;
import com.brainspark.pulsereport.platform.criticalevents.infrastructure.persistence.jpa.assemblers.AlertPersistenceAssembler;
import com.brainspark.pulsereport.platform.criticalevents.infrastructure.persistence.jpa.repositories.AlertPersistenceRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AlertRepositoryImpl implements AlertRepository {

    private final AlertPersistenceRepository alertPersistenceRepository;

    public AlertRepositoryImpl(AlertPersistenceRepository alertPersistenceRepository) {
        this.alertPersistenceRepository = alertPersistenceRepository;
    }

    @Override
    public Alert save(Alert alert) {
        var entity = AlertPersistenceAssembler.toPersistenceFromDomain(alert);
        var savedEntity = alertPersistenceRepository.save(entity);

        return AlertPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }
}
