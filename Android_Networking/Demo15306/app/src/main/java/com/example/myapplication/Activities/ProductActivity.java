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
import android.widget.ImageView;
import android.widget.ListView;

import com.example.myapplication.Adapter.ProductAdapter;
import com.example.myapplication.Model.Product;
import com.example.myapplication.Model.Response2PikModel;
import com.example.myapplication.MyRetrofit.IRetrofitService;
import com.example.myapplication.MyRetrofit.RetrofitBuilder;
import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private ListView listView;
    private List<Product> data = new ArrayList<>();
    private ProductAdapter adapter;

    private Button buttonProductForm;

    private static String BASE_URL = "http://10.0.2.2:8081/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        buttonProductForm = (Button) findViewById(R.id.buttonProductForm);


        IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
        listView = (ListView) findViewById(R.id.listViewProduct);

        service.getAllProduct().enqueue(getAllCB);

        buttonProductForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(ProductActivity.this, ProductFormActivity.class));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product p = (Product) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getBaseContext(), ProductFormActivity.class);
                intent.putExtra("id", p.getId());
                startActivity(intent);
            }
        });

        // laravel
        // socket
        // firebase
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
        service.getAllProduct().enqueue(getAllCB);
    }

    Callback<List<Product>> getAllCB = new Callback<List<Product>>() {
        @Override
        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
            if (response.isSuccessful()){
                if (data.size() == 0){
                    data = response.body();
                    adapter = new ProductAdapter(data, ProductActivity.this);
                    listView.setAdapter(adapter);
                } else {
                    data.clear();
                    data.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            } else {
                Log.i("Error: ", response.message());
            }
        }

        @Override
        public void onFailure(Call<List<Product>> call, Throwable t) {
            Log.i("Error: ", call.toString());
        }
    };

}