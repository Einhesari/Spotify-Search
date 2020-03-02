package com.example.spotifysearch.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class PrefUtils {

    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    @Inject
    public PrefUtils(SharedPreferences sharedPreferences , SharedPreferences.Editor editor) {
        PrefUtils.sharedPreferences = sharedPreferences;
        PrefUtils.editor = editor;
    }


    public static void storeToken(String key, String token) {
        editor.putString(key, token);
        editor.apply();
    }

    public static String getTocken(String key) {
        return sharedPreferences.getString(key, "");
    }
}
