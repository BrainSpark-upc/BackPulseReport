package com.brainspark.pulsereport.platform.handover.domain.repositories;

import com.brainspark.pulsereport.platform.handover.domain.model.aggregates.Handover;

import java.util.List;
import java.util.Optional;


public interface HandoverRepository {
    Optional<Handover> findById(Long id);

    List<Handover> findAll();

    Optional<Handover> findByTitle(String title);

    Handover save(Handover handover);

    boolean existsById(Long id);

    boolean existsByTitle(String title);

    void deleteById(Long id);
}
