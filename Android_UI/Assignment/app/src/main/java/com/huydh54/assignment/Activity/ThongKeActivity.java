package com.huydh54.assignment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.huydh54.assignment.DAO.KhoanDAO;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.Khoan;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class ThongKeActivity extends AppCompatActivity {

    Toolbar toolbar;
    BarChart barChart;
    TextView soDuDau, soDuCuoi, thuNhapRong, khoanThu, khoanChi;
    KhoanDAO khoanDAO;
    LoaiDAO loaiDAO;
    ArrayList<Khoan> dsKhoan;
    double tongThu = 0, tongChi = 0, rong = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        barChart = findViewById(R.id.barChart);
        soDuDau = findViewById(R.id.txtGiaTriSoDuDau);
        soDuCuoi = findViewById(R.id.txtGiaTriSoDuCuoi);
        thuNhapRong = findViewById(R.id.txtGiaTriThuNhapRong);
        khoanThu = findViewById(R.id.txtGiaTriKhoanThu);
        khoanChi = findViewById(R.id.txtGiaTriKhoanChi);
        khoanDAO = new KhoanDAO(ThongKeActivity.this);
        loaiDAO = new LoaiDAO(ThongKeActivity.this);
        dsKhoan = khoanDAO.doc();
        toolbar = findViewById(R.id.tbThongKe);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

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
    }
}