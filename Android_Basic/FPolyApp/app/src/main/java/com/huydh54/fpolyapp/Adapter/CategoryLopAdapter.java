package com.huydh54.fpolyapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huydh54.fpolyapp.Model.Category;
import com.huydh54.fpolyapp.R;

import java.util.List;

public class CategoryLopAdapter extends ArrayAdapter<Category> {
    public CategoryLopAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giaodienlop_spinner, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.txtSelected);
        Category category = this.getItem(position);
        if(category != null){
            tvSelected.setText(category.getMa());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner, parent, false);
        TextView tvCategory = convertView.findViewById(R.id.txtCategoryMaLop);
        Category category = this.getItem(position);
        if(category != null){
            tvCategory.setText(category.getMa());
        }
        return convertView;
    }
}
