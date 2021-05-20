package com.example.demo001;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MyTag";

    private EditText txtNoiDung;
    private EditText txtNoiDung1;
    private Button btnShow;
    private TextView lblNoiDung;

    private Button btnGoToStudent;
    private Button btnGoToSecond;

    private TextView txtFromSecond;
    private EditText inputMain;

    private final int DEMO_INTENT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Activity onCreate");

        txtNoiDung = (EditText) findViewById(R.id.txtNoiDung);
        txtNoiDung1 = (EditText) findViewById(R.id.txtNoiDung1);
        btnShow = (Button) findViewById(R.id.btnShow);
        lblNoiDung = (TextView) findViewById(R.id.lblNoiDung);
        btnGoToStudent = (Button) findViewById(R.id.btnGoToStudent);
        btnGoToSecond = (Button) findViewById(R.id.btnGoToSecond);
        txtFromSecond = (TextView) findViewById(R.id.txtFromSecond);
        inputMain = (EditText) findViewById(R.id.inputMain);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nd = txtNoiDung.getText().toString();
                String nd1 = txtNoiDung1.getText().toString();
                float number = Float.parseFloat(nd);
                float number1 = Float.parseFloat(nd1);
                float result = number + number1;
                lblNoiDung.setText(String.valueOf(result));
//                Toast toast =
//                        Toast.makeText(MainActivity.this,
//                                "Lỗi rồi", Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER, 20, 30);
//                toast.show();
            }
        });

        btnGoToStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nd = txtNoiDung.getText().toString();
                String nd1 = txtNoiDung1.getText().toString();
                float number = Float.parseFloat(nd);
                float number1 = Float.parseFloat(nd1);
                float result = number + number1;

                Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                intent.putExtra("data", "Xin chào buổi chiều");
                intent.putExtra("result", result);
                startActivity(intent);
            }
        });

        btnGoToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = inputMain.getText().toString();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("chatMsg", text);

                // truyen du lieu 1 chieu
                // startActivity(intent);

                // truyen du lieu 2 chieu, co feedback
                startActivityForResult(intent, DEMO_INTENT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == DEMO_INTENT){
            String text = data.getStringExtra("chatMsg");
            txtFromSecond.setText(text);
        }
        else {
            txtFromSecond.setText("??!");
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG, "Activity onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "Activity onStop");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG, "Activity onPause");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG, "Activity onResume");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG, "Activity onStart");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i(TAG, "Activity onRestart");
    }
}