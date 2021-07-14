package com.huydh54.assignment.Fragment.TabThuChiFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.huydh54.assignment.Adapter.LoaiAdapter;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;

import java.util.ArrayList;

public class TabLoaiChiFragment extends Fragment {

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

//        loaiDAO.them("load_loai_eat", "Ăn uống", "Loại chi");
//        loaiDAO.them("load_loai_gas", "Đổ xăng", "Loại chi");
//        loaiDAO.them("load_loai_cinema", "Xem phim", "Loại chi");
//        loaiDAO.them("load_loai_birthday", "Sinh nhật", "Loại chi");
//        loaiDAO.them("load_loai_doctor", "Khám bệnh", "Loại chi");
//        loaiDAO.them("load_loai_medicine", "Thuốc thang", "Loại chi");
//        loaiDAO.them("load_loai_dog", "Thức ăn cho chó", "Loại chi");
//        loaiDAO.them("load_loai_fix", "Sửa xe", "Loại chi");
//        loaiDAO.them("load_loai_box", "Chuyển phát nhanh", "Loại chi");
//        loaiDAO.them("load_loai_shopping", "Mua sắm", "Loại chi");

        dsLoai = loaiDAO.doc();

        ArrayList<Loai> dsLoaiChi = new ArrayList<>();
        ArrayList<Integer> dsViTriChi = new ArrayList<>();
        for (int i=0; i<dsLoai.size(); i++){
            if(dsLoai.get(i).getPhanLoai().equals("Loại chi")){
                dsLoaiChi.add(dsLoai.get(i));
                dsViTriChi.add(i);
            }
        }
        ViewPager viewPager = getParentFragment().getView().findViewById(R.id.vpKhung);
        loaiAdapter = new LoaiAdapter(view.getContext(), dsLoaiChi, dsViTriChi, viewPager);
        lvLoaiThu.setAdapter(loaiAdapter);

        return view;
    }
}