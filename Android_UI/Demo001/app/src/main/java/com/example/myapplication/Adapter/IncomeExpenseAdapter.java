package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Models.Category;
import com.example.myapplication.Models.IncomeExpense;
import com.example.myapplication.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IncomeExpenseAdapter extends
        RecyclerView.Adapter<IncomeExpenseAdapter.ViewHolder>{

    private Context context;
    private List<IncomeExpense> data;
    public IncomeExpenseAdapter(List<IncomeExpense> _data, Context _context){
        context = _context;
        data = _data;
    }

    public void updateData (List<IncomeExpense> _data){
        data.clear();
        data.addAll(_data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_category_item, parent, false);
        ViewHolder holder = new ViewHolder(view, new ViewHolder.IMyViewHolderLongClicks(){
            @Override
            public void onItemLongClick(View caller) {

            }
        });
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(IncomeExpenseAdapter.ViewHolder holder, int position) {
        IncomeExpense model = data.get(position);
        holder.getTextViewName().setText(model.getName());
        holder.getTextViewDate().setText(model.getCreatedDate().toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnLongClickListener {

        public ViewHolder.IMyViewHolderLongClicks mListener;
        private final TextView textViewName, textViewDate;

        public ViewHolder(View itemView, ViewHolder.IMyViewHolderLongClicks _mListener) {
            super(itemView);
            mListener = _mListener;
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            itemView.setOnLongClickListener(this);
        }

        public TextView getTextViewName(){return textViewName;}
        public TextView getTextViewDate(){return textViewDate;}

        @Override
        public boolean onLongClick(View view) {
            mListener.onItemLongClick(view);
            return true;
        }

        public static interface IMyViewHolderLongClicks {
            public void onItemLongClick(View caller);
        }
    }
}
