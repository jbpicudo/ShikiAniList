package com.example.shikianilist.NetworkRetrofitApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static RetrofitInstance instance;

    private Retrofit mRetrofit;

    private static final String BASE_URL = "https://shikimori.one";

    private RetrofitInstance() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitInstance getInstance() {
        if (instance == null){
            instance = new RetrofitInstance();
        }

        return instance;
    }

    public ShikiApi getShikiApi() {
        return mRetrofit.create(ShikiApi.class);
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

}
