package com.example.myapplication.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapter.CategoryRecyclerAdapter;
import com.example.myapplication.Adapter.IncomeExpenseAdapter;
import com.example.myapplication.DAO.CategoryDAO;
import com.example.myapplication.DAO.IncomeExpenseDAO;
import com.example.myapplication.Models.Category;
import com.example.myapplication.Models.IncomeExpense;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FifthFragment extends Fragment {

    private List<IncomeExpense> data;
    private RecyclerView recyclerView;
    private IncomeExpenseAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("key",
                FifthFragment.this, new AddIncomeExpenseDialogFragment(){
                    @Override
                    public void onFragmentResult(String requestKey, Bundle result) {
                        super.onFragmentResult(requestKey, result);
                        List<IncomeExpense> _data = (List<IncomeExpense>)
                                (new IncomeExpenseDAO(getContext())).get(Constants.INCOME);
                        adapter.updateData(_data);
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fifth_fragment,
                container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabAddIncome) ;

        data = (List<IncomeExpense>) (new IncomeExpenseDAO(getContext())).get(Constants.INCOME);
        adapter = new IncomeExpenseAdapter(data, getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.myRecyclerFifthFragment);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);

        // click 1 item, hien dialog update
        // recyclerView

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();

                // instance for add new
                AddIncomeExpenseDialogFragment dialogFragment = AddIncomeExpenseDialogFragment
                        .newInstance(-1, Constants.INCOME);
                dialogFragment.show(fragmentManager, "");
            }
        });

    }

}
