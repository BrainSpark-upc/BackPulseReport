package com.brainspark.pulsereport.platform.iam.domain.model.commands;

import java.util.Locale;

/**
 * Sign in command
 * <p>
 *     This class represents the command to sign in a user.
 * </p>
 * @param username the username of the user
 * @param password the password of the user
 *
 * @see com.brainspark.pulsereport.platform.iam.domain.model.aggregates.User
 */
public record SignInCommand(String username, String password) {
    public SignInCommand {
        username = username == null ? null : username.trim().toLowerCase(Locale.ROOT);
    }
}
