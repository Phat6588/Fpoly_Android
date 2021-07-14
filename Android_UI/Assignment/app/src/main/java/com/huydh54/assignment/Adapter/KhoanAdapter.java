package com.huydh54.assignment.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.huydh54.assignment.Activity.ChiTietKhoanActivity;
import com.huydh54.assignment.DAO.KhoanDAO;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Interface.ItemClickListener;
import com.huydh54.assignment.Model.Khoan;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;
import com.huydh54.assignment.ViewHolder.ViewHolderKhoan;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class KhoanAdapter extends RecyclerView.Adapter<ViewHolderKhoan> {
    public ArrayList<Khoan> dsKhoan;
    Context context;
    LoaiDAO loaiDAO;
    KhoanDAO khoanDAO;
    ArrayList<Integer> dsViTri;

    public KhoanAdapter(ArrayList<Khoan> dsKhoan, Context context, LoaiDAO loaiDAO, ArrayList<Integer> dsViTri, KhoanDAO khoanDAO) {
        this.dsKhoan = dsKhoan;
        this.context = context;
        this.loaiDAO = loaiDAO;
        this.dsViTri = dsViTri;
        this.khoanDAO = khoanDAO;
    }

    @NonNull
    @Override
    public ViewHolderKhoan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_khoan,parent,false);
        return new ViewHolderKhoan(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderKhoan holder, int position) {
        String bang;
        Khoan khoan = dsKhoan.get(position);
        int maLop = khoan.getMaLop();
        Loai loai = loaiDAO.timTheoMa(maLop);
        String tenAnh = loai.getAnh();
        int imgID = context.getResources().getIdentifier(
                tenAnh, "drawable", context.getPackageName()
        );
        try {
            holder.anh.setImageResource(imgID);
        }catch (Exception e){
            holder.anh.setImageResource(R.drawable.load_loai_loi);
        }
        holder.ten.setText(loai.getTen());
        holder.ngay.setText(khoan.getNgay());

        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        String patternTienTe = "###,###,###";
        DecimalFormat formatTienTe = new DecimalFormat(patternTienTe, decimalFormatSymbols);
        String stringTienTe= formatTienTe.format(khoan.getSoTien());

        holder.soTien.setText(stringTienTe);
        if(loai.getPhanLoai().equals("Loại thu")){
            holder.soTien.setTextColor(Color.parseColor("#FF2196F3"));
            bang = "Loại thu";
        }else if(loai.getPhanLoai().equals("Vay nợ") &&
                (loai.getTen().equals("Đi vay") || loai.getTen().equals("Thu nợ"))){
            holder.soTien.setTextColor(Color.parseColor("#FF2196F3"));
            bang = "Loại thu";
        }else{
            bang = "Loại chi";
        }
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick==false){
                    int ma = khoanDAO.getMa(dsViTri.get(position));
                    Intent intent = new Intent(context, ChiTietKhoanActivity.class);
                    Bundle bundleGui = new Bundle();
                    bundleGui.putInt("makhoan", ma);
                    bundleGui.putInt("vitri", position);
                    bundleGui.putInt("vitridata", dsViTri.get(position));
                    bundleGui.putString("bang", bang);
                    intent.putExtra("boxma", bundleGui);
                    ((Activity) context).startActivityForResult(intent, 222);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dsKhoan.size();
    }

}
