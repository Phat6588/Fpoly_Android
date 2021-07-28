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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.myapplication.Adapter.ProductAdapter;
import com.example.myapplication.Models.Product;
import com.example.myapplication.Models.Response2PikModel;
import com.example.myapplication.MyRetrofit.IRetrofitService;
import com.example.myapplication.MyRetrofit.RetrofitBuilder;
import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private ListView listViewProducts;
    private ImageView imageView2Pik;
    private Button buttonAddNew;

    private List<Product>  data = new ArrayList<>();
    private ProductAdapter adapter;

    private static String BASE_URL = "http://10.0.2.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        listViewProducts = (ListView) findViewById(R.id.listViewProducts);
        buttonAddNew = (Button) findViewById(R.id.buttonAddNew);

        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);

        service.productGetAll().enqueue(getAllCB);

        buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ProductFormActivity.class));
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume: ", "onResume>>>>");
        IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
        service.productGetAll().enqueue(getAllCB);
    }

    Callback<Response2PikModel> uploadCB = new Callback<Response2PikModel>() {
        @Override
        public void onResponse(Call<Response2PikModel> call, Response<Response2PikModel> response) {
            if (response.isSuccessful()){
                Response2PikModel model = response.body();
                Log.e(">>>>>uploadCB getSaved", model.getSaved());
            } else{
                Log.e(">>>>>uploadCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<Response2PikModel> call, Throwable t) {
            Log.e(">>>>>uploadCB onFailure", t.getMessage());
        }
    };

    Callback<List<Product>> getAllCB = new Callback<List<Product>>() {
        @Override
        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
            if (response.isSuccessful()){
                if (data.size() == 0){
                    data = response.body();
                    adapter = new ProductAdapter(data, getBaseContext());
                    listViewProducts.setAdapter(adapter);
                } else {
                    data.clear();
                    data.addAll(response.body());
                    listViewProducts.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            } else {
                Log.e(">>>>>getAllCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<List<Product>> call, Throwable t) {
            Log.e(">>>>>getAllCB onFailure", t.getMessage());
        }
    };
}