package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.DAO.UsersDAO;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText username, password;
    private Button buttonLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // kiem tra login, neu da dang nhap >>> chay qua man hinh welcome
        // nguoc lai cho dang nhap
//        checkLoginStatus();

        username = (TextInputEditText) findViewById(R.id.editTextUsername);
        password = (TextInputEditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _username = username.getText().toString();
                String _pass = password.getText().toString();
                // dung FB
                signIn(_username, _pass);

//
//                UsersDAO dao = new UsersDAO(MainActivity.this);
//                boolean isLoggedIn = dao.login(_username, _pass);
//                if (isLoggedIn == true){
//                    // luu trang thai dang nhap
//                    SharedPreferences.Editor editor =
//                            getSharedPreferences("login_status",MODE_PRIVATE).edit();
//                    editor.putBoolean("isLoggedIn", true);
//                    editor.apply();
//                    // chuyen man hinh
//                    Intent intent = new Intent(MainActivity.this, TaskActivity.class);
//                    startActivity(intent);
//                    finish();
//                    return;
//                }
//                else {
//                    Toast.makeText(MainActivity.this,
//                            "Login failed", Toast.LENGTH_LONG).show();
//                }
            }
        });
    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.

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

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(MainActivity.this, TaskActivity.class);
            startActivity(intent);
            finish();
        }
        else {

        }
    }
}