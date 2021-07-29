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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Adapter.CategoryAdapter;
import com.example.myapplication.Model.Product;
import com.example.myapplication.Model.ProductCategory;
import com.example.myapplication.Model.Response2PikModel;
import com.example.myapplication.Model.ResponseModel;
import com.example.myapplication.MyRetrofit.IRetrofitService;
import com.example.myapplication.MyRetrofit.RetrofitBuilder;
import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ProductFormActivity extends AppCompatActivity {

    private EditText editTextProductName, editTextProductPrice;
    private Spinner spinnerCategoryId;
    private TextView textViewTakePhoto;
    private ImageView imageViewProductImage;
    private Button buttonSave, buttonCancel;

    private static String BASE_URL = "http://10.0.2.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";

    private List<ProductCategory> data;
    private CategoryAdapter adapter;

    private String image_url = null;
    private Integer category_id = -1;
    private Integer productId = -1;

    private Product model = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        initView();

        productId = getIntent().getIntExtra("id", -1);

        IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
        service.getAllCategories().enqueue(getAllCategoriesCB);

        textViewTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
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
                IRetrofitService service1 = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
                Product p = new Product();
                p.setImage_url(image_url);
                p.setProduct_name(editTextProductName.getText().toString());
                p.setPrice(Double.parseDouble(editTextProductPrice.getText().toString()));
                p.setCategory_id(category_id);
                p.setId(productId);
                if (productId == -1){
                    service1.insert(p).enqueue(insert_update_CB);
                }
                else {
                    service1.update(p).enqueue(insert_update_CB);
                }
            }
        });

        spinnerCategoryId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ProductCategory productCategory = (ProductCategory)
                                                adapterView.getItemAtPosition(i);
                category_id = productCategory.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            encoded = "data:image/png;base64," + encoded;
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", encoded);
            IRetrofitService service = new RetrofitBuilder()
                    .createService(IRetrofitService.class, BASE_2PIK_URL);
            service.upload2pik(part).enqueue(uploadCB);
        }
    }

    Callback<Product> getByIdCB = new Callback<Product>() {
        @Override
        public void onResponse(Call<Product> call, Response<Product> response) {
            if (response.isSuccessful()){
                model = response.body();
                editTextProductName.setText(model.getProduct_name());
                editTextProductPrice.setText(model.getPrice()+"");
                int index = getIndex(data, model.getCategory_id());
                spinnerCategoryId.setSelection(index);
                image_url = model.getImage_url();
                Glide.with(ProductFormActivity.this)
                        .load(model.getImage_url())
                        .into(imageViewProductImage);
            } else {
                Log.e("getByIdCB onResponse>>>>", response.message());
            }
        }

        @Override
        public void onFailure(Call<Product> call, Throwable t) {
            Log.e("getByIdCB onFailure>>>>", t.getMessage());
        }
    };

    Callback<ResponseModel> insert_update_CB = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.isSuccessful()){
                ResponseModel model = response.body();
                if (model.getStatus()){
                    finish();
                } else {
                    Toast.makeText(ProductFormActivity.this, "Insert failed",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Log.e("insertCB onResponse>>>>", response.message());
            }
        }

        @Override
        public void onFailure(Call<ResponseModel> call, Throwable t) {
            Log.e("insertCB onFailure>>>>", t.getMessage());
        }
    };

    Callback<Response2PikModel> uploadCB = new Callback<Response2PikModel>() {
        @Override
        public void onResponse(Call<Response2PikModel> call, Response<Response2PikModel> response) {
            if (response.isSuccessful()){
                Response2PikModel model = response.body();
                image_url = model.getSaved();
                Log.i("image url : ", image_url);
                Glide.with(ProductFormActivity.this)
                        .load(image_url)
                        .into(imageViewProductImage);
            } else {
                Log.i("uploadCB Error: ", response.message());
            }
        }

        @Override
        public void onFailure(Call<Response2PikModel> call, Throwable t) {
            Log.i("onFailure uploadCB: ", t.getMessage());
        }
    };

    Callback<List<ProductCategory>> getAllCategoriesCB = new Callback<List<ProductCategory>>() {
        @Override
        public void onResponse(Call<List<ProductCategory>> call, Response<List<ProductCategory>> response) {
            if (response.isSuccessful()){
                data = response.body();
                adapter = new CategoryAdapter(data, ProductFormActivity.this);
                spinnerCategoryId.setAdapter(adapter);
                spinnerCategoryId.setSelection(0);
                if (productId != -1){
                    IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
                    service.getProductById(new Product(productId, -1, -1.0, null, null))
                            .enqueue(getByIdCB);
                }
            } else {
                Log.e("getAllCategoriesCB onResponse>>>>", response.message());
            }
        }

        @Override
        public void onFailure(Call<List<ProductCategory>> call, Throwable t) {
            Log.e("getAllCategoriesCB onFailure>>>>", t.getMessage());
        }
    };

    private Integer getIndex(List<ProductCategory> _data, int category_id){
        for (int i = 0; i< _data.size(); i++) {
            if (_data.get(i).getId() == category_id){
                return i;
            }
        }
        return 0;
    }

    private void initView(){
        editTextProductName = (EditText) findViewById(R.id.editTextProductName);
        editTextProductPrice = (EditText) findViewById(R.id.editTextProductPrice);
        spinnerCategoryId = (Spinner) findViewById(R.id.spinnerCategoryId);
        textViewTakePhoto = (TextView) findViewById(R.id.textViewTakePhoto);
        imageViewProductImage = (ImageView) findViewById(R.id.imageViewProductImage);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonSave = (Button) findViewById(R.id.buttonSave);
    }
}