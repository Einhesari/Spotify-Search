package com.example.spotifysearch.di.module;



import com.example.spotifysearch.di.scope.AppScope;
import com.example.spotifysearch.model.auth.Token;
import com.example.spotifysearch.network.ApiService;
import com.example.spotifysearch.utils.Const;
import com.example.spotifysearch.utils.PrefUtils;
import com.example.spotifysearch.utils.RetrofitBaseUrlModifier;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.spotifysearch.utils.Const.REQUEST_TIMEOUT;


@Module
public class ApiModule {

    @Provides
    @AppScope
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Const.BASE_URL_ACCOUNTS)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @AppScope
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @AppScope
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor, Authenticator authenticator, Interceptor interceptor) {
        return new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(httpLoggingInterceptor)
                .authenticator(authenticator)
                .build();

    }

    @Provides
    @AppScope
    Interceptor provideOkHttpInterceptor() {
        return chain -> {
            Request originalRequest = chain.request();
            Request.Builder requestBuilder = originalRequest.newBuilder();

            String accessToken = PrefUtils.getTocken(Const.ACCESS_TOKEN_KEY);

            if (!originalRequest.url().encodedPath().equalsIgnoreCase("/api/token")) {

                requestBuilder.addHeader("Authorization", "Bearer " + accessToken);
            }

            originalRequest = requestBuilder.build();
            return chain.proceed(originalRequest);
        };
    }

    @Provides
    @AppScope
    Authenticator provideOkHttpAuthenticator(Lazy<ApiService> apiServiceLazy, Lazy<Retrofit> retrofitLazy) {

        return (route, response) -> {
            String currentToken = PrefUtils.getTocken(Const.ACCESS_TOKEN_KEY);
            synchronized (ApiModule.class) {
                String newToken = PrefUtils.getTocken(Const.ACCESS_TOKEN_KEY);
                if (currentToken.equals(newToken)) {

                    RetrofitBaseUrlModifier.Change(retrofitLazy.get(), Const.BASE_URL_ACCOUNTS);
                    refreshToken(apiServiceLazy);
                }


            }
            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + PrefUtils.getTocken(Const.ACCESS_TOKEN_KEY))
                    .build();
        };

    }

    private String refreshToken(Lazy<ApiService> apiServiceLazy) {

        retrofit2.Response<Token> refreshedToken;

        try {
            refreshedToken = apiServiceLazy.get().refresh("refresh_token", PrefUtils.getTocken(Const.REFRESH_TOKEN_KEY), Const.CLIENT_ID, Const.CLIENT_SECRET).execute();
            if (refreshedToken.isSuccessful()) {
                PrefUtils.storeToken(Const.ACCESS_TOKEN_KEY, refreshedToken.body().getAccessToken());
            } else {
                //TODO logout
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return PrefUtils.getTocken(Const.ACCESS_TOKEN_KEY);

    }

    @Provides
    @AppScope
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }


}
