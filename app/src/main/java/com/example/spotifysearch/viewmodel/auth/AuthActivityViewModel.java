package com.example.spotifysearch.viewmodel.auth;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotifysearch.states.AuthResource;

import javax.inject.Inject;

public class AuthActivityViewModel extends ViewModel {

    private AuthActivityRepository repository;

    @Inject
    public AuthActivityViewModel(AuthActivityRepository repository) {
        this.repository = repository;
    }



    public void checkAutorizationStatus() {

        repository.checkAuthorization();


    }

    public void getCridentials(Uri data) {
        repository.storeAuthorizationCode(data);
        repository.getAccessCode();
    }

    public MutableLiveData<AuthResource> getAuthorizationStatus() {
        return repository.getState();
    }
}
