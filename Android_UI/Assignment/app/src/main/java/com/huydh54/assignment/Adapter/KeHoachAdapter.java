package com.huydh54.assignment.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huydh54.assignment.Model.TuyChonKeHoach;
import com.huydh54.assignment.R;

import java.util.ArrayList;

public class KeHoachAdapter extends BaseAdapter {
    Context context;
    ArrayList<TuyChonKeHoach> dsKeHoach = new ArrayList<TuyChonKeHoach>();

    public KeHoachAdapter(Context context, ArrayList<TuyChonKeHoach> dsKeHoach) {
        this.context = context;
        this.dsKeHoach = dsKeHoach;
    }

    @Override
    public int getCount() {
        return dsKeHoach.size();
    }

    @Override
    public Object getItem(int i) {
        return dsKeHoach.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new KeHoachAdapter.ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.item_gioi_thieu, null);
            viewHolder.hinh = view.findViewById(R.id.imgIconKeHoach);
            viewHolder.tieuDe = view.findViewById(R.id.txtTieuDeKeHoach);
            viewHolder.noiDung = view.findViewById(R.id.txtNoiDungKeHoach);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.hinh.setImageResource(dsKeHoach.get(i).getAnh());
        viewHolder.tieuDe.setText(dsKeHoach.get(i).getTieuDe());
        viewHolder.noiDung.setText(dsKeHoach.get(i).getNoiDung());
        return view;
    }

    public class ViewHolder{
        ImageView hinh;
        TextView tieuDe, noiDung;
    }
}
