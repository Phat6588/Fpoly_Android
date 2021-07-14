package com.huydh54.assignment.ViewPager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.huydh54.assignment.Fragment.TabThuChiFragment.TabKhoanChiFragment;
import com.huydh54.assignment.Fragment.TabThuChiFragment.TabKhoanThuFragment;
import com.huydh54.assignment.Fragment.TabThuChiFragment.TabLoaiChiFragment;

public class ViewPagerKhoan extends FragmentStatePagerAdapter {

    public ViewPagerKhoan(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TabKhoanThuFragment();
            case 1:
                return new TabKhoanChiFragment();
            default:
                return new TabKhoanThuFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "KHOẢN THU";
                break;
            case 1:
                title = "KHOẢN CHI";
                break;
        }
        return title;
    }
}
