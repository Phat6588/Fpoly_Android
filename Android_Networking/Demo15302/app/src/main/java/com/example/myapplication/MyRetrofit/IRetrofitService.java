package com.example.myapplication.MyRetrofit;

import com.example.myapplication.Models.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface IRetrofitService {

    @GET("getOne.php")
    Call<Person> getOne();

    @GET("getArray.php")
    Call<List<Person>> getArray();

    @GET("getOneByParam.php")
    Call<Person> getOneByParam(@Query("id") int id);

    @POST("post.php")
    Call<Person> post();
}
