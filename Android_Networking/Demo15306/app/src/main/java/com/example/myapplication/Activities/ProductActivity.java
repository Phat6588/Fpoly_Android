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
import com.example.myapplication.Model.Product;
import com.example.myapplication.Model.Response2PikModel;
import com.example.myapplication.MyRetrofit.IRetrofitService;
import com.example.myapplication.MyRetrofit.RetrofitBuilder;
import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private ListView listView;
    private List<Product> data;
    private ProductAdapter adapter;

    private ImageView imageViewCapture;
    private Button buttonCapture, buttonProductForm;

    private static String BASE_URL = "http://10.0.2.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        imageViewCapture = (ImageView) findViewById(R.id.imageViewCapture) ;
        buttonCapture = (Button) findViewById(R.id.buttonCapture);
        buttonProductForm = (Button) findViewById(R.id.buttonProductForm);


        IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
        listView = (ListView) findViewById(R.id.listViewProduct);

        service.getAllProduct().enqueue(getAllCB);

        buttonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });

        buttonProductForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(ProductActivity.this, ProductFormActivity.class), 2);
            }
        });
    }

    Callback<List<Product>> getAllCB = new Callback<List<Product>>() {
        @Override
        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
            if (response.isSuccessful()){
                data = response.body();
                adapter = new ProductAdapter(data, ProductActivity.this);
                listView.setAdapter(adapter);
            } else {
                Log.i("Error: ", response.message());
            }
        }

        @Override
        public void onFailure(Call<List<Product>> call, Throwable t) {
            Log.i("Error: ", call.toString());
        }
    };


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
        } else if (requestCode == 2 && resultCode == RESULT_OK){
//            IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
//            service.getAllProduct().enqueue(getAllCB);
        }
    }



    Callback<Response2PikModel> uploadCB = new Callback<Response2PikModel>() {
        @Override
        public void onResponse(Call<Response2PikModel> call, Response<Response2PikModel> response) {
            if (response.isSuccessful()){
                Response2PikModel model = response.body();
                Log.e("Saved>>>>", model.getSaved());
            } else {
                Log.i("Error: ", response.message());
            }
        }

        @Override
        public void onFailure(Call<Response2PikModel> call, Throwable t) {

        }
    };
}