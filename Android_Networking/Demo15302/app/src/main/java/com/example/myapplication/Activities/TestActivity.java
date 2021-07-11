package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.BackgroundTasks.TestBackgroundTask;
import com.example.myapplication.R;

public class TestActivity extends AppCompatActivity {

    private TextView textView8;
    private Button button10;
    private EditText number1, number2;

    private String url = "http://10.0.2.2:8081/api/test.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textView8 = (TextView) findViewById(R.id.textView8);
        button10 = (Button) findViewById(R.id.button10);
        number1 = (EditText) findViewById(R.id.number1);
        number2 = (EditText) findViewById(R.id.number2);

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Float a = Float.parseFloat(number1.getText().toString());
                Float b = Float.parseFloat(number2.getText().toString());
                url += "?a=" + a + "&b=" + b;
                TestBackgroundTask task = new TestBackgroundTask(TestActivity.this);
                task.execute(url);
            }
        });
    }
}