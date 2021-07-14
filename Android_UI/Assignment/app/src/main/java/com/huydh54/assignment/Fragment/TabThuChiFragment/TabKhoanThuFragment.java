package com.huydh54.assignment.Fragment.TabThuChiFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.huydh54.assignment.Adapter.KhoanAdapter;
import com.huydh54.assignment.DAO.KhoanDAO;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.Khoan;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

public class TabKhoanThuFragment extends Fragment {

    RecyclerView rvKhoanThu;
    KhoanAdapter khoanAdapter;
    ArrayList<Khoan> dsKhoan;
    ArrayList<Loai> dsLoai;
    KhoanDAO khoanDAO;
    LoaiDAO loaiDAO;
    TextView thang, nam, tong;
    Calendar lich = Calendar.getInstance();
    String stringThang = "";
    double tongSo = 0;
    Button nutThemKhoan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_khoan, container, false);

        rvKhoanThu = view.findViewById(R.id.rvKhoanThu);
        dsKhoan = new ArrayList<>();
        khoanDAO = new KhoanDAO(view.getContext());
        loaiDAO = new LoaiDAO(view.getContext());
        thang = view.findViewById(R.id.txtThang);
        nam = view.findViewById(R.id.txtNam);
        tong = view.findViewById(R.id.txtTong);
        stringThang = (lich.get(Calendar.MONTH)+1) + "";
        thang.setText("tháng " + stringThang);
        nam.setText(lich.get(Calendar.YEAR)+"");
        nutThemKhoan = view.findViewById(R.id.btnThemKhoan);

        dsKhoan = khoanDAO.doc();
        dsLoai = loaiDAO.doc();
        ArrayList<Khoan> dsKhoanThu = new ArrayList<>();
        ArrayList<Integer> dsViTriThu = new ArrayList<>();
        for (int i=0; i<dsKhoan.size(); i++){
            int maLop = dsKhoan.get(i).getMaLop();
            Loai loai = loaiDAO.timTheoMa(maLop);
            if(loai.getPhanLoai().equals("Loại thu")){
                dsKhoanThu.add(dsKhoan.get(i));
                dsViTriThu.add(i);
            }else if(loai.getPhanLoai().equals("Vay nợ") &&
                    (loai.getTen().equals("Đi vay") || loai.getTen().equals("Thu nợ"))){
                dsKhoanThu.add(dsKhoan.get(i));
                dsViTriThu.add(i);
            }
        }
        if(dsKhoanThu.size()==0){
            nutThemKhoan.setVisibility(View.VISIBLE);
        }else {
            nutThemKhoan.setVisibility(View.GONE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rvKhoanThu.setLayoutManager(linearLayoutManager);
        ViewPager viewPager = getParentFragment().getView().findViewById(R.id.vpKhung);
        khoanAdapter = new KhoanAdapter(dsKhoanThu, view.getContext(), loaiDAO, dsViTriThu, khoanDAO);
        rvKhoanThu.setAdapter(khoanAdapter);
        if(stringThang.length()==1){
            stringThang = "0" + stringThang;
        }
        String thangNay = stringThang + "/" + lich.get(Calendar.YEAR);
        for(int i=0; i<dsKhoanThu.size(); i++){
            String thangDoiTuong = dsKhoanThu.get(i).getNgay().substring(3);
            if(thangDoiTuong.equals(thangNay)){
                tongSo += dsKhoanThu.get(i).getSoTien();
            }
        }
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        String patternTienTe = "###,###,### ₫";
        DecimalFormat formatTienTe = new DecimalFormat(patternTienTe, decimalFormatSymbols);
        String stringTienTe= formatTienTe.format(tongSo);
        tong.setText(stringTienTe);
        TextView soTienVao = getActivity().findViewById(R.id.txtSoTienVao);
        TextView soTienRa = getActivity().findViewById(R.id.txtSoTienRa);
        TextView soTienTong = getActivity().findViewById(R.id.txtSoTienTong);
        soTienVao.setText(stringTienTe);
        double tienVao = chuyenChuThanhSo(soTienVao.getText().toString());
        double tienRa = chuyenChuThanhSo(soTienRa.getText().toString());
        double tienTong = tienVao - tienRa;
        soTienTong.setText(formatTienTe.format(tienTong));
        return view;
    }
    private double chuyenChuThanhSo(String stringTienTe) {
        String[] daySo = stringTienTe.substring(0, stringTienTe.length()-2).split("");
        String soNoi = "";
        for(int i=0; i<daySo.length; i++){
            if(daySo[i].equals(",")){
                continue;
            }else{
                soNoi+=daySo[i];
            }
        }
        return Double.parseDouble(soNoi);
    }

}