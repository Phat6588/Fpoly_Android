package com.example.myapplication.MyRetrofit;

import com.example.myapplication.Model.AccessToken;
import com.example.myapplication.Model.Person;
import com.example.myapplication.Model.Product;
import com.example.myapplication.Model.ProductCategory;
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

    @POST("views/product_category_get_all.php")
    Call<List<ProductCategory>> getAllCategories();

    @POST("views/product_insert.php")
    Call<ResponseModel> insert(@Body Product product);

    @POST("views/product_get_by_id.php")
    Call<Product> getProductById(@Body Product product);

    @POST("views/product_update.php")
    Call<ResponseModel> update(@Body Product product);

    @POST("views/product_delete.php")
    Call<ResponseModel> delete(@Body Product product);
}
