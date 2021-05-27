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
    private Button btnGoToSecond;

    private TextView txtFromSecond;
    private EditText inputMain;

    private final int DEMO_INTENT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Activity onCreate");


        btnGoToSecond = (Button) findViewById(R.id.btnGoToSecond);
        txtFromSecond = (TextView) findViewById(R.id.txtFromSecond);
        inputMain = (EditText) findViewById(R.id.inputMain);



        btnGoToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = inputMain.getText().toString();
                Intent intent = new Intent(MainActivity.this,
                        SecondActivity.class);
                intent.putExtra("chatMsg", text);

//                Bundle bundle = intent.getExtras();
//                bundle.putString("chatMsg", text);


                // truyen du lieu 1 chieu
//                 startActivity(intent);

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