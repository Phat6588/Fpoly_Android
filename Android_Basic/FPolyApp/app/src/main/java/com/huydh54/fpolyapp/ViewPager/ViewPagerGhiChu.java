package com.huydh54.fpolyapp.ViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.huydh54.fpolyapp.Fragment.TrangGhiChu.GhiChuFragment;
import com.huydh54.fpolyapp.Fragment.TrangGhiChu.ViecCanLamFragment;

public class ViewPagerGhiChu extends FragmentStatePagerAdapter {

    public ViewPagerGhiChu(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new GhiChuFragment();
            case 1:
                return new ViecCanLamFragment();
            default:
                return new GhiChuFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
