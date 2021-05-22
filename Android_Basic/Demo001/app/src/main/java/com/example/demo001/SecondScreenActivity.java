package com.example.demo001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

public class SecondScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        Intent intent = getIntent();
        String data = intent.getStringExtra("myData");
        Toast toast = Toast.makeText(SecondScreenActivity.this,
                                data, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 20, 30);
        toast.show();
    }
}