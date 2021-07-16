package com.example.myapplication.MyRetrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static final String BASE_URL = "http://10.0.2.2:8081/";
    private static final Retrofit retrofit = buildRetrofit();

    private static Retrofit buildRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }
}
