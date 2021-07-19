package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationActivity extends AppCompatActivity
implements SecondFragment.OnItemSelectedListener{
    TextView bottomText;

    Fragment fragment1 ;
    Fragment fragment2 ;
    Fragment fragment3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        bottomText = (TextView) findViewById(R.id.bottomText);

        if(savedInstanceState == null){
            fragment1 = new FirstFragment();
            fragment2 = SecondFragment.newInstance("my haha");
            fragment3 = new ThirdFragment();
        }


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_favorites:
                        displayFragment1();
                        break;
                    case R.id.action_schedules:
                        displayFragment2();
                        break;
                    case R.id.action_music:
                    default:
                        displayFragment3();
                        break;
                }
                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_favorites);
    }

    @Override
    public void onRssItemSelected(String link) {
        bottomText.setText(link);
    }

    protected void displayFragment1() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // removes the existing fragment calling onDestroy
        if (fragment1.isAdded()) { // if the fragment is already in container
            ft.show(fragment1);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.flContainer, fragment1, "A");
        }
        // Hide fragment B
        if (fragment2.isAdded()) { ft.hide(fragment2); }
        // Hide fragment C
        if (fragment3.isAdded()) { ft.hide(fragment3); }
        // Commit changes
        ft.commit();
    }

    protected void displayFragment2() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // removes the existing fragment calling onDestroy
        if (fragment2.isAdded()) { // if the fragment is already in container
            ft.show(fragment2);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.flContainer, fragment2, "B");
        }
        // Hide fragment B
        if (fragment1.isAdded()) { ft.hide(fragment1); }
        // Hide fragment C
        if (fragment3.isAdded()) { ft.hide(fragment3); }
        // Commit changes
        ft.commit();
    }

    protected void displayFragment3() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // removes the existing fragment calling onDestroy
        if (fragment3.isAdded()) { // if the fragment is already in container
            ft.show(fragment3);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.flContainer, fragment3, "B");
        }
        // Hide fragment B
        if (fragment1.isAdded()) { ft.hide(fragment1); }
        // Hide fragment C
        if (fragment2.isAdded()) { ft.hide(fragment2); }
        // Commit changes
        ft.commit();
    }
}