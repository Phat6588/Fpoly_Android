package com.huydh54.assignment.Fragment.ChonFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.huydh54.assignment.Adapter.ChonLoaiAdapter;
import com.huydh54.assignment.Adapter.Loai2Adapter;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;

import java.util.ArrayList;

public class ChonLoaiThuFragment extends Fragment {

    RecyclerView rvLoaiThu;
    ChonLoaiAdapter chonLoaiAdapter;
    ArrayList<Loai> dsLoai;
    LoaiDAO loaiDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chon_loai, container, false);

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
        chonLoaiAdapter = new ChonLoaiAdapter(dsLoaiThu, view.getContext(), loaiDAO, dsViTriThu);
        rvLoaiThu.setAdapter(chonLoaiAdapter);

        return view;
    }
}