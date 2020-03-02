package com.example.spotifysearch.states;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AuthResource {

    @NonNull
    public final AuthStatus status;

    @Nullable
    public final String message;


    public AuthResource(@NonNull AuthStatus status, @Nullable String message) {
        this.status = status;
        this.message = message;
    }

    public static AuthResource authorized(@Nullable String message) {
        return new AuthResource(AuthStatus.AUTHORIZED_SUCCESSFULLY, message);
    }

    public static AuthResource unauthorized(@Nullable String message) {
        return new AuthResource(AuthStatus.UNAUTHROIZED, message);
    }

    public static AuthResource error(@Nullable String message) {
        return new AuthResource(AuthStatus.ERROR, message);
    }

    public static AuthResource loading(@Nullable String message) {
        return new AuthResource(AuthStatus.LOADING, message);
    }

    public static AuthResource login(@Nullable String message) {
        return new AuthResource(AuthStatus.ALREADY_AUTHORIZED, message);
    }

    public enum AuthStatus {
        ALREADY_AUTHORIZED,
        UNAUTHROIZED,
        ERROR,
        AUTHORIZED_SUCCESSFULLY,
        LOADING

    }
}