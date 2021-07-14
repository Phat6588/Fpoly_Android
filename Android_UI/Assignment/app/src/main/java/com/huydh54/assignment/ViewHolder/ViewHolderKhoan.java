package com.huydh54.assignment.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huydh54.assignment.Interface.ItemClickListener;
import com.huydh54.assignment.R;

public class ViewHolderKhoan extends RecyclerView.ViewHolder  implements View.OnClickListener,View.OnLongClickListener{
    public ImageView anh;
    public TextView ten;
    public TextView ngay;
    public TextView soTien;

    private ItemClickListener itemClickListener;

    public ViewHolderKhoan(@NonNull View itemView) {
        super(itemView);
        this.anh = itemView.findViewById(R.id.imgAnhKhoan);
        this.ten = itemView.findViewById(R.id.txtTenKhoan);
        this.ngay = itemView.findViewById(R.id.txtNgayKhoan);
        this.soTien = itemView.findViewById(R.id.txtSoTienKhoan);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}
