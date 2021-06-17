package com.example.demoslide56768;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.demoslide56768.Adapter.BrandAdapter;
import com.example.demoslide56768.Adapter.LaptopAdapter;
import com.example.demoslide56768.DAO.BrandDAO;
import com.example.demoslide56768.DAO.LaptopDAO;
import com.example.demoslide56768.Model.Brand;
import com.example.demoslide56768.Model.Laptop;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    LaptopAdapter adapter;
    List<Laptop> list;
    // dialog
    String selectedBrandId = null;
    EditText id, name, price;
    Spinner spinner;
    Button takePhoto, save;
    ImageView imageView;
    Bitmap bitmapImg;

    Laptop selectedLaptop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ListView listView = (ListView) findViewById(R.id.listViewLaptop);
        list = (new LaptopDAO(this)).get();
        adapter = new LaptopAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Laptop laptop = (Laptop) adapter.getItem(i);
                Intent intent = new Intent(WelcomeActivity.this, LaptopDetailActivity.class);
                intent.putExtra("id", laptop.getLaptopId());
                startActivityForResult(intent, 1);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedLaptop = (Laptop) adapter.getItem(i);
                showConfirmDialog();
                return false;
            }
        });

        Button button = (Button) findViewById(R.id.buttonAddNew);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(WelcomeActivity.this, AddNewActivity.class);
//                startActivityForResult(intent, 1);
                openAddNewDialog();
            }
        });

        Button buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // luu trang thai logout
                SharedPreferences.Editor editor =
                        getSharedPreferences("login_status",MODE_PRIVATE).edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();

                // chuyen ve man hinh login
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            finish();
            startActivity(getIntent());
        } else if (requestCode == 1000 && resultCode == RESULT_OK){
            // cach 1: chup hinh
             Bundle bundle = data.getExtras();
             bitmapImg = (Bitmap) bundle.get("data");
             imageView.setImageBitmap(bitmapImg);
//            finish();
//            startActivity(getIntent());
        }
    }

    private void openAddNewDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View viewAddNew = LayoutInflater.from(this).inflate(R.layout.layout_add_new_laptop, null);
        builder.setView(viewAddNew);

        id = (EditText) viewAddNew.findViewById(R.id.dialog_laptop_id);
        name = (EditText) viewAddNew.findViewById(R.id.dialog_laptop_name);
        price = (EditText) viewAddNew.findViewById(R.id.dialog_laptop_price);
        spinner = (Spinner) viewAddNew.findViewById(R.id.dialog_spinner);
        takePhoto = (Button)  viewAddNew.findViewById(R.id.dialog_laptop_btn_img);
        save = (Button)  viewAddNew.findViewById(R.id.dialog_laptop_btn_save);
        imageView = (ImageView)  viewAddNew.findViewById(R.id.dialog_laptop_image);

        List<Brand> brands = (new BrandDAO(this)).get();
        BrandAdapter adapter = new BrandAdapter(this, brands);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBrandId = ((Brand)adapter.getItem(i)).getBrandId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedBrandId = null;
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 cach 1: chup anh
                 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 startActivityForResult(intent, 1000);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Laptop laptop = readFormData();
                if(laptop != null){
                    LaptopDAO dao = new LaptopDAO(WelcomeActivity.this);
                    dao.insert(laptop);
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        builder.create().show();
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

    private void showConfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cảnh báo");
        builder.setMessage("Bạn có chắc muốn xóa??");
        builder.setCancelable(true);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LaptopDAO dao = new LaptopDAO(WelcomeActivity.this);
                dao.delete(selectedLaptop.getLaptopId());
                finish();
                startActivity(getIntent());
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private byte[] bitmapToByteArray(Bitmap bitmap){
        if (bitmap == null){
            return null;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
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