package com.huydh54.assignment.ViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.huydh54.assignment.Fragment.MainFragment.GioiThieuFragment;
import com.huydh54.assignment.Fragment.MainFragment.KhoanFragment;
import com.huydh54.assignment.Fragment.MainFragment.Loai2Fragment;
import com.huydh54.assignment.Fragment.MainFragment.LoaiFragment;
import com.huydh54.assignment.Fragment.MainFragment.ThongKeFragment;

public class ViewPagerMain extends FragmentStatePagerAdapter {

    public ViewPagerMain(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Loai2Fragment();
            case 1:
                return new KhoanFragment();
            case 2:
                return new GioiThieuFragment();
            default:
                return new Loai2Fragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
