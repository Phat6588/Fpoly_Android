package com.example.demoslide56768;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demoslide56768.DAO.LaptopDAO;
import com.example.demoslide56768.Model.Laptop;

import java.io.ByteArrayOutputStream;

public class AddNewLaptopActivity extends AppCompatActivity {
    EditText id ;
    EditText name ;
    EditText price ;
    EditText brand ;
    ImageView image ;
    Button buttonChupAnh ;
    Button button ;
    Bitmap img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_laptop);

         id = (EditText) findViewById(R.id.txtLaptopId);
         name = (EditText) findViewById(R.id.txtLaptopName);
         price = (EditText) findViewById(R.id.txtLaptopPrice);
         brand = (EditText) findViewById(R.id.txtLaptopBrand);
         image = (ImageView) findViewById(R.id.imageViewSanPham);
        buttonChupAnh = (Button) findViewById(R.id.buttonChupAnh);
         button = (Button) findViewById(R.id.buttonSave);

        buttonChupAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null){
                    try {
                        startActivityForResult(intent, 1);
                    } catch (ActivityNotFoundException e) {
                        Log.i("MyTag", e.getMessage());
                    }
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _id = id.getText().toString();
                String _name = name.getText().toString();
                Double _price = Double.parseDouble(price.getText().toString());
                String _brand = brand.getText().toString();
                byte[] _img = getBitmapAsByteArray(img);
                Laptop laptop = new Laptop(_id, _name, _price, _img, _brand);
                (new LaptopDAO(AddNewLaptopActivity.this)).insert(laptop);
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK);
                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            img = (Bitmap) extras.get("data");
            image.setImageBitmap(img);
        }
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        if (bitmap == null){
            return null;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}