package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.Product;
import com.example.myapplication.R;

import org.w3c.dom.Text;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private List<Product> data;
    private Context ctx;

    public ProductAdapter(List<Product> data, Context ctx) {
        this.data = data;
        this.ctx = ctx;
    }

    public void updateData(List<Product> data){
        data.clear();
        data.addAll(data);
        notifyDataSetChanged();
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
            TextView name = (TextView) view.findViewById(R.id.textViewProductName);
            TextView price = (TextView) view.findViewById(R.id.textViewProductPrice);
            ImageView img = (ImageView) view.findViewById(R.id.imageViewProduct);
            ViewHolder holder = new ViewHolder(name, price, img);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        Product p = (Product) getItem(_i);

        holder.productName.setText(p.getProduct_name());
        holder.productPrice.setText(p.getPrice() + "");

        Glide.with(ctx)
                .load(p.getImage_url())
                .into(holder.productImage);


        return view;
    }

    private static class ViewHolder{
        final TextView productName, productPrice;
        final ImageView productImage;
        public ViewHolder(TextView _name, TextView _price, ImageView imageView){
            this.productImage = imageView;
            this.productName = _name;
            this.productPrice = _price;
        }
    }
}
