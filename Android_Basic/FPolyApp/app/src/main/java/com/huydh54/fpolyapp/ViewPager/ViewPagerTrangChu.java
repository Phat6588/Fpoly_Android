package com.huydh54.fpolyapp.ViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.huydh54.fpolyapp.Fragment.TrangMain.HocTapFragment;
import com.huydh54.fpolyapp.Fragment.TrangMain.NhanTinFragment;
import com.huydh54.fpolyapp.Fragment.TrangMain.TaiKhoanFragment;
import com.huydh54.fpolyapp.Fragment.TrangMain.ThongBaoFragment;
import com.huydh54.fpolyapp.Fragment.TrangMain.TrangChuFragment;

public class ViewPagerTrangChu extends FragmentStatePagerAdapter {

    public ViewPagerTrangChu(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TrangChuFragment();
            case 1:
                return new ThongBaoFragment();
            case 2:
                return new HocTapFragment();
            case 3:
                return new NhanTinFragment();
            case 4:
                return new TaiKhoanFragment();
            default:
                return new TrangChuFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
