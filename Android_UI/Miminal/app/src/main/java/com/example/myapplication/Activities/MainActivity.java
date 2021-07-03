package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText username, password;
    private Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // kiem tra login, neu da dang nhap >>> chay qua man hinh welcome
        // nguoc lai cho dang nhap
        checkLoginStatus();

        username = (TextInputEditText) findViewById(R.id.editTextUsername);
        password = (TextInputEditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _username = username.getText().toString();
                String _pass = password.getText().toString();
                if (_username.equals("fpoly") && _pass.equals("123")){
                    // luu trang thai dang nhap
                    SharedPreferences.Editor editor =
                            getSharedPreferences("login_status",MODE_PRIVATE).edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();
                    // chuyen man hinh
                    Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                else {
                    Toast.makeText(MainActivity.this,
                            "Login failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkLoginStatus(){
        SharedPreferences editor =
                getSharedPreferences("login_status",MODE_PRIVATE);
        boolean isLoggedIn = editor.getBoolean("isLoggedIn",false);
        if (isLoggedIn == true){
            startActivity(new Intent(getApplicationContext(), TaskActivity.class));
            finish();
            return;
        }
    }
}