package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationActivity extends AppCompatActivity implements FirstFragment.OnTextViewClickListener {

    Fragment fragment1, fragment2, fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        if (savedInstanceState == null){
            fragment1 = new FirstFragment();
            fragment2 = SecondFragment.newInstance("Xin chao chi ong nau");
            fragment3 = new ThirdFragment();
        }

        BottomNavigationView bottomNavigationView =
                (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_contact:
                        displayFrgment2();
                        break;
                    case R.id.action_person:
                        displayFrgment3();
                        break;
                    default:
                        displayFrgment1();
                        break;
                }
                return true;
            }
        });

        // set default selected
        bottomNavigationView.setSelectedItemId(R.id.action_music);

    }

    private void displayFrgment1(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragment1.isAdded()){
            ft.show(fragment1);
        } else {
            ft.add(R.id.my_frame_container, fragment1, "fragment1");
        }
        if(fragment2.isAdded()){
            ft.hide(fragment2);
        }
        if(fragment3.isAdded()){
            ft.hide(fragment3);
        }
        ft.commit();
    }

    private void displayFrgment2(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragment2.isAdded()){
            ft.show(fragment2);
        } else {
            ft.add(R.id.my_frame_container, fragment2, "fragment2");
        }
        if(fragment1.isAdded()){
            ft.hide(fragment1);
        }
        if(fragment3.isAdded()){
            ft.hide(fragment3);
        }
        ft.commit();
    }

    private void displayFrgment3(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragment3.isAdded()){
            ft.show(fragment3);
        } else {
            ft.add(R.id.my_frame_container, fragment3, "fragment3");
        }
        if(fragment1.isAdded()){
            ft.hide(fragment1);
        }
        if(fragment2.isAdded()){
            ft.hide(fragment2);
        }
        ft.commit();
    }

    @Override
    public void onTextViewClick(String text) {

    }
}