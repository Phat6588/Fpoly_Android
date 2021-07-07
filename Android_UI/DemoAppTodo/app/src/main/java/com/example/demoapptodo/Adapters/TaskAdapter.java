package com.example.demoapptodo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.demoapptodo.Activities.ItemActivity;
import com.example.demoapptodo.Models.Items;
import com.example.demoapptodo.Models.Tasks;
import com.example.demoapptodo.R;

import java.util.List;

import static com.example.demoapptodo.Utilities.Constants.*;

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private List<Tasks> data;

    public TaskAdapter(Context _context, List<Tasks> _data) {
        context = _context;
        data = _data;
    }

    public void updateData(List<Tasks> _data){
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
            view = View.inflate(_viewGroup.getContext(), R.layout.layout_adapter_task, null);
            TextView textView = (TextView) view.findViewById(R.id.textViewTaskName) ;
            ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButton) ;
            ViewHolder holder = new ViewHolder(textView, imageButton);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        Tasks tasks = (Tasks) getItem(_i);
        holder.textView.setText(tasks.getName());
        holder.imageButton.setTag(_i);
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemActivity.class);
                intent.putExtra(COLUMN_TASK_ID, tasks.getId());
                intent.putExtra(COLUMN_TASK_NAME, tasks.getName());
                context.startActivity(intent);
            }
        });
        return view;
    }

    private static class ViewHolder{
        final TextView textView;
        final ImageButton imageButton;
        ViewHolder(TextView _textView, ImageButton _imageButton){
            textView = _textView;
            imageButton = _imageButton;
        }
    }
}
