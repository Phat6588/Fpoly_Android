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

import com.example.myapplication.Models.Person;
import com.example.myapplication.MyRetrofit.IRetrofitService;
import com.example.myapplication.MyRetrofit.RetrofitBuilder;
import com.example.myapplication.R;

import java.util.List;

public class PersonActivity extends AppCompatActivity {

    private IRetrofitService service;

    private TextView myTextView;
    private Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        service = RetrofitBuilder.createService(IRetrofitService.class);

        myTextView = (TextView) findViewById(R.id.myTextView);
        myButton = (Button) findViewById(R.id.myButton);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // service.getOne().enqueue(getOneCallback);
                // service.getArray().enqueue(getArrayCallback);
                service.getOneByParam(1).enqueue(getOneCallback);
            }
        });
    }

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