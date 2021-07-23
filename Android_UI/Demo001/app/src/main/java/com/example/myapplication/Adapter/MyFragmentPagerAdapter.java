package com.example.myapplication.Adapter;

import com.example.myapplication.Fragment.FifthFragment;
import com.example.myapplication.Fragment.FourthFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyFragmentPagerAdapter extends FragmentStateAdapter {
    final int TAB_COUNT = 2;
    private String[] titles = new String[]{"Khoan thu", "Khoan chi", "Thong ke"};

    public MyFragmentPagerAdapter(FragmentActivity fa){
        super(fa);
    }
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (position){
            case 0:
                fragmentClass = FifthFragment.class;
                break;
            case 1:
                fragmentClass = FourthFragment.class;
                break;
            case 2:
                fragmentClass = FifthFragment.class;
                break;
            default:
                fragmentClass = FifthFragment.class;
                break;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e){}
        return fragment;
    }

    @Override
    public int getItemCount() {
        return TAB_COUNT;
    }
}
