package com.example.myapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.DAO.CategoryDAO;
import com.example.myapplication.Fragment.AddCategoryDialogFragment;
import com.example.myapplication.Models.Category;
import com.example.myapplication.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryRecyclerAdapter extends
        RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Category> data;
    public CategoryRecyclerAdapter(List<Category> _data, Context _context){
        context = _context;
        data = _data;
    }

    public void updateData (List<Category> _data){
        data.clear();
        data.addAll(_data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_category_item, parent, false);
        ViewHolder holder = new ViewHolder(view,
                new ViewHolder.IMyViewHolderLongClicks() {
                    @Override
                    public void onItemLongClick(View caller) {
                        PopupMenu menu = new PopupMenu(parent.getContext(), caller);
                        menu.inflate(R.menu.menu_popup);

                        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Category category = data.get(getItemViewType(viewType));
                                switch (item.getItemId()){
                                    case R.id.menu_edit:
                                        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                                        AddCategoryDialogFragment dialogFragment = AddCategoryDialogFragment
                                                .newInstance(category.getId(), category.getName());
                                        dialogFragment.show(fragmentManager, "");
                                        return true;
                                    case R.id.menu_delete:
                                        CategoryDAO dao = new CategoryDAO(context);
                                        dao.delete(category.getId());
                                        List<Category> _data = dao.get();
                                        updateData(_data);
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });


                        menu.show();
                        Log.e(">>>>>>onItemLongClick", "item long click");
                    }
                });

        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(CategoryRecyclerAdapter.ViewHolder holder, int position) {
        Category category = data.get(position);
        TextView textView = holder.getTextView();
        textView.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
        implements View.OnLongClickListener {

        public IMyViewHolderLongClicks mListener;

        private final TextView textView;
        public ViewHolder(View itemView, IMyViewHolderLongClicks _mListener) {
            super(itemView);
            mListener = _mListener;
            textView = (TextView) itemView.findViewById(R.id.textView2);
            itemView.setOnLongClickListener(this);
        }

        public TextView getTextView(){return textView;}

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
