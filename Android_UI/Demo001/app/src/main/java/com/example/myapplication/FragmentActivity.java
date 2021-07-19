package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FragmentActivity extends AppCompatActivity
        implements FirstFragment.OnTextViewClickListener{

    private Button btn1, btn2, btn3;
    private TextView textFrgment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        btn1 = (Button) findViewById(R.id.btnFragment1);
        btn2 = (Button) findViewById(R.id.btnFragment2);
        btn3 = (Button) findViewById(R.id.btnFragment3);

        textFrgment = (TextView) findViewById(R.id.textFrgment);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                Class fragmentClass = FirstFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e){}
                getSupportFragmentManager().beginTransaction()
                            .replace(R.id.my_frame, fragment).commit();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondFragment fragment = SecondFragment.newInstance("xin chao buoi trua");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.my_frame, fragment).commit();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                Class fragmentClass = ThirdFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e){}
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.my_frame, fragment).commit();
            }
        });





    }

    @Override
    public void onTextViewClick(String text) {
        textFrgment.setText(text);
    }
}