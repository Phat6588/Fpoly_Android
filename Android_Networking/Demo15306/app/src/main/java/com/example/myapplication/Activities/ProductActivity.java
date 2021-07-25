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
    private Button buttonCapture;

    private static String BASE_URL = "http://10.0.2.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        imageViewCapture = (ImageView) findViewById(R.id.imageViewCapture) ;
        buttonCapture = (Button) findViewById(R.id.buttonCapture);


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
//            Log.i(">>>>>>>>>>", encoded);
            // encoded = "data:image/jpeg;base64,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";
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", encoded);
            IRetrofitService service = new RetrofitBuilder()
                    .createService(IRetrofitService.class, BASE_2PIK_URL);
            service.upload2pik(part).enqueue(uploadCB);
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