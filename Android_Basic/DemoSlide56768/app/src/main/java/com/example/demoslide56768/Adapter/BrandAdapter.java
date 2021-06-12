package com.example.demoslide56768.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.demoslide56768.Model.Brand;
import com.example.demoslide56768.Model.Laptop;
import com.example.demoslide56768.R;

import java.util.List;


public class BrandAdapter extends BaseAdapter {

    private Context context;
    private List<Brand> data;

    public BrandAdapter (Context _context,List<Brand> _data){
        context = _context;
        data = _data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    public int getPositon(String brandId){
        int index = -1;
        for (int i = 0; i < data.size(); i++) {
            Brand brand = data.get(i);
            if (brand.getBrandId().equals(brandId)){
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int _i, View _view, ViewGroup _viewGroup) {
        View view = _view;
        if (_view == null){
            view = View.inflate(_viewGroup.getContext(),
                    R.layout.brand_item_spinner_layout, null);
        }

        TextView brandName = (TextView) view.findViewById(R.id.textViewBrandItem);
        Brand brand = (Brand) getItem(_i);

        brandName.setText(brand.getBrandName());

        return view;
    }
}
