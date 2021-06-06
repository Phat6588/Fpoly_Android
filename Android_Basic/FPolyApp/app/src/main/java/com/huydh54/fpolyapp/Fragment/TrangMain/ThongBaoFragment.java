package com.huydh54.fpolyapp.Fragment.TrangMain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.huydh54.fpolyapp.Adapter.ChatAdapter;
import com.huydh54.fpolyapp.Adapter.ThongBaoAdapter;
import com.huydh54.fpolyapp.DAO.ThongBaoDAO;
import com.huydh54.fpolyapp.Model.Chat;
import com.huydh54.fpolyapp.Model.ThongBao;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;

public class ThongBaoFragment extends Fragment {

    ListView lvThongBao;
    ThongBaoDAO thongBaoDAO;
    ThongBaoAdapter thongBaoAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_bao, container, false);

        lvThongBao = view.findViewById(R.id.lvThongBao);
        thongBaoDAO = new ThongBaoDAO(getContext());
        ArrayList<ThongBao> thongBaoList = new ArrayList<>();
//        thongBaoList.add(new ThongBao(R.drawable.ic_thongbao_themmoi, "Thêm mới sinh viên thành công!"));
//        thongBaoList.add(new ThongBao(R.drawable.ic_thongbao_xoa, "Xóa lớp thành công!"));
//        thongBaoList.add(new ThongBao(R.drawable.ic_thongbao_themmoi, "Thêm mới sinh viên thành công!"));
//        thongBaoList.add(new ThongBao(R.drawable.ic_thongbao_themmoi, "Thêm mới lớp thành công!"));
//        thongBaoList.add(new ThongBao(R.drawable.ic_thongbao_tinnhan, "Bạn có tin nhắn mới!"));
//        thongBaoList.add(new ThongBao(R.drawable.ic_thongbao_themmoi, "Thêm mới khóa học thành công!"));
//        thongBaoList.add(new ThongBao(R.drawable.ic_thongbao_xoa, "Xóa khóa học thành công!"));
//        thongBaoList.add(new ThongBao(R.drawable.ic_thongbao_tinnhan, "Bạn có tin nhắn mới!"));

        thongBaoList = thongBaoDAO.doc();
        ArrayList<ThongBao> listNguoc = new ArrayList<>();
        for (int j=thongBaoList.size()-1; j>=0; j--){
            listNguoc.add(thongBaoList.get(j));
        }
        thongBaoAdapter = new ThongBaoAdapter(view.getContext(), listNguoc);
        lvThongBao.setAdapter(thongBaoAdapter);

        return view;
    }
}