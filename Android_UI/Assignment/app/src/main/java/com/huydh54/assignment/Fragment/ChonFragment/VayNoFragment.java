package com.huydh54.assignment.Fragment.ChonFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huydh54.assignment.Adapter.ChonLoaiAdapter;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;

import java.util.ArrayList;

public class VayNoFragment extends Fragment {

    RecyclerView rvLoaiVay;
    ChonLoaiAdapter chonLoaiAdapter;
    ArrayList<Loai> dsLoai;
    LoaiDAO loaiDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_loai_2, container, false);

        rvLoaiVay = view.findViewById(R.id.rvLoaiThu);
        dsLoai = new ArrayList<>();
        loaiDAO = new LoaiDAO(view.getContext());

        dsLoai = loaiDAO.doc();
        ArrayList<Loai> dsLoaiVay = new ArrayList<>();
        ArrayList<Integer> dsViTriVay = new ArrayList<>();
        for (int i=0; i<dsLoai.size(); i++){
            if(dsLoai.get(i).getPhanLoai().equals("Vay nợ")){
                dsLoaiVay.add(dsLoai.get(i));
                dsViTriVay.add(i);
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rvLoaiVay.setLayoutManager(linearLayoutManager);
        chonLoaiAdapter = new ChonLoaiAdapter(dsLoaiVay, view.getContext(), loaiDAO, dsViTriVay);
        rvLoaiVay.setAdapter(chonLoaiAdapter);

        return view;
    }
}