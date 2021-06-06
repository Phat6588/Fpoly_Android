package com.example.assignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.Model.User;
import com.example.assignment.SQLite.UserDAO;

import java.util.ArrayList;

public class QuanLyUserActivity extends AppCompatActivity {
    TextView etTenUser;
    LinearLayout DoiMatKhau,QuanLyUser,Thoat,QuanLyLopHoc,QuanLySinhVien;
    ImageView DangXuat;
    UserDAO userDAO;
    ArrayList<User> dsUser=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_user);
        DangXuat=findViewById(R.id.DangXuat);
        DoiMatKhau=findViewById(R.id.DoiMatKhau);
        Thoat=findViewById(R.id.Exit);
        QuanLyUser=findViewById(R.id.QuanLyUser);
        QuanLyLopHoc=findViewById(R.id.QuanLyLop);
        QuanLySinhVien=findViewById(R.id.QuanLySinhVien);
        etTenUser=findViewById(R.id.etTenUser);
        etTenUser.setText(getIntent().getStringExtra("xinchao"));
        DangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(QuanLyUserActivity.this);
                View view= LayoutInflater.from(QuanLyUserActivity.this).inflate(R.layout.dialog_xac_nhan,null);
                TextView xacnhan=view.findViewById(R.id.tvXacNhan);
                xacnhan.setText("XÁC NHẬN ĐĂNG XUẤT");
                builder.setView(view);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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
        DoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyUserActivity.this);
                View view = LayoutInflater.from(QuanLyUserActivity.this).inflate(R.layout.dialog_doi_mat_khau, null);
                builder.setView(view);
                TextView matkhaucu = view.findViewById(R.id.etMatKhauCu);
                TextView matkhaumoi = view.findViewById(R.id.etMatKhauMoi);
                TextView xacnhanmatkhaumoi = view.findViewById(R.id.etXacNhanMatKhauMoi);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if (matkhaucu.getText().toString().equals("") || matkhaumoi.getText().toString().equals("")) {
                                Toast.makeText(QuanLyUserActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                            } else if (xacnhanmatkhaumoi.getText().toString().equalsIgnoreCase(matkhaumoi.getText().toString()) == false) {
                                Toast.makeText(QuanLyUserActivity.this, "Xác nhận mật khẩu chưa chính xác", Toast.LENGTH_SHORT).show();
                            } else {
                                userDAO = new UserDAO(QuanLyUserActivity.this);
                                dsUser=userDAO.getAllUser();
                                int id=-1;
                                for(int i=0;i<dsUser.size();i++){
                                    if(etTenUser.getText().toString().equals(dsUser.get(i).getTaikhoan())){
                                        id=dsUser.get(i).getId_user();
                                        break;
                                    }
                                }
                                if(id==-1){
                                    Toast.makeText(QuanLyUserActivity.this, "Mật khẩu cũ chưa chính xác", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    userDAO.SuaUser(matkhaumoi.getText().toString(),id);
                                    Toast.makeText(QuanLyUserActivity.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception ex) {
                            Toast.makeText(QuanLyUserActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
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
        Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(QuanLyUserActivity.this);
                View view= LayoutInflater.from(QuanLyUserActivity.this).inflate(R.layout.dialog_xac_nhan,null);
                TextView xacnhan=view.findViewById(R.id.tvXacNhan);
                xacnhan.setText("XÁC NHẬN THOÁT");
                builder.setView(view);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                        System.exit(0);
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
        QuanLyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QuanLyUserActivity.this,UserActivity.class);
                intent.putExtra("userhoatdong",etTenUser.getText().toString());
                startActivity(intent);
            }
        });
        QuanLyLopHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QuanLyUserActivity.this,LopActivity.class);
                startActivity(intent);
            }
        });
        QuanLySinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QuanLyUserActivity.this,SinhVienActivity.class);
                startActivity(intent);
            }
        });
    }
}