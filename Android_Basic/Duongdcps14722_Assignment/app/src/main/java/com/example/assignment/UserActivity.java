package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.assignment.Adapter.LopAdapter;
import com.example.assignment.Adapter.SVAdapter;
import com.example.assignment.Adapter.UserAdapter;
import com.example.assignment.Model.User;
import com.example.assignment.SQLite.SinhVienDAO;
import com.example.assignment.SQLite.UserDAO;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    UserDAO userDAO;
    ArrayList<User> dsUser=new ArrayList<>();
    ListView lvUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        lvUser=findViewById(R.id.lvUser);
        userDAO=new UserDAO(UserActivity.this);
        dsUser=userDAO.getAllUser();
        if(dsUser.size()==0){
            Toast.makeText(UserActivity.this, "Danh sách trống",Toast.LENGTH_SHORT).show();
        }
        else {
            UserAdapter userAdapter=new UserAdapter(UserActivity.this,dsUser,userDAO);
            lvUser.setAdapter(userAdapter);
        }
    }
    public void CapNhatUser(){
        dsUser=userDAO.getAllUser();
        UserAdapter userAdapter=new UserAdapter(UserActivity.this,dsUser,userDAO);
        lvUser.setAdapter(userAdapter);
    }
    public String getUserHoatDong(){
        return getIntent().getStringExtra("userhoatdong");
    }
}