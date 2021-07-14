package com.huydh54.assignment.Fragment.TabThuChiFragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.huydh54.assignment.Adapter.LoaiAdapter;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;

import java.util.ArrayList;

public class TabLoaiThuFragment extends Fragment {

    ListView lvLoaiThu;
    LoaiAdapter loaiAdapter;
    ArrayList<Loai> dsLoai;
    LoaiDAO loaiDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_loai, container, false);

        lvLoaiThu = view.findViewById(R.id.lvLoaiThu);
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
        ViewPager viewPager = getParentFragment().getView().findViewById(R.id.vpKhung);
        loaiAdapter = new LoaiAdapter(view.getContext(), dsLoaiThu, dsViTriThu, viewPager);
        lvLoaiThu.setAdapter(loaiAdapter);

        return view;
    }
}