package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HandlerActivity extends AppCompatActivity {

    private Button button, button2, button3;
    private TextView textView, textView2, textView3;
    private int number1, number2;

    private final int MIN = 10;
    private final int MAX = 900;
    private final int TEXTVIEW1 = 1;
    private final int TEXTVIEW2 = 2;

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case TEXTVIEW1:
                    textView.setText(msg.obj.toString() + "");
                    break;
                case TEXTVIEW2:
                    textView2.setText(msg.obj.toString() + "");
                    break;
                default: break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       myHandler(TEXTVIEW1);
                   }
               }).start();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        myHandler(TEXTVIEW2);
                    }
                }).start();
            }
        });
    }

    private void myHandler(int flag){
        int b = (int)(Math.random()*(MAX-MIN+1)+MIN);
        int count = 1;
        while (true){

            Message message = new Message();
            message.what = flag;
            message.obj = b;
            handler.sendMessage(message);

            SystemClock.sleep(100);
            b++;
            count++;
            if (b >1000){
                b = 1;
            }
            if (count > 100){
                break;
            }
        };
    }
}