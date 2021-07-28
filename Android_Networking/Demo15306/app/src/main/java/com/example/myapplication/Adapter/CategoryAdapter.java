package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.Product;
import com.example.myapplication.Model.ProductCategory;
import com.example.myapplication.R;

public class CategoryAdapter extends BaseAdapter {

    private List<ProductCategory> data;
    private Context ctx;

    public CategoryAdapter(List<ProductCategory> data, Context ctx) {
        this.data = data;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int _i, View _view, ViewGroup _viewGroup) {
        View view = _view;
        if (view == null){
            view = View.inflate(_viewGroup.getContext(),
                    R.layout.layout_product_category_item, null);
            TextView name = (TextView) view.findViewById(R.id.textViewProductCategoryName);
            ViewHolder holder = new ViewHolder(name);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        ProductCategory p = (ProductCategory) getItem(_i);
        holder.name.setText(p.getCategory_name());
        return view;
    }

    private static class ViewHolder{
        final TextView name;
        public ViewHolder(TextView _name){
            this.name = _name;
        }
    }
}
