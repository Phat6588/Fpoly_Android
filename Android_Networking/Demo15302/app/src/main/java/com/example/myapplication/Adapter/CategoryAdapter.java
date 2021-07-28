package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.Models.ProductCategory;
import com.example.myapplication.Models.Product;
import com.example.myapplication.R;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private List<ProductCategory> data;
    private Context context;

    public CategoryAdapter(List<ProductCategory> data, Context context) {
        this.data = data;
        this.context = context;
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
            view = View.inflate(_viewGroup.getContext(), R.layout.layout_category_item, null);
            TextView textViewName = (TextView) view.findViewById(R.id.textViewCategoryName);
            ViewHolder holder = new ViewHolder(textViewName);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        ProductCategory p = (ProductCategory) getItem(_i);
        holder.categoryName.setText(p.getName());

        return view;
    }

    private static class ViewHolder{
        final TextView categoryName;

        public ViewHolder(TextView name) {
            this.categoryName = name;
        }
    }
}
