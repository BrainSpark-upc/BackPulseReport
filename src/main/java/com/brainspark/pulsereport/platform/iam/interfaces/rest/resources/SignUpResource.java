package com.brainspark.pulsereport.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Resource received to register a new IAM user.
 */
@Schema(
    name = "SignUpRequest",
    description = "User sign-up request with credentials and roles",
    example = "{\"username\": \"nurse.maria\", \"password\": \"SecurePass123!\"}"
)
public record SignUpResource(
    @NotBlank(message = "{validation.not-blank}")
    @Size(min = 3, max = 50, message = "{validation.size}")
    @Schema(
        description = "Desired username",
        example = "nurse.maria",
        minLength = 3,
        maxLength = 50
    )
    String username,

    @NotBlank(message = "{validation.not-blank}")
    @Size(min = 8, max = 72, message = "{validation.size}")
    @Schema(
        description = "User password (8 to 72 characters)",
        example = "SecurePass123!",
        minLength = 8,
        maxLength = 72
    )
    String password
) {
}
