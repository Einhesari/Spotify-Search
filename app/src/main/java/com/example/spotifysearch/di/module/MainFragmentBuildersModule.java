package com.example.spotifysearch.di.module;

import com.example.spotifysearch.view.main.search.SearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract SearchFragment contributeSearchFragment();
}
