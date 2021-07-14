package com.example.myapplication.Model;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    private static final String BASE_URL = "http://10.0.2.2:8081/api/";

//    private final static OkHttpClient client = buildClient();
    private final static Retrofit retrofit = buildRetrofit();

//    private static OkHttpClient buildClient(){
//        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request();
//
//                        Request.Builder builder = request.newBuilder()
//                                .addHeader("Accept", "application/json")
//                                .addHeader("Connection", "close");
//
//                        request = builder.build();
//
//                        return chain.proceed(request);
//
//                    }
//                });
//
//        return builder.build();
//
//    }

    private static Retrofit buildRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static <T> T createService(Class<T> service){
        return retrofit.create(service);
    }


//    public static Retrofit getRetrofit() {
//        return retrofit;
//    }
}
