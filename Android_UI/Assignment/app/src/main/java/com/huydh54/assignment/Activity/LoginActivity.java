package com.huydh54.assignment.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    Toolbar tbLogin;
    TextView dangKy;
    String taiKhoan = "admin", matKhau = "admin";
    Button dangNhap;
    EditText formTaiKhoan, formMatKhau;
    TextInputLayout khungTaiKhoan, khungMatKhau;

    SignInButton googleButton;
    private GoogleApiClient googleApiClient;
    private static final int SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tbLogin = findViewById(R.id.tbLogin);
        dangKy = findViewById(R.id.txtDangKyDN);
        dangNhap = findViewById(R.id.btnLogin);
        formTaiKhoan = findViewById(R.id.txtTaiKhoan);
        formMatKhau = findViewById(R.id.txtMatKhau);
        khungTaiKhoan = findViewById(R.id.txtlTaiKhoan);
        khungMatKhau = findViewById(R.id.txtlMatKhau);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        googleButton = findViewById(R.id.btnGoogle);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN);
            }
        });

        setSupportActionBar(tbLogin);
        tbLogin.setNavigationIcon(R.drawable.ic_back_black);
        tbLogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(formTaiKhoan.getText().toString().length()==0){
                    khungTaiKhoan.setError("Bạn chưa nhập tài khoản!");
                }else if(formTaiKhoan.getText().toString().equals("admin")==false){
                    khungTaiKhoan.setError("Tài khoản không đúng!");
                }else{
                    khungTaiKhoan.setError("");
                }
                if(formMatKhau.getText().toString().length()==0){
                    khungMatKhau.setError("Bạn chưa nhập mật khẩu!");
                }else if(formMatKhau.getText().toString().equals("admin")==false){
                    khungMatKhau.setError("Mật khẩu không đúng!");
                }else{
                    khungMatKhau.setError("");
                }
                if(formTaiKhoan.getText().toString().equals("admin") &&
                        formMatKhau.getText().toString().equals("admin")) {
                    loadDangNhap();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            int statusCode = result.getStatus().getStatusCode();
            if(result.isSuccess()){
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }else{
//                Toast.makeText(DangNhap.this, statusCode + "", Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadDangNhap() {
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.DialogTheme);
        progressDialog.setTitle("Đăng nhập thành công!");
        progressDialog.setMessage("Đang tải dữ liệu...");
        progressDialog.show();
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }.start();
    }
}