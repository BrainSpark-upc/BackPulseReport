package com.brainspark.pulsereport.platform.criticalevents.domain.repositories;

import com.brainspark.pulsereport.platform.criticalevents.domain.model.aggregates.Alert;

public interface AlertRepository {

    Alert save(Alert alert);
}
