package com.huydh54.fpolyapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huydh54.fpolyapp.Model.CaiDat;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;

public class SapXepKHAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> dsTuyChon = new ArrayList<>();

    public SapXepKHAdapter(Context context, ArrayList<String> dsTuyChon) {
        this.context = context;
        this.dsTuyChon = dsTuyChon;
    }

    @Override
    public int getCount() {
        return dsTuyChon.size();
    }

    @Override
    public Object getItem(int i) {
        return dsTuyChon.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new SapXepKHAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_tuychonsapxep, null);
            viewHolder.tuyChon = view.findViewById(R.id.txtTenTuyChonSX);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.tuyChon.setText(dsTuyChon.get(i));
        return view;
    }

    public class ViewHolder{
        TextView tuyChon;
    }
}
