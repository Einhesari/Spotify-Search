package com.example.spotifysearch.network;


import com.example.spotifysearch.model.auth.Token;
import com.example.spotifysearch.model.item.SearchResult;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("api/token")
    Single<Token> authorize(@Field("grant_type") String grant_type, @Field("code") String auth_code, @Field("redirect_uri") String redirect_uri, @Field("client_id") String client_id, @Field("client_secret") String client_secret);

    @GET("v1/search")
    Observable<SearchResult> search(@Query("q") String searchQuery, @Query("type") String type, @Query("limit") int limit);

    @FormUrlEncoded
    @POST("api/token")
    Call<Token> refresh(@Field("grant_type") String grant_type, @Field("refresh_token") String refresh_token, @Field("client_id") String client_id, @Field("client_secret") String client_secret);

}
