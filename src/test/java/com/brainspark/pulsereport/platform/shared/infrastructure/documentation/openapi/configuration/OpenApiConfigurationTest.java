package com.brainspark.pulsereport.platform.shared.infrastructure.documentation.openapi.configuration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenApiConfigurationTest {

    @Test
    void shouldUseTheSwaggerUiOriginForInteractiveRequests() {
        var configuration = new OpenApiConfiguration();
        configuration.applicationName = "PulseReport Platform";
        configuration.applicationDescription = "PulseReport Platform Backend";
        configuration.applicationVersion = "test";

        var openApi = configuration.learningPlatformOpenApi();

        assertEquals(1, openApi.getServers().size());
        assertEquals("/", openApi.getServers().getFirst().getUrl());
        assertEquals(
                "Current environment",
                openApi.getServers().getFirst().getDescription()
        );
    }
}
