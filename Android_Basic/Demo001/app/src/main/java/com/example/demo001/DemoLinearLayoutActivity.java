package com.example.demo001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class DemoLinearLayoutActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button button1;
    private Button button2;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_linear_layout);
        this.imageView = (ImageView) this.findViewById(R.id.imageView);

        this.button1 = (Button) this.findViewById(R.id.button1);
        this.button2 = (Button) this.findViewById(R.id.button2);
        this.listView = (ListView) this.findViewById(R.id.mylist);
        String[] arr = new String[]{"aa","bb"};
////        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, , arr);
////        listView.setAdapter(adapter);
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
////            public void onClick(View view) {
////                showImage1();
////            }
//        });
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
////            public void onClick(View view) {
////                showImage2();
////            }
//        });

    }

//    private void showImage1() {
//        this.imageView.setImageResource(R.drawable.hinh1);
//    }
//
//    private void showImage2() {
//        this.imageView.setImageResource(R.drawable.hinh2);
//    }
}