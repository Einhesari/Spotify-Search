package com.example.spotifysearch.states;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SearchResouces<T> {

    @NonNull
    public final SearchStatus status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;

    public SearchResouces(SearchStatus status, T data, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public static <T> SearchResouces<T> loading() {
        return new SearchResouces<>(SearchStatus.LOADING, null, null);
    }

    public static <T> SearchResouces<T> error(@NonNull String msg, @Nullable T data) {
        return new SearchResouces<>(SearchStatus.ERROR, data, msg);
    }

    public static <T> SearchResouces<T> searchComplete(@Nullable T data) {
        return new SearchResouces<>(SearchStatus.SEARCH_COMPLETE, data, null);
    }

    public enum SearchStatus {
        LOADING,
        SEARCH_COMPLETE,
        ERROR,

    }

}
