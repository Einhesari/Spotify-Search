package com.example.spotifysearch.viewmodel.auth;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.example.spotifysearch.model.auth.Token;
import com.example.spotifysearch.network.ApiService;
import com.example.spotifysearch.states.AuthResource;
import com.example.spotifysearch.utils.Const;
import com.example.spotifysearch.utils.PrefUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AuthActivityRepository {

    private ApiService apiService;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<AuthResource> authState = new MutableLiveData<>();

    @Inject
    public AuthActivityRepository( ApiService apiService) {
        this.apiService = apiService;
        authState.postValue(AuthResource.loading(null));
    }

    public void checkAuthorization() {
        if (PrefUtils.getTocken(Const.ACCESS_TOKEN_KEY).equals("") || PrefUtils.getTocken(Const.REFRESH_TOKEN_KEY).equals("")) {
            authState.postValue(AuthResource.unauthorized(null));
        } else {
            authState.postValue(AuthResource.login("Login Successful"));
        }

    }

    public void getAccessCode() {
        disposable.add(apiService.authorize(Const.AUTHORIZATION_CODE_KEY, PrefUtils.getTocken(Const.AUTHORIZATION_CODE_KEY), Const.REDIRECT_URL, Const.CLIENT_ID, Const.CLIENT_SECRET)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Token>() {
                    @Override
                    public void onSuccess(Token token) {
                        PrefUtils.storeToken(Const.REFRESH_TOKEN_KEY, token.getRefreshToken());
                        PrefUtils.storeToken(Const.ACCESS_TOKEN_KEY, token.getAccessToken());
                        PrefUtils.storeToken(Const.EXPIRE_DATE_KEY, String.valueOf(token.getExpireDate()));
                        authState.postValue(AuthResource.authorized("Authorized Successfully"));
                    }

                    @Override
                    public void onError(Throwable e) {
                        authState.postValue(AuthResource.error(e.getMessage()));
                    }
                }));
    }

    public void storeAuthorizationCode(Uri data) {
        authState.postValue(AuthResource.loading(null));
        String authorizationCode = data.getQueryParameters("code").get(0);
        PrefUtils.storeToken(Const.AUTHORIZATION_CODE_KEY, authorizationCode);
    }

    public MutableLiveData<AuthResource> getState() {
        return authState;
    }
}
