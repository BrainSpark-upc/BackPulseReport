package com.brainspark.pulsereport.platform.handover.interfaces.rest.transform;

import com.brainspark.pulsereport.platform.handover.domain.model.commands.CreateHandoverCommand;
import com.brainspark.pulsereport.platform.handover.interfaces.rest.resources.CreateHandoverResource;


public class CreateHandoverCommandFromResourceAssembler {

    public static CreateHandoverCommand toCommandFromResource(CreateHandoverResource resource) {
        return new CreateHandoverCommand(resource.title(), resource.description());
    }
}
