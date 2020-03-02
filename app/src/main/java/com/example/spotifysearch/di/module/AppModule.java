package com.example.spotifysearch.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    public Context getContext(Application application) {
        return application.getApplicationContext();
    }

}
