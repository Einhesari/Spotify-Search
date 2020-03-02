package com.example.spotifysearch.model.auth;

import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("scope")
    private String scope;

    @SerializedName("expires_in")
    private int expireDate;

    public String getAccessToken() {
        return accessToken;
    }


    public String getRefreshToken() {
        return refreshToken;
    }


    public String getTokenType() {
        return tokenType;
    }


    public String getScope() {
        return scope;
    }


    public int getExpireDate() {
        return expireDate;
    }



}
