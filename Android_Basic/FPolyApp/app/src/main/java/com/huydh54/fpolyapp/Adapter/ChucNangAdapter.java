package com.huydh54.fpolyapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huydh54.fpolyapp.Model.Chat;
import com.huydh54.fpolyapp.Model.ChucNang;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;

public class ChucNangAdapter extends BaseAdapter {
    Context context;
    ArrayList<ChucNang> dsChucNang = new ArrayList<ChucNang>();

    public ChucNangAdapter(Context context, ArrayList<ChucNang> dsChucNang) {
        this.context = context;
        this.dsChucNang = dsChucNang;
    }

    @Override
    public int getCount() {
        return dsChucNang.size();
    }

    @Override
    public Object getItem(int i) {
        return dsChucNang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChucNangAdapter.ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ChucNangAdapter.ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.item_chucnang, null);
            viewHolder.anh = view.findViewById(R.id.imgAnhCN);
            viewHolder.ten = view.findViewById(R.id.txtTenCN);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ChucNangAdapter.ViewHolder)view.getTag();
        }
        viewHolder.anh.setImageResource(dsChucNang.get(i).getAnh());
        viewHolder.ten.setText(dsChucNang.get(i).getTen());
        return view;
    }

    public class ViewHolder{
        ImageView anh;
        TextView ten;
    }
}
