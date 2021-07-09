package com.example.demoapptodo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapptodo.DAO.UsersDAO;
import com.example.demoapptodo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.demoapptodo.Utilities.Constants.*;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        mAuth = FirebaseAuth.getInstance();


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _username = editTextUsername.getText().toString();
                String _pass = editTextPassword.getText().toString();

                // dùng SQLite
//                UsersDAO dao = new UsersDAO(MainActivity.this);
//                boolean isLoggedIn = dao.login(_username, _pass);
//                if(isLoggedIn == true){
//                    SharedPreferences.Editor editor = getSharedPreferences(LOGIN_STATUS, MODE_PRIVATE).edit();
//                    editor.putBoolean(IS_LOGGED_IN, true);
//                    editor.apply();
//                    Intent intent = new Intent(MainActivity.this, TaskActivity.class);
//                    startActivity(intent);
//                    finish();
//                    return;
//                }
//                else {
//                    editTextUsername.setText("");
//                    editTextPassword.setText("");
//                    Toast.makeText(MainActivity.this, LOGIN_FAILED, Toast.LENGTH_LONG).show();
//                }

                mAuth.signInWithEmailAndPassword(_username, _pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                editTextUsername.setText("");
                                editTextPassword.setText("");
                                Toast.makeText(MainActivity.this, LOGIN_FAILED, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            }
        });
    }

    private void checkLogInStatus() {
        SharedPreferences editor = getSharedPreferences(LOGIN_STATUS, MODE_PRIVATE);
        boolean isLoggedIn = editor.getBoolean(IS_LOGGED_IN, false);
        if (isLoggedIn == true) {
            Intent intent = new Intent(MainActivity.this, TaskActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void checkLogInFB() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(MainActivity.this, TaskActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // dùng SQLite
        // checkLogInStatus();

        // dùng FB
        checkLogInFB();
    }
}