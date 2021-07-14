package com.huydh54.assignment.ViewPager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.huydh54.assignment.Fragment.ChonFragment.ChonLoaiChiFragment;
import com.huydh54.assignment.Fragment.ChonFragment.ChonLoaiThuFragment;
import com.huydh54.assignment.Fragment.ChonFragment.VayNoFragment;
import com.huydh54.assignment.Fragment.TabThuChiFragment.TabLoaiChi2Fragment;
import com.huydh54.assignment.Fragment.TabThuChiFragment.TabLoaiThu2Fragment;

public class ViewPagerChonLoai extends FragmentStatePagerAdapter {

    public ViewPagerChonLoai(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new VayNoFragment();
            case 1:
                return new ChonLoaiThuFragment();
            case 2:
                return new ChonLoaiChiFragment();
            default:
                return new ChonLoaiThuFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "ĐI VAY & CHO VAY";
                break;
            case 1:
                title = "LOẠI THU";
                break;
            case 2:
                title = "LOẠI CHI";
                break;
        }
        return title;
    }

}
