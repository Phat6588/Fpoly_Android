package com.huydh54.assignment.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.huydh54.assignment.Activity.ThuVien;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Interface.ItemClickListener;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;
import com.huydh54.assignment.ViewHolder.ViewHolderChonLoai;

import java.util.ArrayList;

public class ChonLoai2Adapter extends BaseAdapter {
    ArrayList<Loai> dsLoai;
    Context context;
    LoaiDAO loaiDAO;
    ArrayList<Integer> dsViTri;

    public ChonLoai2Adapter(ArrayList<Loai> dsLoai, Context context, LoaiDAO loaiDAO, ArrayList<Integer> dsViTri) {
        this.dsLoai = dsLoai;
        this.context = context;
        this.loaiDAO = loaiDAO;
        this.dsViTri = dsViTri;
    }

    @Override
    public int getCount() {
        return dsLoai.size();
    }

    @Override
    public Object getItem(int i) {
        return dsLoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChonLoai2Adapter.ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ChonLoai2Adapter.ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.item_chonloai, null);
            viewHolder.anh = view.findViewById(R.id.imgAnhChonLoai);
            viewHolder.ten = view.findViewById(R.id.txtTenChonLoai);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ChonLoai2Adapter.ViewHolder)view.getTag();
        }
        String tenAnh = dsLoai.get(i).getAnh();
        int imgID = view.getContext().getResources().getIdentifier(
                tenAnh, "drawable", view.getContext().getPackageName()
        );
        try {
            viewHolder.anh.setImageResource(imgID);
        }catch (Exception e){
            viewHolder.anh.setImageResource(R.drawable.load_loai_loi);
        }
        viewHolder.ten.setText(dsLoai.get(i).getTen());

        return view;
    }

    public class ViewHolder{
        ImageView anh;
        TextView ten;
    }
}
