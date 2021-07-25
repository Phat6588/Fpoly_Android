package com.example.myapplication.MyRetrofit;

import com.example.myapplication.Model.AccessToken;
import com.example.myapplication.Model.Person;
import com.example.myapplication.Model.Product;
import com.example.myapplication.Model.Response2PikModel;
import com.example.myapplication.Model.ResponseModel;
import com.example.myapplication.Model.Student;
import com.example.myapplication.Model.ThaThinh;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface IRetrofitService {

    @POST("views/user_login.php")
    Call<ResponseModel> login(@Body Person person);

    @POST("views/product_get_all.php")
    Call<List<Product>> getAllProduct();


    @Multipart
    @POST("/")
    Call<Response2PikModel> upload2pik(@Part MultipartBody.Part image);
}
