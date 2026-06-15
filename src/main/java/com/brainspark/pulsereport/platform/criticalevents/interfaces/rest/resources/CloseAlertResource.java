package com.brainspark.pulsereport.platform.criticalevents.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CloseAlertResource(
        @NotBlank
        @Size(max = 120)
        String closedBy,

        @NotBlank
        @Size(max = 255)
        String resolutionNotes
) {
}
