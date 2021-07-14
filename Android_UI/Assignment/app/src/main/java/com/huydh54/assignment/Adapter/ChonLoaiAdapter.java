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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.huydh54.assignment.Activity.ThuVien;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Database.MyDatabase;
import com.huydh54.assignment.Interface.ItemClickListener;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;
import com.huydh54.assignment.ViewHolder.ViewHolderChonLoai;
import com.huydh54.assignment.ViewHolder.ViewHolderLoai;

import java.util.ArrayList;

public class ChonLoaiAdapter extends RecyclerView.Adapter<ViewHolderChonLoai> {
    ArrayList<Loai> dsLoai;
    Context context;
    LoaiDAO loaiDAO;
    ArrayList<Integer> dsViTri;

    public ChonLoaiAdapter(ArrayList<Loai> dsLoai, Context context, LoaiDAO loaiDAO, ArrayList<Integer> dsViTri) {
        this.dsLoai = dsLoai;
        this.context = context;
        this.loaiDAO = loaiDAO;
        this.dsViTri = dsViTri;
    }

    @NonNull
    @Override
    public ViewHolderChonLoai onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_chonloai,parent,false);
        return new ViewHolderChonLoai(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderChonLoai holder, int position) {
        Loai loai = dsLoai.get(position);
        String tenAnh = loai.getAnh();
        int imgID = context.getResources().getIdentifier(
                tenAnh, "drawable", context.getPackageName()
        );
        try {
            holder.anh.setImageResource(imgID);
        }catch (Exception e){
            holder.anh.setImageResource(R.drawable.load_loai_loi);
        }
        holder.ten.setText(loai.getTen());
        if(position == dsLoai.size()-1){
            holder.view.setVisibility(View.INVISIBLE);
        }
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick==false){
                    int ma = loaiDAO.getMa(dsViTri.get(position));
                    Intent intent = new Intent();
                    Bundle bundleMa = new Bundle();
                    bundleMa.putInt("ma", ma);
                    intent.putExtra("boxma", bundleMa);
                    ((Activity) context).setResult(Activity.RESULT_OK, intent);
                    ((Activity) context).finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dsLoai.size();
    }
}
