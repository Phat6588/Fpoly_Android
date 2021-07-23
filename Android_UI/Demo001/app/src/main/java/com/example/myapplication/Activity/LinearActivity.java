package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LinearActivity extends AppCompatActivity {

    private TextInputEditText textInputEditText;
    private TextInputLayout textInputLayout;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear);

        textInputEditText = (TextInputEditText) findViewById(R.id.editTextPassword);
        textInputLayout = (TextInputLayout) findViewById(R.id.editTextPasswordLayout);
        button = (Button) findViewById(R.id.buttonLogin);

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pattern = "[a-zA-Z0-9]{6,8}";
                if(charSequence.toString().matches(pattern) == false){
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError("Password 6-8 chars");
                    button.setEnabled(false);
                }
                else {
                    textInputLayout.setErrorEnabled(false);
                    textInputLayout.setError("");
                    button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}