package com.huydh54.fpolyapp.ViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.huydh54.fpolyapp.Fragment.TrangDangNhap.DangKyFragment;
import com.huydh54.fpolyapp.Fragment.TrangDangNhap.DangNhapFragment;

public class ViewPagerDangNhap extends FragmentStatePagerAdapter {
    public ViewPagerDangNhap(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DangNhapFragment();
            case 1:
                return new DangKyFragment();
            default:
                return new DangNhapFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
