package com.example.demoslide56768;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.demoslide56768.Adapter.BrandAdapter;
import com.example.demoslide56768.DAO.BrandDAO;
import com.example.demoslide56768.DAO.LaptopDAO;
import com.example.demoslide56768.Model.Brand;
import com.example.demoslide56768.Model.Laptop;

import java.util.List;

public class LaptopDetailActivity extends AppCompatActivity {

    Spinner spinnerBrandId;
    String selectedBrandId = null;


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
        ImageView imageView = (ImageView) findViewById(R.id.imageViewLaptopDetail);

        textViewId.setText(laptop.getLaptopId());
        editTextName.setText(laptop.getLaptopName());
        editTextPrice.setText(laptop.getLaptopPrice() + "");

        // hinh anh
        byte[] imageArray = laptop.getLaptopImage();
        if (imageArray == null){
            imageView.setImageResource(R.drawable.ic_launcher_background);
        }
        else {
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageArray, 0,
                    imageArray.length));
        }

        List<Brand> brands = (new BrandDAO(this)).get();
        BrandAdapter adapter = new BrandAdapter(this, brands);
        spinnerBrandId = (Spinner) findViewById(R.id.spinnerLaptopDetail);
        spinnerBrandId.setAdapter(adapter);

        spinnerBrandId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBrandId = ((Brand)adapter.getItem(i)).getBrandId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedBrandId = null;
            }
        });

        spinnerBrandId.setSelection(adapter.getPositon(laptop.getBrandId()));

        Button buttonXoa = (Button) findViewById(R.id.buttonXoa);
        buttonXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LaptopDAO dao = new LaptopDAO(LaptopDetailActivity.this);
                dao.delete(laptop.getLaptopId());
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        Button buttonCapNhat = (Button) findViewById(R.id.buttonCapNhat);
        buttonCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name = editTextName.getText().toString();
                Double _price = Double.parseDouble(editTextPrice.getText().toString());

                LaptopDAO dao = new LaptopDAO(LaptopDetailActivity.this);
                laptop.setLaptopName(_name);
                laptop.setLaptopPrice(_price);
                laptop.setBrandId(selectedBrandId);
                dao.update(laptop);

                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }
}