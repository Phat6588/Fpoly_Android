package com.huydh54.assignment.Fragment.TabThuChiFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.huydh54.assignment.Adapter.Loai2Adapter;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;

import java.util.ArrayList;

public class TabLoaiChi2Fragment extends Fragment {

    RecyclerView rvLoaiChi;
    Loai2Adapter loai2Adapter;
    ArrayList<Loai> dsLoai;
    LoaiDAO loaiDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_loai_2, container, false);

        rvLoaiChi = view.findViewById(R.id.rvLoaiThu);
        dsLoai = new ArrayList<>();
        loaiDAO = new LoaiDAO(view.getContext());

        dsLoai = loaiDAO.doc();
        ArrayList<Loai> dsLoaiChi = new ArrayList<>();
        ArrayList<Integer> dsViTriChi = new ArrayList<>();
        for (int i=0; i<dsLoai.size(); i++){
            if(dsLoai.get(i).getPhanLoai().equals("Loại chi")){
                dsLoaiChi.add(dsLoai.get(i));
                dsViTriChi.add(i);
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rvLoaiChi.setLayoutManager(linearLayoutManager);
        ViewPager viewPager = getParentFragment().getView().findViewById(R.id.vpKhung);
        loai2Adapter = new Loai2Adapter(dsLoaiChi, view.getContext(), dsViTriChi, viewPager);
        rvLoaiChi.setAdapter(loai2Adapter);

        return view;
    }
}