package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Model.Person;
import com.example.myapplication.Model.ResponseModel;
import com.example.myapplication.MyRetrofit.IRetrofitService;
import com.example.myapplication.MyRetrofit.RetrofitBuilder;
import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private Button buttonLogin;
    private static String BASE_URL = "http://10.0.2.2:8081/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _us = username.getText().toString();
                String _pw = password.getText().toString();
                Person p = new Person(_pw, _us);
                service.login(p).enqueue(loginCB);
            }
        });
    }

    Callback<ResponseModel> loginCB = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.isSuccessful()){
                ResponseModel responseModel = response.body();
                if (responseModel.getStatus()){
                    // luu vao Shared preferences

                    startActivity(new Intent(MainActivity.this, ProductActivity.class));
                    finish();
                } else {
                    // Toast
                }
            }else {
            }
        }
        @Override
        public void onFailure(Call<ResponseModel> call, Throwable t) {
            Log.i(">>>>>", call.toString());
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        // kiem tra trong shared preferences
        // neu co ----> chuyen qua Product
        // neu chua co >>> login
    }
}