package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.BackgroundTask.DatabaseBackgroundTask;
import com.example.myapplication.R;

public class PhepToanActivity extends AppCompatActivity {

    private TextView textView3;
    private Button button3;
    private EditText number1, number2, number3;

    private String url = "http://10.0.2.2:8081/demo.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phep_toan);

        textView3 = (TextView) findViewById(R.id.textView3);
        button3 = (Button) findViewById(R.id.button3);
        number1 = (EditText) findViewById(R.id.editTextNumber1);
        number2 = (EditText) findViewById(R.id.editTextNumber2);
        number3 = (EditText) findViewById(R.id.editTextNumber3);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(number1.getText().toString());
                int b = Integer.parseInt(number2.getText().toString());
                int c = Integer.parseInt(number3.getText().toString());
                url += "a=" + a + "&b=" + b + "&c=" + c;
                DatabaseBackgroundTask backgroundTask = new DatabaseBackgroundTask(PhepToanActivity.this);
                backgroundTask.execute(url);

            }
        });
    }
}