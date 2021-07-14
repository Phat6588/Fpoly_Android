package com.huydh54.assignment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.huydh54.assignment.Adapter.ChonLoaiAdapter;
import com.huydh54.assignment.R;
import com.huydh54.assignment.ViewPager.ViewPagerChonLoai;

public class ChonLoaiActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerChonLoai viewPagerChonLoai;
    String bang = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_loai);

        toolbar = findViewById(R.id.tbChonLoai);
        tabLayout = findViewById(R.id.tabLayoutChonLoai);
        viewPager = findViewById(R.id.vpKhungChonLoai);

        viewPagerChonLoai = new ViewPagerChonLoai(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerChonLoai);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(1).select();

        SearchView searchView = (SearchView) toolbar.getMenu().findItem(R.id.action_search).getActionView();
        ImageView icon = searchView.findViewById(androidx.appcompat.R.id.search_button);
        icon.setColorFilter(Color.BLACK);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }
}