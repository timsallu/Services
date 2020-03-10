package com.example.instruments.request;


import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestApi {

    @GET("fixprof/instruments/prices")
    Call<String> getRess();
}
