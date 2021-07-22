package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.myapplication.Adapter.MyFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class TabsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        ViewPager viewPager = (ViewPager) findViewById(R.id.my_view_pager);
        viewPager.setAdapter(
                new MyFragmentPagerAdapter(getSupportFragmentManager(),
                        TabsActivity.this)
                );
        TabLayout tabLayout = (TabLayout) findViewById(R.id.my_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}