package com.example.spotifysearch.utils;


public class Const {
//    static {
//        System.loadLibrary("keys");
//    }

//    public static native String getClientSecret();
//
//    public static native String getClientId();


    public static final String BASE_URL_ACCOUNTS = "https://accounts.spotify.com/";
    public static final String BASE_URL_API = "https://api.spotify.com/";
    public static final String REDIRECT_URL = "mohsen://mohsenspotifyredirect.com/mohsen/";
    public static final String ACCESS_TOKEN_KEY = "access_tocken";
    public static final String REFRESH_TOKEN_KEY = "refresh_tocken";
    public static final String AUTHORIZATION_CODE_KEY = "authorization_code";
    public static final String EXPIRE_DATE_KEY = "expiration_date";
    public static final String CLIENT_ID = "a75ec794a8344c8b993380ef080efe54";
    //    public static final String CLIENT_ID = getClientId();
    public static final String CLIENT_SECRET = "99737a2b81da447f89b41f66b898c6e6";
    //    public static final String CLIENT_SECRET = getClientSecret();
    public static int REQUEST_TIMEOUT = 60;
    public static final String STATE = "34fFs29kd09";
    public static final String RESPONSE_TYPE = "code";
    public static final String SCOPE = "user-read-private%20user-read-email";

    public static String AUTH_URL = BASE_URL_ACCOUNTS + "authorize?" + "client_id=" + CLIENT_ID + "&" +
            "response_type=" + RESPONSE_TYPE + "&" +
            "redirect_uri=" + REDIRECT_URL + "&" +
            "scope=" + SCOPE + "&" +
            "state=" + STATE;


}
