package com.example.demoapptodo.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.demoapptodo.DAO.ItemsDAO;
import com.example.demoapptodo.Models.Items;
import com.example.demoapptodo.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demoapptodo.Utilities.Constants.COLUMN_ITEM_ID;
import static com.example.demoapptodo.Utilities.Constants.COLUMN_ITEM_NAME;
import static com.example.demoapptodo.Utilities.Constants.COLUMN_ITEM_STATUS;
import static com.example.demoapptodo.Utilities.Constants.COLUMN_ITEM_TASK_ID;
import static com.example.demoapptodo.Utilities.Constants.STATUS_FALSE;
import static com.example.demoapptodo.Utilities.Constants.STATUS_TRUE;
import static com.example.demoapptodo.Utilities.Constants.TABLE_TODO_ITEMS;

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
            CheckBox checkboxButton = (CheckBox) view.findViewById(R.id.checkboxButton) ;
            ViewHolder holder = new ViewHolder(textView, checkboxButton);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        Items items = (Items) getItem(_i);
        holder.textView.setText(items.getName());
        holder.checkboxButton.setChecked(items.getStatus());
        holder.checkboxButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                onStatusChanged(items, b);
            }
        });
        return view;
    }

    private void onStatusChanged(Items items, boolean status){
        // dùng sqlite
        // ItemsDAO dao = new ItemsDAO(context);
        // items.setStatus(status);
        // dao.update(items);


        // dùng firebase

        final CollectionReference reference = FirebaseFirestore.getInstance().collection(TABLE_TODO_ITEMS);
        Map map = new HashMap<String, Object>();
        map.put(COLUMN_ITEM_ID, items.getId());
        map.put(COLUMN_ITEM_NAME, items.getName());
        map.put(COLUMN_ITEM_STATUS, status == true ? STATUS_TRUE : STATUS_FALSE);
        map.put(COLUMN_ITEM_TASK_ID, items.getTaskId());
        reference.document(items.getId()).set(map, SetOptions.merge());

        reference.document(items.getId()).delete();
    }

    private static class ViewHolder{
        final TextView textView;
        final CheckBox checkboxButton;
        ViewHolder(TextView _textView, CheckBox _checkboxButton){
            textView = _textView;
            checkboxButton = _checkboxButton;
        }
    }
}











