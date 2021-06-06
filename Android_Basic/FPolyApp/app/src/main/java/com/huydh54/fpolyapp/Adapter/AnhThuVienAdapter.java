package com.huydh54.fpolyapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huydh54.fpolyapp.Model.AnhThuVien;
import com.huydh54.fpolyapp.Model.ChucNang;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;

public class AnhThuVienAdapter extends BaseAdapter {
    Context context;
    ArrayList<AnhThuVien> dsAnh = new ArrayList<AnhThuVien>();

    public AnhThuVienAdapter(Context context, ArrayList<AnhThuVien> dsAnh) {
        this.context = context;
        this.dsAnh = dsAnh;
    }

    @Override
    public int getCount() {
        return dsAnh.size();
    }

    @Override
    public Object getItem(int i) {
        return dsAnh.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AnhThuVienAdapter.ViewHolder viewHolder;
        if(view == null){
            viewHolder = new AnhThuVienAdapter.ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.item_anhthuvien, null);
            viewHolder.anh = view.findViewById(R.id.imgAnhThuVien);
            viewHolder.ten = view.findViewById(R.id.txtTenAnh);
            view.setTag(viewHolder);
        }else{
            viewHolder = (AnhThuVienAdapter.ViewHolder)view.getTag();
        }
        String imgName = dsAnh.get(i).getTenAnh();
        int imgID = ((Activity) context).getResources().getIdentifier(
                imgName, "drawable", ((Activity) context).getPackageName()
        );
        try {
            viewHolder.anh.setImageResource(imgID);
        }catch (Exception e){
            viewHolder.anh.setImageResource(R.drawable.avatar_loi);
        }
        if(viewHolder.anh.getDrawable() == null){
            viewHolder.anh.setImageResource(R.drawable.avatar_loi);
        }
        viewHolder.ten.setText(dsAnh.get(i).getTenAnh());
        return view;
    }

    public class ViewHolder{
        ImageView anh;
        TextView ten;
    }
}
