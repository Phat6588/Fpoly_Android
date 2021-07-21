package com.example.myapplication;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyFragmentPagerAdapter extends FragmentStateAdapter {
    final int TAB_COUNT = 3;
    private String[] titles = new String[]{"Khoan thu", "Khoan chi", "Thong ke"};
    private Context ctx;

    public MyFragmentPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (position){
            case 0:
                fragmentClass = ThirdFragment.class;
                break;
            case 1:
                fragmentClass = FourthFragment.class;
                break;
            case 2:
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

//    public MyFragmentPagerAdapter(FragmentManager fragmentManager, Context context){
//        super(fragmentManager);
//        ctx = context;
//    }
//    @Override
//    public Fragment getItem(int position) {
//        Fragment fragment = null;
//        Class fragmentClass = null;
//        switch (position){
//            case 0:
//                fragmentClass = ThirdFragment.class;
//                break;
//            case 1:
//                fragmentClass = FourthFragment.class;
//                break;
//            case 2:
//                fragmentClass = FifthFragment.class;
//                break;
//        }
//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e){}
//        return fragment;
//    }
//
//    @Override
//    public int getCount() {
//        return TAB_COUNT;
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return titles[position];
//    }
}
