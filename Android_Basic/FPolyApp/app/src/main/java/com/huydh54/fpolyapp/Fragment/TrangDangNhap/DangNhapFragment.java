package com.huydh54.fpolyapp.Fragment.TrangDangNhap;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huydh54.fpolyapp.Activity.MainActivity;
import com.huydh54.fpolyapp.Activity.TrangDangNhap;
import com.huydh54.fpolyapp.DAO.TaiKhoanDAO;
import com.huydh54.fpolyapp.Model.TaiKhoan;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;

//"Tài khoản: admin@123\nMật khẩu: admin"
public class DangNhapFragment extends Fragment {

    Button btnDangNhap;
    CheckBox nhoTaiKhoan;
    EditText edtTaiKhoan, edtMatKhau;
    TaiKhoanDAO taiKhoanDAO;
    ArrayList<TaiKhoan> dsTaiKhoan;
    String taiKhoanAM = "admin123", matKhauAM = "admin";
    String maDangNhap = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_nhap, container, false);

        btnDangNhap = view.findViewById(R.id.btnDangNhapTC);
        edtTaiKhoan = view.findViewById(R.id.edtEmail);
        edtMatKhau = view.findViewById(R.id.edtPass);
        nhoTaiKhoan = view.findViewById(R.id.cbNhoTK);
        taiKhoanDAO = new TaiKhoanDAO(getContext());
        dsTaiKhoan = taiKhoanDAO.doc();

        tuDongDien();
        if(checkLoginRemember()>0){
            ghiNhoTaiKhoan(edtTaiKhoan.getText().toString(), edtMatKhau.getText().toString(), nhoTaiKhoan.isChecked());
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
        }

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dsTaiKhoan = taiKhoanDAO.doc();
                int i;
                for(i=0; i<dsTaiKhoan.size(); i++){
                    if(edtTaiKhoan.getText().toString().equals(dsTaiKhoan.get(i).getTenTK()) &&
                            edtMatKhau.getText().toString().equals(dsTaiKhoan.get(i).getMatKhau())){
                        maDangNhap = dsTaiKhoan.get(i).getMaSV();
                        break;
                    }
                }
                if(i<dsTaiKhoan.size() ||
                        (edtTaiKhoan.getText().toString().equals(taiKhoanAM) &&
                                edtMatKhau.getText().toString().equals(matKhauAM))){
                    ghiNhoTaiKhoan(edtTaiKhoan.getText().toString(), edtMatKhau.getText().toString(), nhoTaiKhoan.isChecked());
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    ghiMa();
                    startActivity(intent);
                }else{
                    dialogThongBao("Tài khoản hoặc mật khẩu không đúng!");
                }
            }
        });

        return view;
    }

    public void ghiNhoTaiKhoan(String user, String pass, boolean check){
        SharedPreferences sp = this.getActivity().getSharedPreferences("luudangnhap.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(check==false || edtTaiKhoan.getText().toString().equalsIgnoreCase("admin@123")){
            editor.clear();
        }else{
            editor.putString("user", edtTaiKhoan.getText().toString());
            editor.putString("pass", edtMatKhau.getText().toString());
            editor.putInt("check", 1);
        }
        editor.commit();
    }

    public void ghiMa(){
        SharedPreferences sp = this.getActivity().getSharedPreferences("luuma.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(edtTaiKhoan.getText().toString().equalsIgnoreCase("admin123")){
            editor.putString("maluu", "admin");
        }else {
            editor.putString("maluu", maDangNhap);
        }
        editor.commit();
    }

    public void tuDongDien(){
        SharedPreferences sp = this.getActivity().getSharedPreferences("luudangnhap.txt", Context.MODE_PRIVATE);
        edtTaiKhoan.setText(sp.getString("user", ""));
        edtMatKhau.setText(sp.getString("pass", ""));
        if(sp.getInt("check", -1)==-1){
            nhoTaiKhoan.setChecked(false);
        }else {
            nhoTaiKhoan.setChecked(true);
        }
    }

    public int checkLoginRemember(){
        SharedPreferences sp = this.getActivity().getSharedPreferences("luudangnhap.txt", Context.MODE_PRIVATE);
        int check = sp.getInt("check", -1);
        return check;
    }
    private void dialogThongBao(String noiDung) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_thongbao);
        dialog.show();

        Button xacNhan = dialog.findViewById(R.id.btnXacNhanTB);
        TextView thongBao = dialog.findViewById(R.id.txtNoiDungTB);
        ImageView thoat = dialog.findViewById(R.id.imgThoatTB);

        thongBao.setText(noiDung);

        xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}