package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.Product;
import com.example.myapplication.R;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private List<Product> data;
    private Context context;

    public ProductAdapter(List<Product> data, Context context) {
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
            view = View.inflate(_viewGroup.getContext(), R.layout.layout_product_item, null);
            TextView textViewName = (TextView) view.findViewById(R.id.textViewProductName);
            TextView textViewPrice = (TextView) view.findViewById(R.id.textViewProductPrice);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageViewProduct);
            ViewHolder holder = new ViewHolder(textViewName, textViewPrice, imageView);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        Product p = (Product) getItem(_i);
        holder.name.setText(p.getName());
        holder.price.setText(String.valueOf(p.getPrice()));

        Glide.with(context).load(p.getImage_url())
                .into(holder.imageView);

        return view;
    }

    private static class ViewHolder{
        final TextView name, price;
        final ImageView imageView;

        public ViewHolder(TextView name, TextView price, ImageView imageView) {
            this.name = name;
            this.price = price;
            this.imageView = imageView;
        }
    }
}
