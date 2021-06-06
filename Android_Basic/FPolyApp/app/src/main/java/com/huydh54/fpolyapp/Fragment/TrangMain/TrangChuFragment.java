package com.huydh54.fpolyapp.Fragment.TrangMain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.huydh54.fpolyapp.Adapter.ChatAdapter;
import com.huydh54.fpolyapp.Adapter.ChucNangAdapter;
import com.huydh54.fpolyapp.Model.ChucNang;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;

public class TrangChuFragment extends Fragment {

    GridView gvChucNang;
    ChucNangAdapter chucNangAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);

        gvChucNang = view.findViewById(R.id.gv_ChucNang);
        ArrayList<ChucNang> chucNangList = new ArrayList<>();
        chucNangList.add(new ChucNang(R.drawable.ic_chucnang_diemdanh, "Điểm danh"));
        chucNangList.add(new ChucNang(R.drawable.ic_chucnang_lichhoc, "Lịch học"));
        chucNangList.add(new ChucNang(R.drawable.ic_chucnang_diemthi, "Điểm thi"));
        chucNangList.add(new ChucNang(R.drawable.ic_chucnang_khenthuong, "Khen thưởng"));
        chucNangList.add(new ChucNang(R.drawable.ic_chucnang_dichvu, "Dịch vụ"));
        chucNangList.add(new ChucNang(R.drawable.ic_chucnang_sms, "SMS"));

        chucNangAdapter = new ChucNangAdapter(view.getContext(), chucNangList);
        gvChucNang.setAdapter(chucNangAdapter);

        return view;
    }
}