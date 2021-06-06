package com.huydh54.fpolyapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.huydh54.fpolyapp.CongCu.InternetConnection;
import com.huydh54.fpolyapp.R;

public class TrinhDuyetActivity extends AppCompatActivity {
    private static final String[] LISTWEB = new String[] {
        "google.com", "youtube.com", "facebook.com", "google.com.vn", "vnexpress.net", "thethao247.vn", "drive.google.com",
            "zing.vn", "shopee.vn", "24h.com.vn", "vtv.vn", "kenh14.vn", "tiki.vn", "phimmoi.net", "bongdapm.com", "docs.google.com",
            "dantri.com.vn", "wikipedia.org", "zalo.me", "lazada.vn", "yahoo.com", "stackoverflow.com", "vietnamnet.vn",
            "baomoi.com", "xemphimplus.net", "sendo.vn", "bomboxtv.com", "cafef.vn", "amazon.com", "doctruyen3s.com", "caodang.fpt.edu.vn",
            "omtivi.com", "xemphim3s.net", "office.com", "cungcau.vn", "nhaccuatui.com", "nettruyen.com", "mail.google.com"
    };
    WebView webView;
    AutoCompleteTextView linkWed;
    ImageView load, back, next, reload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trinh_duyet);

        webView = findViewById(R.id.wvHienThi);
        linkWed = findViewById(R.id.edtWebLink);
        load = findViewById(R.id.imgMoWeb);
        back = findViewById(R.id.imgWVBack);
        next = findViewById(R.id.imgWVNext);
        reload = findViewById(R.id.imgWVLoad);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TrinhDuyetActivity.this, android.R.layout.simple_list_item_1, LISTWEB);
        linkWed.setAdapter(adapter);
        setting();
        go("caodang.fpt.edu.vn");

        linkWed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = linkWed.getText().toString();
                go(url);
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = linkWed.getText().toString();
                go(url);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(webView.canGoBack()){
                    webView.goBack();
                    linkWed.setText(webView.getOriginalUrl());
                    Toast.makeText(TrinhDuyetActivity.this, "Trang trước!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TrinhDuyetActivity.this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.reload();
                linkWed.setText(webView.getUrl());
                webView.setWebViewClient(new MyWebViewClient());
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(webView.canGoForward()){
                    webView.goForward();
                    linkWed.setText(webView.getOriginalUrl());
                    Toast.makeText(TrinhDuyetActivity.this, "Trang sau!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TrinhDuyetActivity.this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setting() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
    }

    private void go(String url) {
        if (InternetConnection.checkConnection(TrinhDuyetActivity.this)){
            Toast.makeText(TrinhDuyetActivity.this, "Có Internet!", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(TrinhDuyetActivity.this, "Không có Internet!", Toast.LENGTH_SHORT).show();
        }
        webView.loadUrl(url);
        linkWed.setText(webView.getUrl());
        webView.setWebViewClient(new MyWebViewClient());
    }
}

class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.getUrl();
        return false;
    }
}