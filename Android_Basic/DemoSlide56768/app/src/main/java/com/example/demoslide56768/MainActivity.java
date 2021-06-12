package com.example.demoslide56768;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demoslide56768.DAO.UserDAO;


// Adapter
// DAO
// Database
// Model

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // kiem tra login, neu da dang nhap >>> chay qua man hinh welcome
        // nguoc lai cho dang nhap
        checkLoginStatus();

        EditText editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        Button buttonDangNhap = (Button) findViewById(R.id.buttonDangNhap);

        buttonDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                Boolean check = (new UserDAO(MainActivity.this))
                                .login(username, password);
                if (check == true){
                    // luu trang thai dang nhap
                    SharedPreferences.Editor editor =
                            getSharedPreferences("login_status",MODE_PRIVATE).edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    // chuyen man hinh
                    Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
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
            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
            finish();
            return;
        }
    }
}