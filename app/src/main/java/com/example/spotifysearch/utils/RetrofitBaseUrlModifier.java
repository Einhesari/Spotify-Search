package com.example.spotifysearch.utils;

import java.lang.reflect.Field;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;

public class RetrofitBaseUrlModifier {

    public static void Change(Retrofit retrofit, String newUrl) {
        if (retrofit != null) {
            try {
                Field field = Retrofit.class.getDeclaredField("baseUrl");
                field.setAccessible(true);
                okhttp3.HttpUrl newHttpUrl = HttpUrl.parse(newUrl);
                field.set(retrofit, newHttpUrl);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
