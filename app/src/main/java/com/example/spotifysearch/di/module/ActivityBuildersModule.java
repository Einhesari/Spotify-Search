package com.example.spotifysearch.di.module;

import com.example.spotifysearch.view.auth.AuthActivity;
import com.example.spotifysearch.view.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {


    @ContributesAndroidInjector
    abstract AuthActivity contributeAuthActivity();

    @ContributesAndroidInjector(
            modules = {
                    MainFragmentBuildersModule.class,
            }
    )
    abstract MainActivity contributeMainActivity();

}
