package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.myapplication.DAO.ItemsDAO;
import com.example.myapplication.Models.Items;
import com.example.myapplication.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private List<Items> data;
    final CollectionReference reference = FirebaseFirestore.getInstance().collection("items");

    public ItemAdapter(Context _context, List<Items> _data) {
        context = _context;
        data = _data;
    }

    public void updateData(List<Items> _data) {
        data.clear();
        data.addAll(_data);
        this.notifyDataSetChanged();
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
        if (view == null) {
            view = View.inflate(_viewGroup.getContext(), R.layout.layout_each_item, null);
            ViewHolder holder = createViewHolderFrom(view);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        Items item = (Items) getItem(_i);
        holder.text.setText(item.getName());
        holder.checkBox.setChecked(item.isStatus());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                updateItem(item, b);
            }
        });
        return view;

    }

    private void updateItem(Items item, boolean isChecked){
//        dùng sqlite
        ItemsDAO dao = new ItemsDAO(context);
        item.setStatus(isChecked);
        dao.update(item);

//        dùng fb
//        Map map = new HashMap<String, Object>();
//        map.put("id", item.getId());
//        map.put("name", item.getName());
//        map.put("status", isChecked == true ? "1" : "0");
//        map.put("taskId", item.getTaskId());
//        reference.document(item.getId()).set(map, SetOptions.merge());

    }

    private ViewHolder createViewHolderFrom(View view) {
        TextView textView = (TextView) view.findViewById(R.id.todoName);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkButton);
        return new ViewHolder(textView, checkBox);
    }

    private static class ViewHolder {
        final TextView text;
        final CheckBox checkBox;
        ViewHolder(TextView text, CheckBox checkBox) {
            this.text = text;
            this.checkBox = checkBox;
        }
    }
}
