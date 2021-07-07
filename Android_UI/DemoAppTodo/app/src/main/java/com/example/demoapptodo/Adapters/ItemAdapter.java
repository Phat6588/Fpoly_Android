package com.example.demoapptodo.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.demoapptodo.DAO.ItemsDAO;
import com.example.demoapptodo.Models.Items;
import com.example.demoapptodo.R;

import java.util.List;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private List<Items> data;

    public ItemAdapter(Context _context, List<Items> _data) {
        context = _context;
        data = _data;
    }

    public void updateData(List<Items> _data){
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
        if(view == null){
            view = View.inflate(_viewGroup.getContext(), R.layout.layout_adapter_item, null);
            TextView textView = (TextView) view.findViewById(R.id.textViewItemName) ;
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.radioButton) ;
            ViewHolder holder = new ViewHolder(textView, radioButton);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        Items items = (Items) getItem(_i);
        holder.textView.setText(items.getName());
        holder.radioButton.setChecked(items.getStatus());
        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                onStatusChanged(items, b);
            }
        });
        return view;
    }

    private void onStatusChanged(Items items, boolean status){
        ItemsDAO dao = new ItemsDAO(context);
        items.setStatus(status);
        dao.update(items);
    }

    private static class ViewHolder{
        final TextView textView;
        final RadioButton radioButton;
        ViewHolder(TextView _textView, RadioButton _radioButton){
            textView = _textView;
            radioButton = _radioButton;
        }
    }
}
