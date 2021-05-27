package com.example.demo001;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static java.net.Proxy.Type.HTTP;

public class DemoIntentActivity extends AppCompatActivity {

    ImageView imageViewCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_intent);

        Button buttonView = (Button) findViewById(R.id.buttonView);
        imageViewCapture = (ImageView) findViewById(R.id.imageViewCapture);

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Uri webPage = Uri.parse("https://tuoitre.vn");
//                Intent intent = new Intent(Intent.ACTION_VIEW, webPage);

//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel: 0928372837"));


//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setData(Uri.parse("smsto: "));
//                intent.putExtra("sms_body", "Xin chao");

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.withAppendedPath())

                if (intent.resolveActivity(getPackageManager()) != null){
//                    startActivity(intent);
                    startActivityForResult(intent, 1);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap img = (Bitmap) extras.get("data");
            imageViewCapture.setImageBitmap(img);
        }
    }
}