package com.example.assignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.Model.User;
import com.example.assignment.SQLite.LopDAO;
import com.example.assignment.SQLite.UserDAO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    UserDAO userDAO;
    ArrayList<User> dsUser=new ArrayList<>();
    EditText etuser,etpassword;
    Button login,dangky;
    CheckBox remember;
    EditText dangkytaikhoan,dangkymatkhau,xacnhanmatkhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etuser=findViewById(R.id.etUser);
        etpassword=findViewById(R.id.etPassword);
        remember=findViewById(R.id.checkRemember);
        login=findViewById(R.id.btnLogin);
        dangky=findViewById(R.id.btnDangKy);
        userDAO=new UserDAO(MainActivity.this);
        dsUser=userDAO.getAllUser();
        restore();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(etuser.getText().toString().equals("")||etpassword.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this,"Không được để trống",Toast.LENGTH_SHORT).show();
                    }
                    else if(CheckTaiKhoan(etuser.getText().toString(),etpassword.getText().toString())){
                        Toast.makeText(MainActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        save();
                        Intent intent=new Intent(MainActivity.this,QuanLyUserActivity.class);
                        intent.putExtra("xinchao",etuser.getText().toString());
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Tài khoản hoặc mật khẩu chưa chính xác",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception ex){
                    Toast.makeText(MainActivity.this,"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
                }

            }
        });
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_dang_ky_user,null);
                builder.setView(view);
                dangkytaikhoan=view.findViewById(R.id.etDangKyTaiKhoan);
                dangkymatkhau=view.findViewById(R.id.etDangKyMatKhau);
                xacnhanmatkhau=view.findViewById(R.id.etXacNhanMatKhau);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       try{
                           if(dangkytaikhoan.getText().toString().equals("")||dangkymatkhau.getText().toString().equals("")){
                               Toast.makeText(MainActivity.this,"Không được để trống",Toast.LENGTH_SHORT).show();
                           }
                           else if(xacnhanmatkhau.getText().toString().equalsIgnoreCase(dangkymatkhau.getText().toString())==false){
                               Toast.makeText(MainActivity.this,"Xác nhận mật khẩu chưa chính xác",Toast.LENGTH_SHORT).show();
                           }
                           else if(CheckTaiKhoan(dangkytaikhoan.getText().toString())){
                               Toast.makeText(MainActivity.this,"Đã có tài khoản này",Toast.LENGTH_SHORT).show();
                           }
                           else {
                               userDAO=new UserDAO(MainActivity.this);
                               userDAO.ThemUser(dangkytaikhoan.getText().toString(),dangkymatkhau.getText().toString());
                               dsUser=userDAO.getAllUser();
                               Toast.makeText(MainActivity.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                           }
                       }catch (Exception ex){
                           Toast.makeText(MainActivity.this,"Đăng ký thất bại",Toast.LENGTH_SHORT).show();
                       }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
            }
        });
    }
    public void save(){
        SharedPreferences sharedPreferences=getSharedPreferences("user.txt",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String user=etuser.getText().toString();
        String password=etpassword.getText().toString();
        boolean check=remember.isChecked();
        if(check){
            editor.putString("user",user);
            editor.putString("password",password);
            editor.putBoolean("remember",check);
        }
        else
            editor.clear();
        editor.commit();
    }
    public void restore(){
        SharedPreferences sharedPreferences=getSharedPreferences("user.txt",MODE_PRIVATE);
        boolean check=sharedPreferences.getBoolean("remember",false);
        if(check){
            etuser.setText(sharedPreferences.getString("user",""));
            etpassword.setText(sharedPreferences.getString("password",""));
        }
        remember.setChecked(check);
    }
    public boolean CheckTaiKhoan(String taikhoan){
        boolean check=false;
        for(int i=0;i<dsUser.size();i++){
            if(taikhoan.equals(dsUser.get(i).getTaikhoan())){
                check=true;
                break;
            }
        }
        return  check;
    }
    public boolean CheckTaiKhoan(String taikhoan,String matkhau){
        boolean check=false;
        for(int i=0;i<dsUser.size();i++){
            if(taikhoan.equals(dsUser.get(i).getTaikhoan())&&matkhau.equalsIgnoreCase(dsUser.get(i).getMatkhau())){
                check=true;
                break;
            }
        }
        return  check;
    }
}