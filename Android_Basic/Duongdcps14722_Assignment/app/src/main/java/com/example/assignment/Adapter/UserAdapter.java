package com.example.assignment.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.assignment.Model.User;
import com.example.assignment.QuanLyUserActivity;
import com.example.assignment.R;
import com.example.assignment.SQLite.UserDAO;
import com.example.assignment.SinhVienActivity;
import com.example.assignment.UserActivity;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    public Context c;
    public ArrayList<User> dsuser=new ArrayList<User>();
    UserDAO userDAO;
    public UserAdapter(Context c, ArrayList<User> dsuser, UserDAO userDAO){
        this.c=c;
        this.dsuser=dsuser;
        this.userDAO=userDAO;
    }
    @Override
    public int getCount() {
        return dsuser.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserAdapter.View_mot_o mot_o;
        LayoutInflater inf = ((Activity)c).getLayoutInflater();
        if(convertView==null){
            mot_o=new UserAdapter.View_mot_o();
            convertView=inf.inflate(R.layout.user,null);
            mot_o.tenuser=convertView.findViewById(R.id.tv_layout_tenUser);
            mot_o.xoauser=convertView.findViewById(R.id.btnXoaUser);
            convertView.setTag(mot_o);
        }
        else{
            mot_o=(UserAdapter.View_mot_o)convertView.getTag();
        }
        mot_o.tenuser.setText("Tên tài khoản: "+dsuser.get(position).getTaikhoan());
        mot_o.xoauser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XoaUser(position);
            }
        });
        return convertView;
    }
    public class View_mot_o{
        public TextView tenuser;
        public ImageView xoauser;
    }
    public void XoaUser(int position){
        for(int i=0;i<dsuser.size();i++){
            try {
                if(((UserActivity)c).getUserHoatDong().equals(dsuser.get(position).getTaikhoan())){
                    Toast.makeText(c,"Đang đăng nhập tài khoản này!",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(position==i){
                    AlertDialog.Builder builder=new AlertDialog.Builder(c);
                    View view= LayoutInflater.from(c).inflate(R.layout.dialog_xac_nhan,null);
                    TextView xacnhan=view.findViewById(R.id.tvXacNhan);
                    xacnhan.setText("XÁC NHẬN XÓA USER");
                    builder.setView(view);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            userDAO.XoaUser(dsuser.get(position).getId_user());
                            ((UserActivity)c).CapNhatUser();
                            Toast.makeText(c,"Xóa thành công!",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();

                }
            }catch (Exception ex){
                Toast.makeText(c,"Xóa thất bại",Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }
}
