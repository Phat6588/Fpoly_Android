package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DemoFragmentActivity extends AppCompatActivity
        implements SecondFragment.OnItemSelectedListener{

    private Button change1, change2, change3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_fragment);

        change1 = (Button) findViewById(R.id.buttonChangeFragment1);
        change2 = (Button) findViewById(R.id.buttonChangeFragment2);
        change3 = (Button) findViewById(R.id.buttonChangeFragment3);

        change1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                Class fragmentClass = FirstFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.your_placeholder, fragment).commit();
            }
        });
        change2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                SecondFragment fragmentDemo = SecondFragment.newInstance("my title");
//                ft.replace(R.id.your_placeholder, fragmentDemo);
//                ft.commit();


                SecondFragment fragment = SecondFragment.newInstance("my haha");
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.your_placeholder, fragment).commit();
            }
        });
        change3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                Class fragmentClass = ThirdFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.your_placeholder, fragment).commit();
            }
        });
    }

    @Override
    public void onRssItemSelected(String link) {
        Toast.makeText(this, link, Toast.LENGTH_LONG).show();
    }
}