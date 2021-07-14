package com.huydh54.assignment.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huydh54.assignment.DAO.KhoanDAO;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.Khoan;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class Khoan2Adapter extends BaseAdapter {
    public ArrayList<Khoan> dsKhoan;
    Context context;
    LoaiDAO loaiDAO;
    KhoanDAO khoanDAO;
    ArrayList<Integer> dsViTri;

    public Khoan2Adapter(ArrayList<Khoan> dsKhoan, Context context, LoaiDAO loaiDAO, ArrayList<Integer> dsViTri, KhoanDAO khoanDAO) {
        this.dsKhoan = dsKhoan;
        this.context = context;
        this.loaiDAO = loaiDAO;
        this.dsViTri = dsViTri;
        this.khoanDAO = khoanDAO;
    }

    @Override
    public int getCount() {
        return dsKhoan.size();
    }

    @Override
    public Object getItem(int i) {
        return dsKhoan.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Khoan2Adapter.ViewHolder viewHolder;
        if(view == null){
            viewHolder = new Khoan2Adapter.ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.item_khoan, null);
            viewHolder.anh = view.findViewById(R.id.imgAnhKhoan);
            viewHolder.ten = view.findViewById(R.id.txtTenKhoan);
            viewHolder.ngay = view.findViewById(R.id.txtNgayKhoan);
            viewHolder.soTien = view.findViewById(R.id.txtSoTienKhoan);
            view.setTag(viewHolder);
        }else{
            viewHolder = (Khoan2Adapter.ViewHolder)view.getTag();
        }
        Loai loai = loaiDAO.timTheoMa(dsKhoan.get(i).getMaLop());
        String tenAnh = loai.getAnh();
        int imgID = view.getContext().getResources().getIdentifier(
                tenAnh, "drawable", view.getContext().getPackageName()
        );
        try {
            viewHolder.anh.setImageResource(imgID);
        }catch (Exception e){
            viewHolder.anh.setImageResource(R.drawable.load_loai_loi);
        }
        viewHolder.ten.setText(loai.getTen());
        viewHolder.ngay.setText(dsKhoan.get(i).getNgay());

        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        String patternTienTe = "###,###,###";
        DecimalFormat formatTienTe = new DecimalFormat(patternTienTe, decimalFormatSymbols);
        String stringTienTe= formatTienTe.format(dsKhoan.get(i).getSoTien());

        viewHolder.soTien.setText(stringTienTe);
        if(loai.getPhanLoai().equals("Loại thu")){
            viewHolder.soTien.setTextColor(Color.parseColor("#FF2196F3"));
        }else if(loai.getPhanLoai().equals("Vay nợ") &&
                (loai.getTen().equals("Đi vay") || loai.getTen().equals("Thu nợ"))){
            viewHolder.soTien.setTextColor(Color.parseColor("#FF2196F3"));
        }

        viewHolder.soTien.setText(stringTienTe);

        return view;
    }

    public class ViewHolder{
        ImageView anh;
        TextView ten;
        TextView ngay;
        TextView soTien;
    }
}
