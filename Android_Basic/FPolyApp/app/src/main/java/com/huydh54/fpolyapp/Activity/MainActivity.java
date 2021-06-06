package com.huydh54.fpolyapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.huydh54.fpolyapp.R;
import com.huydh54.fpolyapp.ViewPager.ViewPagerTrangChu;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView menuChanTrang;
    ViewPager khungHienThi;
    String maTK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuChanTrang = findViewById(R.id.menuChan);
        khungHienThi = findViewById(R.id.vpKhungMain);
//        if(checkLoginRemember()<0){
//            Intent intent = new Intent(MainActivity.this, TrangDangNhap.class);
//            startActivity(intent);
//        }
//        try {
//            Bundle bundleNhanTK = getIntent().getBundleExtra("boxtk");
//            maTK = bundleNhanTK.getString("masv");
//        } catch (Exception e) {
//
//        }

        caiDatKhungMain();

        menuChanTrang.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.trangchu:
                        khungHienThi.setCurrentItem(0);
                        break;
                    case R.id.thongbao:
                        khungHienThi.setCurrentItem(1);
                        break;
                    case R.id.hoctap:
                        khungHienThi.setCurrentItem(2);
                        break;
                    case R.id.nhantin:
                        khungHienThi.setCurrentItem(3);
                        break;
                    case R.id.taikhoan:
                        khungHienThi.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });

    }

    private void caiDatKhungMain() {
        ViewPagerTrangChu viewPagerTrangChu = new ViewPagerTrangChu(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        khungHienThi.setAdapter(viewPagerTrangChu);

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
                    case 2:
                        menuChanTrang.getMenu().getItem(2).setChecked(true);
                        break;
                    case 3:
                        menuChanTrang.getMenu().getItem(3).setChecked(true);
                        break;
                    case 4:
                        menuChanTrang.getMenu().getItem(4).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

//    public int checkLoginRemember(){
//        SharedPreferences sp = getSharedPreferences("luudangnhap.txt", MODE_PRIVATE);
//        boolean check = sp.getBoolean("check", false);
//        if(check){
//            user = sp.getString("user", "");
//            return 1;
//        }
//        return -1;
//    }
}