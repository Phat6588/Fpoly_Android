package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapter.CategoryRecyclerAdapter;
import com.example.myapplication.DAO.CategoryDAO;
import com.example.myapplication.Models.Category;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FourthFragment extends Fragment  {

    private List<Category> data;
    private RecyclerView recyclerView;
    private CategoryRecyclerAdapter recyclerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("requestKey",
                FourthFragment.this, new AddCategoryDialogFragment() {
                    @Override
                    public void onFragmentResult(String requestKey, Bundle bundle) {
                        List<Category> _data = (List<Category>) (new CategoryDAO(getContext())).get();
                        recyclerAdapter.updateData(_data);
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fourth_fragment,
                container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabAddCategory) ;
        data = (List<Category>) (new CategoryDAO(getContext())).get();

        recyclerAdapter = new CategoryRecyclerAdapter(data);
        recyclerView = (RecyclerView) view.findViewById(R.id.myRecyclerFourthFragment);
        recyclerView.setAdapter(recyclerAdapter);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                AddCategoryDialogFragment dialogFragment = AddCategoryDialogFragment
                        .newInstance("Add Category Name");
                dialogFragment.show(fragmentManager, "");
            }
        });
    }
}
