package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.Adapter.CategoryAdapter;
import com.example.myapplication.DAO.CategoryDAO;
import com.example.myapplication.Models.Category;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private CategoryAdapter adapter;
    private List<Category> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        data = (new CategoryDAO(this)).get();

        adapter = new CategoryAdapter(this, data);
        listView.setAdapter(adapter);



    }
}



    // Category: Hochanh, GiaDinh, AnUong.......
    // IncomeExpense:
    // User