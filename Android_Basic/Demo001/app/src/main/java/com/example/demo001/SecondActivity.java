package com.example.demo001;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView txtFromMain;
    private EditText inputSecond;
    private Button btnSecond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        txtFromMain = (TextView) findViewById(R.id.txtFromMain);
        inputSecond = (EditText) findViewById(R.id.inputSecond);
        btnSecond = (Button) findViewById(R.id.btnSecond);

        Intent intent = getIntent();
        String text = intent.getStringExtra("chatMsg");
        txtFromMain.setText(text);

        btnSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = inputSecond.getText().toString();
                Intent intent1 = new Intent();
                intent1.putExtra("chatMsg", text);
                setResult(Activity.RESULT_OK, intent1);
                finish();
            }
        });

    }
}