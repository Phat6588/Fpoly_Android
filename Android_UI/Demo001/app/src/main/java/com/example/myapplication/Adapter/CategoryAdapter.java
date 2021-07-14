package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.Models.Category;
import com.example.myapplication.R;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    private Context ctx;
    private List<Category> data;
    public CategoryAdapter(Context _ctx, List<Category> _data){
        ctx = _ctx;
        data = _data;
    }

    public void updateData (List<Category> _data){
        data.clear();
        data.addAll(_data);
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
            view = View.inflate(_viewGroup.getContext(), R.layout.layout_category_item, null);
            TextView textView = (TextView) view.findViewById(R.id.textView2);
            ViewHolder holder = new ViewHolder(textView);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        Category category = (Category) getItem(_i);
        holder.textView.setText(category.getName());
        return view;
    }

    private static class ViewHolder{
        final TextView textView;
        public ViewHolder(TextView textView) {
            this.textView = textView;
        }
    }
}
