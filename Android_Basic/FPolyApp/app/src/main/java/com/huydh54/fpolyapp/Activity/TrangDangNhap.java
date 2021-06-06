package com.huydh54.fpolyapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huydh54.fpolyapp.R;
import com.huydh54.fpolyapp.ViewPager.ViewPagerDangNhap;

public class TrangDangNhap extends AppCompatActivity {

    ViewPager khungHienThi;
    TextView txtDangKy, txtCauHoi, txtTieuDe;
    int item = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_dang_nhap);

        khungHienThi = findViewById(R.id.vpKhungNhap);
        txtDangKy = findViewById(R.id.txtDangKy);
        txtCauHoi = findViewById(R.id.txtCauHoi);
        txtTieuDe = findViewById(R.id.txtTieuDe);

        caiDatKhung();

        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item == 0) item = 1;
                else item = 0;
                khungHienThi.setCurrentItem(item);
            }
        });
    }

    private void caiDatKhung() {
        ViewPagerDangNhap viewPagerDangNhap = new ViewPagerDangNhap(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        khungHienThi.setAdapter(viewPagerDangNhap);

        khungHienThi.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    txtDangKy.setText("Đăng ký");
                    txtCauHoi.setText("Bạn chưa có tài khoản?");
                    txtTieuDe.setText("Đăng nhập");
                }else{
                    txtDangKy.setText("Đăng nhập");
                    txtCauHoi.setText("Bạn đã có tài khoản?");
                    txtTieuDe.setText("Đăng ký");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}