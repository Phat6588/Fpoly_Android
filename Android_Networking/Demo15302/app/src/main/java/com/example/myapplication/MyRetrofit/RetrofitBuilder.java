package com.example.myapplication.MyRetrofit;

import com.example.myapplication.Models.AccessToken;
import com.example.myapplication.Models.AccessTokenManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private String BASE_URL = "http://10.0.2.2:8081/";

    private Retrofit buildRetrofit() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String access_token = AccessTokenManager.getInstance(null).getToken().getAccess_token();
                Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + access_token)
                        .build();
                return chain.proceed(request);
            }
        };
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        OkHttpClient client = builder.build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public <T> T createService(Class<T> service, String url) {
        this.BASE_URL = url;
        return buildRetrofit().create(service);
    }
}
