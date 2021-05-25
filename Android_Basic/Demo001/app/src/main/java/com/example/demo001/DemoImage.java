package com.example.demo001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DemoImage extends AppCompatActivity {

    private ImageView myImageView1;
    private Button btnHinhMot;
    private Button btnHinhHai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_image);

        myImageView1 = (ImageView) findViewById(R.id.myImageView1);
        btnHinhMot = (Button) findViewById(R.id.btnHinhMot);
        btnHinhHai = (Button) findViewById(R.id.btnHinhHai);



        btnHinhMot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myImageView1.setImageResource(R.drawable.hinh001);
            }
        });

        btnHinhHai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myImageView1.setImageResource(R.drawable.hinh002);
            }
        });
    }
}