package com.example.myapplication.DAO;

import retrofit2.Call;
import retrofit2.http.*;

import com.example.myapplication.Model.AccessToken;
import com.example.myapplication.Model.Student;

import java.util.List;

public interface InterfaceRetrofitAPI {
    static final String BASE_URL = "http://10.0.2.2:8081/api/";
    @GET("test.php/")
    Call<List<Student>> get();

    @POST("test.php/")
    Call<List<Student>> post(@Body Student student);

    @GET("test.php/")
    Call<Student> getOne();

    @POST("login.php/")
    Call<AccessToken> login(@Body Student student);

//    @GET("/repos/{owner}/{repo}/issues")
//    Single<List<GithubIssue>> getIssues(@Path("owner") String owner, @Path("repo") String repository);

//    @POST("api/test.php/")
//    Call<Void> postComment(@Url String url, @Body GithubIssue issue);
}
