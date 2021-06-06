package com.huydh54.fpolyapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.huydh54.fpolyapp.Model.KhoaHoc;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;

public class KhoaHocAdapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<KhoaHoc> dsKhoaHoc = new ArrayList<KhoaHoc>();
    ArrayList<KhoaHoc> dsKhoaHocLoc = new ArrayList<KhoaHoc>();

    public KhoaHocAdapter(Context context, ArrayList<KhoaHoc> dsKhoaHoc) {
        this.context = context;
        this.dsKhoaHoc = dsKhoaHoc;
        this.dsKhoaHocLoc = dsKhoaHoc;
    }

    @Override
    public int getCount() {
        return dsKhoaHoc.size();
    }

    @Override
    public Object getItem(int i) {
        return dsKhoaHoc.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        KhoaHocAdapter.ViewHolder viewHolder;
        if(view == null){
            viewHolder = new KhoaHocAdapter.ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.item_khoahoc, null);
            viewHolder.ma= view.findViewById(R.id.txtMaKH);
            viewHolder.anh = view.findViewById(R.id.imgAnhKH);
            viewHolder.thoiGian= view.findViewById(R.id.txtNgayBatDau);
            viewHolder.ten= view.findViewById(R.id.txtTenKH);
            view.setTag(viewHolder);
        }else{
            viewHolder = (KhoaHocAdapter.ViewHolder)view.getTag();
        }
        viewHolder.ma.setText(dsKhoaHoc.get(i).getMa());
        String imgName = dsKhoaHoc.get(i).getAnh();
        int imgID = ((Activity) context).getResources().getIdentifier(
                imgName, "drawable", ((Activity) context).getPackageName()
        );
        try {
            viewHolder.anh.setImageResource(imgID);
        }catch (Exception e){
            viewHolder.anh.setImageResource(R.drawable.load_no_image);
        }
        if(viewHolder.anh.getDrawable() == null){
            viewHolder.anh.setImageResource(R.drawable.load_no_image);
        }
        viewHolder.thoiGian.setText("Bắt đầu: " + dsKhoaHoc.get(i).getThoiGian());
        viewHolder.ten.setText(dsKhoaHoc.get(i).getTen());
        return view;
    }

    public class ViewHolder{
        ImageView anh;
        TextView ma, ten, thoiGian;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String tuKhoa = charSequence.toString();
                if(tuKhoa.isEmpty()){
                    dsKhoaHoc = dsKhoaHocLoc;
                }else {
                    ArrayList<KhoaHoc> dsKhoaHocXuLy = new ArrayList<>();
                    for (KhoaHoc khoaHoc: dsKhoaHocLoc){
                        if(khoaHoc.getTen().toLowerCase().contains(tuKhoa)){
                            dsKhoaHocXuLy.add(khoaHoc);
                        }else if(khoaHoc.getMa().toLowerCase().contains(tuKhoa)){
                            dsKhoaHocXuLy.add(khoaHoc);
                        }
                    }
                    dsKhoaHoc = dsKhoaHocXuLy;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = dsKhoaHoc;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dsKhoaHoc = (ArrayList<KhoaHoc>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
