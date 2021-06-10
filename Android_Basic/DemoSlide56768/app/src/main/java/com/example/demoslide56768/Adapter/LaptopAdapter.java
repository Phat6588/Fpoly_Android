package com.example.demoslide56768.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demoslide56768.Model.Laptop;
import com.example.demoslide56768.R;

import java.util.List;

public class LaptopAdapter extends BaseAdapter {
    private List<Laptop> data;
    private Context context;

    public LaptopAdapter(Context _context,List<Laptop> _data){
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

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int _i, View _view, ViewGroup _viewGroup) {
        View view = _view;
        if (_view == null){
            view = View.inflate(_viewGroup.getContext(),
                    R.layout.laptop_item_layout, null);
        }

        TextView id = (TextView) view.findViewById(R.id.textViewLaptopId);
        TextView name = (TextView) view.findViewById(R.id.textViewLaptopName);
        ImageView image = (ImageView) view.findViewById(R.id.imageViewLaptop1);

        Laptop laptop = (Laptop) getItem(_i);

        id.setText(laptop.getLaptopId());
        name.setText(laptop.getLaptopName());

        // hinh anh
        byte[] imageArray = laptop.getLaptopImage();
        if (imageArray == null){
            image.setImageResource(R.drawable.ic_launcher_background);
        }
        else {
            image.setImageBitmap(BitmapFactory.decodeByteArray(imageArray, 0,
                    imageArray.length));
        }

        return view;
    }
}
