package com.example.myapplication.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Adapter.CategoryAdapter;
import com.example.myapplication.Models.Product;
import com.example.myapplication.Models.ProductCategory;
import com.example.myapplication.Models.Response2PikModel;
import com.example.myapplication.Models.ResponseModel;
import com.example.myapplication.MyRetrofit.IRetrofitService;
import com.example.myapplication.MyRetrofit.RetrofitBuilder;
import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ProductFormActivity extends AppCompatActivity {

    private EditText editTextProductName, editTextProductPrice,
            editTextProductQuantity;
    private Spinner spinnerCategories;
    private TextView textViewTakePhoto;
    private ImageView imageViewProduct;
    private Button buttonCancel, buttonSave;

    private static String BASE_URL = "http://10.0.2.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";

    private List<ProductCategory> data;
    private CategoryAdapter adapter;


    private String image_url = null;
    private Integer category_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        initActivity();

        IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
        service.productCategoryGetAll().enqueue(getAllProductCategoryCB);

        textViewTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 1);
            }
        });

        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ProductCategory productCategory = (ProductCategory) adapterView.getItemAtPosition(i);
                category_id = productCategory.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product p = new Product();
                p.setCategory_id(category_id);
                p.setImage_url(image_url);
                p.setName(editTextProductName.getText().toString());
                p.setPrice(Double.parseDouble(editTextProductPrice.getText().toString()));
                p.setQuantity(Integer.parseInt(editTextProductQuantity.getText().toString()));
                IRetrofitService service1 = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
                service1.productInsert(p).enqueue(insertCB);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");

            // chuyen thanh base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            String encoded = Base64.encodeToString(bytes, Base64.DEFAULT);
            encoded = "data:image/png;base64," + encoded;

            MultipartBody.Part part = MultipartBody.Part.createFormData("image", encoded);
            IRetrofitService service1 = new RetrofitBuilder()
                    .createService(IRetrofitService.class, BASE_2PIK_URL);

            service1.upload(part).enqueue(uploadCB);

        }
    }

    Callback<ResponseModel> insertCB = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.isSuccessful()){
                ResponseModel model = response.body();
                if(model.getStatus()){
                    finish();
                } else {
                    Log.e(">>>>>insertCB getStatus failed", "insert failed");
                }
            } else{
                Log.e(">>>>>insertCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<ResponseModel> call, Throwable t) {
            Log.e(">>>>>insertCB onFailure", t.getMessage());
        }
    };

    Callback<Response2PikModel> uploadCB = new Callback<Response2PikModel>() {
        @Override
        public void onResponse(Call<Response2PikModel> call, Response<Response2PikModel> response) {
            if (response.isSuccessful()){
                Response2PikModel model = response.body();
                image_url = model.getSaved();
                Glide.with(ProductFormActivity.this)
                        .load(image_url)
                        .into(imageViewProduct);
            } else{
                Log.e(">>>>>uploadCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<Response2PikModel> call, Throwable t) {
            Log.e(">>>>>uploadCB onFailure", t.getMessage());
        }
    };

    Callback<List<ProductCategory>> getAllProductCategoryCB = new Callback<List<ProductCategory>>() {
        @Override
        public void onResponse(Call<List<ProductCategory>> call, Response<List<ProductCategory>> response) {
            if (response.isSuccessful()){
                data = response.body();
                adapter = new CategoryAdapter(data, ProductFormActivity.this);
                spinnerCategories.setAdapter(adapter);
                spinnerCategories.setSelection(category_id);
            } else {
                Log.e("getAllProductCategoryCB onResponse: ", response.message());
            }
        }

        @Override
        public void onFailure(Call<List<ProductCategory>> call, Throwable t) {
            Log.e("getAllProductCategoryCB onResponse: ", t.getMessage());
        }
    };

    private void initActivity(){
        editTextProductName = (EditText) findViewById(R.id.editTextProductName);
        editTextProductPrice = (EditText) findViewById(R.id.editTextProductPrice);
        editTextProductQuantity = (EditText) findViewById(R.id.editTextProductQuantity);
        spinnerCategories = (Spinner) findViewById(R.id.spinnerCategories);
        textViewTakePhoto = (TextView) findViewById(R.id.textViewTakePhoto);
        imageViewProduct = (ImageView) findViewById(R.id.imageViewProduct);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonSave = (Button) findViewById(R.id.buttonSave);
    }
}