package com.example.spotifysearch.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPrefsModule {

    @Provides
    public SharedPreferences provideSharedPrefs(Context context) {
        return context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
    }

    @Provides
    public SharedPreferences.Editor provideEditor(SharedPreferences sharedPreferences) {
        return sharedPreferences.edit();
    }

}
