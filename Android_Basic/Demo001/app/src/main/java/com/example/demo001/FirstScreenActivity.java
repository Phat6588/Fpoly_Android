package com.example.demo001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstScreenActivity extends AppCompatActivity {

    private Button btnGoToSecondScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        btnGoToSecondScreen = (Button) findViewById(R.id.btnGoToSecondScreen);

        btnGoToSecondScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstScreenActivity.this,
                        SecondScreenActivity.class);
                intent.putExtra("myData", "Xin chào từ màn hình 1");
                startActivity(intent);
            }
        });
    }
}