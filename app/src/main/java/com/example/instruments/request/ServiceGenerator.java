package com.example.instruments.request;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ServiceGenerator {

    public static final String BASE_URL = "https://data-fix.smt-data.com/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .callTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            ;

    private static Retrofit retrofit =new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    private static RequestApi requestApi1 = retrofit.create(RequestApi.class);

    public static RequestApi getRequestApi(){
        return requestApi1;
    }

}
