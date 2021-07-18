package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Models.AccessToken;
import com.example.myapplication.Models.AccessTokenManager;
import com.example.myapplication.Models.Person;
import com.example.myapplication.MyRetrofit.IRetrofitService;
import com.example.myapplication.MyRetrofit.RetrofitBuilder;
import com.example.myapplication.R;

import java.util.List;

public class PersonActivity extends AppCompatActivity {

    private IRetrofitService service;
    private AccessTokenManager tokenManager;

    private TextView myTextView;
    private Button myButton, button11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        service = RetrofitBuilder.createService(IRetrofitService.class);
        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        myTextView = (TextView) findViewById(R.id.myTextView);
        myButton = (Button) findViewById(R.id.myButton);
        button11 = (Button) findViewById(R.id.button11);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // service.getOne().enqueue(getOneCallback);
                // service.getArray().enqueue(getArrayCallback);
                // service.getOneByParam(1).enqueue(getOneCallback);
                service.login(new Person("admin", "123")).enqueue(loginCallback);
            }
        });

        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.getOne().enqueue(getOneCallback);
            }
        });
    }

    Callback<AccessToken> loginCallback = new Callback<AccessToken>() {
        @Override
        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
            if (response.isSuccessful()){
                AccessToken p = response.body();
                myTextView.setText(p.getAccess_token());
                tokenManager.saveToken(p);
            } else {
            }
        }

        @Override
        public void onFailure(Call<AccessToken> call, Throwable t) {
            Log.e(">>>>>", t.getMessage());
        }
    };

    Callback<List<Person>> getArrayCallback = new Callback<List<Person>>() {
        @Override
        public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
            if (response.isSuccessful()){
                List<Person> list = response.body();
                String s = "";
                for (int i = 0; i < list.size(); i++){
                    s+= list.get(i).getName() + " ";
                }
                myTextView.setText(s);
            } else {
            }
        }

        @Override
        public void onFailure(Call<List<Person>> call, Throwable t) {}
    };

    Callback<Person> getOneCallback = new Callback<Person>() {
        @Override
        public void onResponse(Call<Person> call, Response<Person> response) {
            if (response.isSuccessful()){
                Person p = response.body();
                myTextView.setText(p.getName());
            } else {
            }
        }

        @Override
        public void onFailure(Call<Person> call, Throwable t) {
            Log.e(">>>>>", t.getMessage());
        }
    };

}