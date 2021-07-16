package com.example.myapplication.MyRetrofit;

import com.example.myapplication.Model.AccessToken;
import com.example.myapplication.Model.Person;
import com.example.myapplication.Model.Student;
import com.example.myapplication.Model.ThaThinh;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface IRetrofitService {

    @GET("getOne.php")
    Call<Person> get();

    @GET("getArray.php")
    Call<List<Person>> getArray();

    @POST("postPerson.php")
    Call<Student> postPerson(@Body Student student);

    @POST("thaThinh.php")
    Call<ThaThinh> thaThinh(@Body ThaThinh tt);

    @POST("login.php")
    Call<AccessToken> login(@Body Person person);

    @POST("getProfile.php")
    Call<Person> getProfile();
}
