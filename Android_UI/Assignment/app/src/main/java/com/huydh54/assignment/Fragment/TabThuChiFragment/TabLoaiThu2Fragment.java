package com.huydh54.assignment.Fragment.TabThuChiFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.huydh54.assignment.Adapter.Loai2Adapter;
import com.huydh54.assignment.Adapter.LoaiAdapter;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;

import java.util.ArrayList;

public class TabLoaiThu2Fragment extends Fragment {

    RecyclerView rvLoaiThu;
    Loai2Adapter loai2Adapter;
    ArrayList<Loai> dsLoai;
    LoaiDAO loaiDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_loai_2, container, false);

        rvLoaiThu = view.findViewById(R.id.rvLoaiThu);
        dsLoai = new ArrayList<>();
        loaiDAO = new LoaiDAO(view.getContext());

        dsLoai = loaiDAO.doc();
        ArrayList<Loai> dsLoaiThu = new ArrayList<>();
        ArrayList<Integer> dsViTriThu = new ArrayList<>();
        for (int i=0; i<dsLoai.size(); i++){
            if(dsLoai.get(i).getPhanLoai().equals("Loại thu")){
                dsLoaiThu.add(dsLoai.get(i));
                dsViTriThu.add(i);
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rvLoaiThu.setLayoutManager(linearLayoutManager);
        ViewPager viewPager = getParentFragment().getView().findViewById(R.id.vpKhung);
        loai2Adapter = new Loai2Adapter(dsLoaiThu, view.getContext(), dsViTriThu, viewPager);
        rvLoaiThu.setAdapter(loai2Adapter);

        return view;
    }
}