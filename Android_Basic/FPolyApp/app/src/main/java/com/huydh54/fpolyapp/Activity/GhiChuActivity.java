package com.huydh54.fpolyapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.huydh54.fpolyapp.R;
import com.huydh54.fpolyapp.ViewPager.ViewPagerGhiChu;
import com.huydh54.fpolyapp.ViewPager.ViewPagerTrangChu;

public class GhiChuActivity extends AppCompatActivity {

    BottomNavigationView menuChanTrang;
    ViewPager khungHienThi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghi_chu);

        menuChanTrang = findViewById(R.id.menuChanGhiChu);
        khungHienThi = findViewById(R.id.vpKhungGhiChu);

        caiDatKhungGhiChu();

        menuChanTrang.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ghichu:
                        khungHienThi.setCurrentItem(0);
                        break;
                    case R.id.vieccanlam:
                        khungHienThi.setCurrentItem(1);
                        break;
                }
                return true;
            }
        });
    }

    private void caiDatKhungGhiChu(){
        ViewPagerGhiChu viewPagerGhiChu = new ViewPagerGhiChu(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        khungHienThi.setAdapter(viewPagerGhiChu);

        khungHienThi.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        menuChanTrang.getMenu().getItem(0).setChecked(true);
                        break;
                    case 1:
                        menuChanTrang.getMenu().getItem(1).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}