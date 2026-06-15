package com.brainspark.pulsereport.platform.handover.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(
        name = "HandoverResponse",
        description = "Handover information response",
        example = "{\"id\": 1, \"patientId\": 1, \"title\": \"Night Shift Handover\", \"description\": \"Pending tasks and key updates for the next shift\", \"status\": \"PENDING\"}"
)
public record HandoverResource(
        @Schema(description = "Handover unique identifier", example = "1")
        Long id,

        @Schema(description = "Patient ID associated with the handover", example = "1")
        Long patientId,

        @Schema(description = "Handover title", example = "Night Shift Handover")
        String title,

        @Schema(description = "Handover description", example = "Pending tasks and key updates for the next shift")
        String description,

        @Schema(description = "Handover status", example = "PENDING")
        String status
) {
}
