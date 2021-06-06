package com.example.demoslide56768;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demoslide56768.DAO.LaptopDAO;
import com.example.demoslide56768.Model.Laptop;

public class LaptopDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_detail);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        Laptop laptop = (new LaptopDAO(this)).get(id);

        TextView textViewId = (TextView) findViewById(R.id.textViewId);
        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        EditText editTextPrice = (EditText) findViewById(R.id.editTextPrice);

        textViewId.setText(laptop.getLaptopId());
        editTextName.setText(laptop.getLaptopName());
        editTextPrice.setText(laptop.getLaptopPrice() + "");
    }
}