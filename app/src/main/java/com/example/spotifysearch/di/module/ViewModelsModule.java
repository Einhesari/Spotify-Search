package com.example.spotifysearch.di.module;

import androidx.lifecycle.ViewModel;

import com.example.spotifysearch.viewmodel.auth.AuthActivityViewModel;
import com.example.spotifysearch.viewmodel.main.MainActivityViewModel;
import com.example.spotifysearch.di.scope.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthActivityViewModel.class)
    public abstract ViewModel bindAuthActivityViewModel(AuthActivityViewModel authActivityViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    public abstract ViewModel bindMainActivityViewModel(MainActivityViewModel mainActivityViewModel);
}
