package com.example.demoslide56768;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.demoslide56768.Adapter.BrandAdapter;
import com.example.demoslide56768.DAO.BrandDAO;
import com.example.demoslide56768.DAO.LaptopDAO;
import com.example.demoslide56768.Model.Brand;
import com.example.demoslide56768.Model.Laptop;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class AddNewActivity extends AppCompatActivity {

    EditText id, name, price;
    ImageView imageView;
    Button capture;
    Button button;
    Bitmap bitmapImg;

    Spinner spinnerBrandId;
    String selectedBrandId = null;

//    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        id = (EditText) findViewById(R.id.editTextLaptopId);
        name = (EditText) findViewById(R.id.editTextLaptopName);
        price = (EditText) findViewById(R.id.editTextLaptopPrice);
        imageView = (ImageView) findViewById(R.id.imageViewLaptop2);
        capture = (Button) findViewById(R.id.buttonChupAnh);
        button = (Button) findViewById(R.id.buttonLuu);

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cach 1: chup anh
                // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // startActivityForResult(intent, 1);
                // cach 2: chon anh trong thu vien
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose picture"), 1);
            }
        });

        List<Brand> brands = (new BrandDAO(this)).get();
        BrandAdapter adapter = new BrandAdapter(this, brands);
        spinnerBrandId = (Spinner) findViewById(R.id.dialog_spinner);
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // validation
                Laptop laptop = readFormData();
                if(laptop != null){
                    LaptopDAO dao = new LaptopDAO(AddNewActivity.this);
                    dao.insert(laptop);
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            // cach 1: chup hinh
            // Bundle bundle = data.getExtras();
            // bitmapImg = (Bitmap) bundle.get("data");
            // imageView.setImageBitmap(bitmapImg);
            // cach 2: lay trong thu vien
            try {
                Uri uri = data.getData();
                bitmapImg = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmapImg);
            }
            catch(Exception e){

            }
        }
    }

    private byte[] bitmapToByteArray(Bitmap bitmap){
        if (bitmap == null){
            return null;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    private Laptop readFormData(){
        String _id = id.getText().toString();
        String _name = name.getText().toString();
        String _price = price.getText().toString();
        String _brand = selectedBrandId;
        byte[] _img = bitmapToByteArray(bitmapImg);

        // id = Laptop1234,
        String patternId = "Laptop\\d{4}";
        String patternName = "[a-zA-Z0-9]{3,}";
        String patternPrice = "\\d{3,}";
        String patternDate = "0[1-9]|[1-2][0-9]|3[0-1]-0[1-9]|1[0-2]-\\d{4}";

        boolean checkId = _id.matches(patternId);
        boolean checkName = _name.matches(patternName);
        boolean checkPrice = _price.matches(patternPrice);
        if(!checkId || !checkName || !checkPrice){
            showDialog();
            return null;
        }
        Double price = Double.parseDouble(_price);
        Laptop laptop = new Laptop(_id, _name, price, _brand, _img);
        return laptop;
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cảnh báo");
        builder.setMessage("Du lieu khong chinh xac");
        builder.setCancelable(true);
        builder.setPositiveButton("Hieu roi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Dong y", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
//        builder.setView()
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}