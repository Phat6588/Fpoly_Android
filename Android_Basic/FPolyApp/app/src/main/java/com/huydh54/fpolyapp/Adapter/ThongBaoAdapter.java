package com.huydh54.fpolyapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huydh54.fpolyapp.Model.ThongBao;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;

public class ThongBaoAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThongBao> dsThongBao = new ArrayList<ThongBao>();

    public ThongBaoAdapter(Context context, ArrayList<ThongBao> dsThongBao) {
        this.context = context;
        this.dsThongBao = dsThongBao;
    }

    @Override
    public int getCount() {
        return dsThongBao.size();
    }

    @Override
    public Object getItem(int i) {
        return dsThongBao.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ThongBaoAdapter.ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ThongBaoAdapter.ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.item_thongbao, null);
            viewHolder.anh = view.findViewById(R.id.imgIconTB);
            viewHolder.ten = view.findViewById(R.id.txtNoiDungTB);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ThongBaoAdapter.ViewHolder)view.getTag();
        }
        viewHolder.anh.setImageResource(dsThongBao.get(i).getAnh());
        int imgIDThem = ((Activity) context).getResources().getIdentifier(
                "ic_thongbao_themmoi", "drawable", ((Activity) context).getPackageName()
        );
        int imgIDXoa = ((Activity) context).getResources().getIdentifier(
                "ic_thongbao_xoa", "drawable", ((Activity) context).getPackageName()
        );
        int imgIDTinNhan = ((Activity) context).getResources().getIdentifier(
                "ic_thongbao_tinnhan", "drawable", ((Activity) context).getPackageName()
        );
        if(dsThongBao.get(i).getAnh() == imgIDThem){
            viewHolder.anh.setBackgroundResource(R.drawable.bg_tronluc);
        }
        if(dsThongBao.get(i).getAnh() == imgIDXoa){
            viewHolder.anh.setBackgroundResource(R.drawable.bg_trondo);
        }
        if(dsThongBao.get(i).getAnh() == imgIDTinNhan){
            viewHolder.anh.setBackgroundResource(R.drawable.bg_tronlam);
        }
        viewHolder.ten.setText(dsThongBao.get(i).getTen());
        return view;
    }

    public class ViewHolder{
        ImageView anh;
        TextView ten;
    }
}
