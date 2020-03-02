package com.example.spotifysearch;


import com.example.spotifysearch.di.component.DaggerAppComponent;
import com.example.spotifysearch.utils.PrefUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    @Inject
    PrefUtils prefUtils;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

}
