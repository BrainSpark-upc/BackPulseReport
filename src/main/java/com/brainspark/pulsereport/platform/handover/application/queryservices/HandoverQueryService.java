package com.brainspark.pulsereport.platform.handover.application.queryservices;

import com.brainspark.pulsereport.platform.handover.domain.model.aggregates.Handover;
import com.brainspark.pulsereport.platform.handover.domain.model.queries.GetAllHandoversQuery;
import com.brainspark.pulsereport.platform.handover.domain.model.queries.GetHandoverByIdQuery;

import java.util.List;
import java.util.Optional;

public interface HandoverQueryService {

    Optional<Handover> handle(GetHandoverByIdQuery query);
    List<Handover> handle(GetAllHandoversQuery query);
}
