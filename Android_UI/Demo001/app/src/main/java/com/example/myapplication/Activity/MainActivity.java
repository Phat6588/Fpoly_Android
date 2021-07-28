package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.Adapter.CategoryAdapter;
import com.example.myapplication.Adapter.CategoryRecyclerAdapter;
import com.example.myapplication.DAO.CategoryDAO;
import com.example.myapplication.Models.Category;
import com.example.myapplication.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private CategoryAdapter adapter;
    private List<Category> data;


    private RecyclerView recyclerView;
    private CategoryRecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler);

//        listView = (ListView) findViewById(R.id.listView);
        data = (new CategoryDAO(this)).get();

//        adapter = new CategoryAdapter(this, data);
//        listView.setAdapter(adapter);

//        recyclerAdapter = new CategoryRecyclerAdapter(data);
        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setAdapter(recyclerAdapter);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,false);

//        StaggeredGridLayoutManager gridLayoutManager =
//                new StaggeredGridLayoutManager(3,
//                        StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

//        SnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                if (Math.abs(velocityY) > 1000){
                    if(velocityY < 0){
                        // down
                        Log.i("MyTag>>>>", "down");
                        data.remove(9);
                        recyclerAdapter.notifyItemRemoved(9);
                    }
                    else {
                        // up
                        Log.i("MyTag>>>>", "up");
                    }
                }
                return false;
            }
        });
    }
}



    // Category: Hochanh, GiaDinh, AnUong.......
    // IncomeExpense:
    // User