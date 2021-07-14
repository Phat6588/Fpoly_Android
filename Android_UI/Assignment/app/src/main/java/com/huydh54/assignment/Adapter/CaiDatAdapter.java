package com.huydh54.assignment.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huydh54.assignment.Activity.TruotActivity;
import com.huydh54.assignment.Model.CaiDat;
import com.huydh54.assignment.R;

import java.util.ArrayList;

public class CaiDatAdapter extends BaseAdapter {
    Context context;
    ArrayList<CaiDat> dsCaiDat = new ArrayList<CaiDat>();

    public CaiDatAdapter(Context context, ArrayList<CaiDat> dsTuyChon2) {
        this.context = context;
        this.dsCaiDat = dsTuyChon2;
    }

    @Override
    public int getCount() {
        return dsCaiDat.size();
    }

    @Override
    public Object getItem(int i) {
        return dsCaiDat.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new CaiDatAdapter.ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.item_caidat, null);
            viewHolder.hinh = view.findViewById(R.id.imgIconCaiDat);
            viewHolder.tieuDe = view.findViewById(R.id.txtCaiDat);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.hinh.setImageResource(dsCaiDat.get(i).getHinh());
        viewHolder.tieuDe.setText(dsCaiDat.get(i).getTieuDe());
        return view;
    }

    public class ViewHolder{
        ImageView hinh;
        TextView tieuDe;
    }
}
