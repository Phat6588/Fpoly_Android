package com.huydh54.assignment.Fragment.ChonFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huydh54.assignment.Adapter.ChonLoaiAdapter;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;
import com.huydh54.assignment.ViewHolder.ViewHolderChonLoai;

import java.util.ArrayList;

public class ChonLoaiChiFragment extends Fragment {

    RecyclerView rvLoaiChi;
    ChonLoaiAdapter chonLoaiAdapter;
    ArrayList<Loai> dsLoai;
    LoaiDAO loaiDAO;
    Button them;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chon_loai, container, false);

        rvLoaiChi = view.findViewById(R.id.rvLoaiThu);
        dsLoai = new ArrayList<>();
        loaiDAO = new LoaiDAO(view.getContext());
        them = view.findViewById(R.id.btnThemChonLoai);

        them.setText("LOẠI CHI MỚI");

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
        chonLoaiAdapter = new ChonLoaiAdapter(dsLoaiChi, view.getContext(), loaiDAO, dsViTriChi);
        rvLoaiChi.setAdapter(chonLoaiAdapter);

        return view;
    }
}