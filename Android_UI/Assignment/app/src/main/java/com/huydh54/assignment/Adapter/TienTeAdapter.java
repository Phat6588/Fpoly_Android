package com.huydh54.assignment.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huydh54.assignment.Model.TienTe;
import com.huydh54.assignment.R;

import java.util.ArrayList;

public class TienTeAdapter extends BaseAdapter {
    Context context;
    ArrayList<TienTe> dsTienTe = new ArrayList<TienTe>();

    public TienTeAdapter(Context context, ArrayList<TienTe> dsTienTe) {
        this.context = context;
        this.dsTienTe = dsTienTe;
    }

    @Override
    public int getCount() {
        return dsTienTe.size();
    }

    @Override
    public Object getItem(int i) {
        return dsTienTe.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TienTeAdapter.ViewHolder viewHolder;
        if(view == null){
            viewHolder = new TienTeAdapter.ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.item_tiente, null);
            viewHolder.anh = view.findViewById(R.id.imgIconTT);
            viewHolder.ten = view.findViewById(R.id.txtLoaiTien);
            viewHolder.kyHieu = view.findViewById(R.id.txtKyHieu);
            view.setTag(viewHolder);
        }else{
            viewHolder = (TienTeAdapter.ViewHolder)view.getTag();
        }
        viewHolder.anh.setImageResource(dsTienTe.get(i).getAnh());
        viewHolder.ten.setText(dsTienTe.get(i).getTen());
        viewHolder.kyHieu.setText(dsTienTe.get(i).getKyHieu());
        return view;
    }

    public class ViewHolder{
        ImageView anh;
        TextView ten, kyHieu;
    }
}
