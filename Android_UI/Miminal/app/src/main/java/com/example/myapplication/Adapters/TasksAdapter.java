package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.Activities.ItemActivity;
import com.example.myapplication.Models.TodoTask;
import com.example.myapplication.R;

import java.util.List;

public class TasksAdapter extends BaseAdapter {

    private Context context;
    private List<TodoTask> data;

    public TasksAdapter(Context _context, List<TodoTask> _data) {
        context = _context;
        data = _data;
    }

    public void updateData(List<TodoTask> _data) {
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
            view = View.inflate(_viewGroup.getContext(), R.layout.layout_each_task, null);
            ViewHolder holder = createViewHolderFrom(view);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        TodoTask task = (TodoTask) getItem(_i);
        holder.text.setText(task.getName());
        holder.button.setTag(_i);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goDetail(task);
            }
        });
        return view;

    }

    private void goDetail(TodoTask task) {
        Intent intent = new Intent(context, ItemActivity.class);
        intent.putExtra("id", task.getId());
        intent.putExtra("name", task.getName());
        context.startActivity(intent);
    }

    private ViewHolder createViewHolderFrom(View view) {
        TextView text = (TextView) view.findViewById(R.id.taskName);
        ImageButton button = (ImageButton) view.findViewById(R.id.imageButton);
        return new ViewHolder(text, button);
    }

    private static class ViewHolder {
        final TextView text;
        final ImageButton button;
        ViewHolder(TextView text, ImageButton button) {
            this.text = text;
            this.button = button;
        }
    }
}
