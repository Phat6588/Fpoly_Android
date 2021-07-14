package com.huydh54.assignment.ViewPager;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.huydh54.assignment.Fragment.TabThuChiFragment.TabLoaiChi2Fragment;
import com.huydh54.assignment.Fragment.TabThuChiFragment.TabLoaiChiFragment;
import com.huydh54.assignment.Fragment.TabThuChiFragment.TabLoaiThu2Fragment;
import com.huydh54.assignment.Fragment.TabThuChiFragment.TabLoaiThuFragment;

public class ViewPagerLoai extends FragmentStatePagerAdapter {

    public ViewPagerLoai(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TabLoaiThu2Fragment();
            case 1:
                return new TabLoaiChi2Fragment();
            default:
                return new TabLoaiThu2Fragment();
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
                title = "LOẠI THU";
                break;
            case 1:
                title = "LOẠI CHI";
                break;
        }
        return title;
    }

}
