package com.huydh54.assignment.Fragment.MainFragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.huydh54.assignment.DAO.KhoanDAO;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.Khoan;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class ThongKeFragment extends Fragment {

    Toolbar toolbar;
    BarChart barChart;
    TextView soDuDau, soDuCuoi, thuNhapRong, khoanThu, khoanChi;
    KhoanDAO khoanDAO;
    LoaiDAO loaiDAO;
    ArrayList<Khoan> dsKhoan;
    double tongThu = 0, tongChi = 0, rong = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);
//        toolbar = getActivity().findViewById(R.id.toolBar);
//        toolbar.setElevation(9);

        barChart = view.findViewById(R.id.barChart);
        soDuDau = view.findViewById(R.id.txtGiaTriSoDuDau);
        soDuCuoi = view.findViewById(R.id.txtGiaTriSoDuCuoi);
        thuNhapRong = view.findViewById(R.id.txtGiaTriThuNhapRong);
        khoanThu = view.findViewById(R.id.txtGiaTriKhoanThu);
        khoanChi = view.findViewById(R.id.txtGiaTriKhoanChi);
        khoanDAO = new KhoanDAO(view.getContext());
        loaiDAO = new LoaiDAO(view.getContext());
        dsKhoan = khoanDAO.doc();

        for(int i=0; i<dsKhoan.size(); i++){
            Loai loai = loaiDAO.timTheoMa(dsKhoan.get(i).getMaLop());
            if(loai.getPhanLoai().equals("Loại thu") || loai.getTen().equals("Đi vay") || loai.getTen().equals("Thu nợ")){
                tongThu += dsKhoan.get(i).getSoTien();
            }else{
                tongChi += dsKhoan.get(i).getSoTien();
            }
        }
        rong = tongThu - tongChi;

        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        String patternTienTe = "###,###,### ₫";
        DecimalFormat formatTienTe = new DecimalFormat(patternTienTe, decimalFormatSymbols);
        soDuCuoi.setText(formatTienTe.format(rong));
        thuNhapRong.setText(formatTienTe.format(rong));
        khoanThu.setText(formatTienTe.format(tongThu));
        khoanChi.setText(formatTienTe.format(tongChi));

        ArrayList<BarEntry> listThu = new ArrayList<>();
        listThu.add(new BarEntry(2, 3600000));
        listThu.add(new BarEntry(3, 4200000));
        listThu.add(new BarEntry(4, (float) tongThu));
        ArrayList<BarEntry> listChi = new ArrayList<>();
        listChi.add(new BarEntry(2.5f, 3300000));
        listChi.add(new BarEntry(3.5f, 3900000));
        listChi.add(new BarEntry(4.5f, (float) tongChi));

        BarDataSet barDataSetThu = new BarDataSet(listThu, "Tổng thu");
        barDataSetThu.setColor(Color.parseColor("#FF2196F3"));
        BarDataSet barDataSetChi = new BarDataSet(listChi, "Tổng chi");
        barDataSetChi.setColor(Color.parseColor("#F44336"));

        BarData barData = new BarData(barDataSetThu, barDataSetChi);
        barChart.setData(barData);

        String[] thang = new String[]{"Tháng 2, Tháng 3, Tháng 4"};
        XAxis xAxis = barChart.getXAxis();
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(thang));
//        xAxis.setCenterAxisLabels(false);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3);
        float barSpace = 0;
        float groupSpace = 0.11f;
        barData.setBarWidth(0.11f);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace, barSpace)*3.5f);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.invalidate();
        barChart.getDescription().setText("Tháng 4");
        barChart.animateY(1000);

        return view;
    }
}