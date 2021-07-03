package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThreadActivity extends AppCompatActivity {

    private Button button1, button2;
    private TextView textView;

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            textView.setText(msg.obj.toString() + "");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        button1 = (Button) findViewById(R.id.button4);
        button2 = (Button) findViewById(R.id.button5);
        textView = (TextView) findViewById(R.id.textView4);

        // UI Thread
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // background thread
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        SystemClock.sleep(8000);
//                        textView.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                textView.setText("Xin chào buổi tối");
//                            }
//                        });
//                    }
//                }).start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(8000);
                        Message message = new Message();
                        message.obj = "Xin chào Handler";
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Xin chào buổi khuya");
            }
        });
    }
}