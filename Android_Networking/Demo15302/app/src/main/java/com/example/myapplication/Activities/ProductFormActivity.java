package com.example.myapplication.Activities;

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
    private Integer product_id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        initActivity();

        product_id = getIntent().getIntExtra("id", -1);

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
                // validation
                p.setImage_url(image_url);
                p.setName(editTextProductName.getText().toString());
                p.setPrice(Double.parseDouble(editTextProductPrice.getText().toString()));
                p.setQuantity(Integer.parseInt(editTextProductQuantity.getText().toString()));
                p.setId(product_id);
                IRetrofitService service1 = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
                if (product_id == -1){
                    service1.productInsert(p).enqueue(insert_update_CB);
                } else {
                    service1.productUpdate(p).enqueue(insert_update_CB);
                }
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

    Callback<Product> getByIdCB = new Callback<Product>() {
        @Override
        public void onResponse(Call<Product> call, Response<Product> response) {
            if (response.isSuccessful()){
                Product p = response.body();
                editTextProductName.setText(p.getName());
                editTextProductPrice.setText(String.valueOf(p.getPrice()));
                editTextProductQuantity.setText(String.valueOf(p.getQuantity()));
                int index = getIndex(data, p.getCategory_id());
                spinnerCategories.setSelection(index);
                category_id = p.getCategory_id();
                image_url = p.getImage_url();
                Glide.with(ProductFormActivity.this)
                        .load(image_url)
                        .into(imageViewProduct);
            } else {
                Log.e("getByIdCB onResponse: ", response.message());
            }
        }

        @Override
        public void onFailure(Call<Product> call, Throwable t) {
            Log.e("getByIdCB onResponse: ", t.getMessage());
        }
    };

    Callback<ResponseModel> insert_update_CB = new Callback<ResponseModel>() {
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
                if (product_id != -1){
                    IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
                    service.productGetById(new Product(product_id, null, 0.0, 0, null, null))
                            .enqueue(getByIdCB);
                }
            } else {
                Log.e("getAllProductCategoryCB onResponse: ", response.message());
            }
        }

        @Override
        public void onFailure(Call<List<ProductCategory>> call, Throwable t) {
            Log.e("getAllProductCategoryCB onResponse: ", t.getMessage());
        }
    };

    private Integer getIndex (List<ProductCategory> list, int categoryId){
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getId() == categoryId){
                return i;
            }
        }
        return 0;
    }

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