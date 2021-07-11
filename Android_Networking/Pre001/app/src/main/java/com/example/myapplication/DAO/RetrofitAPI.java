package com.example.myapplication.DAO;

import android.util.Log;

import com.example.myapplication.Model.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPI implements Callback <List<Student>>{

    static final String BASE_URL = "http://10.0.2.2:8081/";

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        InterfaceRetrofitAPI api = retrofit.create(InterfaceRetrofitAPI.class);

        Call<List<Student>> call = api.get();
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
        if(response.isSuccessful()) {
            List<Student> list = response.body();
            list.forEach(s -> Log.e(">>>>>>>>>>>",s.getName()));
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Student>> call, Throwable t) {
        Log.e(">>>>>>>>>>>",t.getMessage());
    }
}
