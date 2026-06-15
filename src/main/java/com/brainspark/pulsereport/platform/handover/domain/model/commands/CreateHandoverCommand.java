package com.brainspark.pulsereport.platform.handover.domain.model.commands;

public record CreateHandoverCommand(String title, String description) {

    public CreateHandoverCommand {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description cannot be null or blank");
        }
    }
}
