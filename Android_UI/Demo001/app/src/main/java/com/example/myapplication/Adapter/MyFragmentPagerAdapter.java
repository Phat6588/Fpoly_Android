package com.example.myapplication.Adapter;

import android.content.Context;

import com.example.myapplication.FifthFragment;
import com.example.myapplication.FourthFragment;
import com.example.myapplication.ThirdFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyFragmentPagerAdapter extends FragmentStateAdapter {
    final int TAB_COUNT = 9;
    private String[] titles = new String[]{"Khoan thu", "Khoan chi", "Thong ke", "Khoan thu", "Khoan chi", "Thong ke", "Khoan thu", "Khoan chi", "Thong ke"};
    private Context ctx;

    public MyFragmentPagerAdapter(FragmentActivity fa){
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
                fragmentClass = FifthFragment.class;
                break;
            case 2:
                fragmentClass = FifthFragment.class;
                break;
            default:
                fragmentClass = ThirdFragment.class;
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
