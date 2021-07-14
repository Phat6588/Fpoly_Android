package com.huydh54.assignment.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huydh54.assignment.R;

public class ViewHolderLoai extends RecyclerView.ViewHolder {
    public ImageView anh;
    public TextView ten;
    public TextView phanLoai;
    public ImageView edit, delete;

    public ViewHolderLoai(@NonNull View itemView) {
        super(itemView);
        this.anh = itemView.findViewById(R.id.imgAnhLoaiRV);
        this.ten = itemView.findViewById(R.id.txtTenLoaiRV);
        this.phanLoai = itemView.findViewById(R.id.txtPhanLoaiRV);
        this.edit = itemView.findViewById(R.id.imgCapNhatLoaiRV);
        this.delete = itemView.findViewById(R.id.imgXoaLoaiRV);
    }
}
