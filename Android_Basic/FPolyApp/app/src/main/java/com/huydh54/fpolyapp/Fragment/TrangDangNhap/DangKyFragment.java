package com.huydh54.fpolyapp.Fragment.TrangDangNhap;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huydh54.fpolyapp.DAO.TaiKhoanDAO;
import com.huydh54.fpolyapp.Model.TaiKhoan;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;

public class DangKyFragment extends Fragment {

    EditText mSSV, email, matKhau;
    Button taoTK;
    TaiKhoanDAO taiKhoanDAO;
    ArrayList<TaiKhoan> dsTaiKhoan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_ky, container, false);

        mSSV = view.findViewById(R.id.edtMSSV);
        email = view.findViewById(R.id.edtEmail2);
        matKhau = view.findViewById(R.id.edtPass2);
        taoTK = view.findViewById(R.id.btnDangKy);
        taiKhoanDAO = new TaiKhoanDAO(getContext());
        dsTaiKhoan = taiKhoanDAO.doc();

        taoTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSSV.getText().toString().isEmpty() ||
                        email.getText().toString().isEmpty() ||
                        matKhau.getText().toString().isEmpty()){
                    dialogThongBao("Vui lòng nhập đủ thông tin!");
                }else if(matKhau.getText().toString().length()<8){
                    dialogThongBao("Mật khẩu phải ít nhất 8 ký tự!");
                }else{
                    int i;
                    for(i=0; i<dsTaiKhoan.size(); i++){
                        if(mSSV.getText().toString().equalsIgnoreCase(dsTaiKhoan.get(i).getMaSV())){
                            break;
                        }
                    }
                    if(i<dsTaiKhoan.size()){
                        dialogThongBao("Sinh viên này đã có tài khoản!");
                        mSSV.setText("");
                        mSSV.requestFocus();
                    }else{
                        int j;
                        for(j=0; j<dsTaiKhoan.size(); j++){
                            if(email.getText().toString().equalsIgnoreCase(dsTaiKhoan.get(j).getTenTK())){
                                break;
                            }
                        }
                        if(j<dsTaiKhoan.size()){
                            dialogThongBao("Tên tài khoản đã được sử dụng!");
                            email.setText("");
                            email.requestFocus();
                        }else{
                            try {
                                taiKhoanDAO.them(
                                    email.getText().toString(),
                                    matKhau.getText().toString(),
                                    mSSV.getText().toString()
                                );

                                dialogThongBao("Đăng ký thành công!");
                                mSSV.setText("");
                                email.setText("");
                                matKhau.setText("");
                                ViewPager viewPager = (ViewPager)getActivity().findViewById(R.id.vpKhungNhap);
                                viewPager.setCurrentItem(0, true);
                            } catch (android.database.sqlite.SQLiteConstraintException e) {
                                dialogThongBao("Mã số sinh viên không tồn tại!");
                                mSSV.setText("");
                                mSSV.requestFocus();
                            }
                        }
                    }
                }
            }
        });

        return view;

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