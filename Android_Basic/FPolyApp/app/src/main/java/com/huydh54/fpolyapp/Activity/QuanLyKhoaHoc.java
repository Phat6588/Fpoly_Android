package com.huydh54.fpolyapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huydh54.fpolyapp.Adapter.QLKhoaHocAdapter;
import com.huydh54.fpolyapp.DAO.KhoaHocDAO;
import com.huydh54.fpolyapp.DAO.ThongBaoDAO;
import com.huydh54.fpolyapp.Model.KhoaHoc;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;

public class QuanLyKhoaHoc extends AppCompatActivity {

    ListView lvQuanLyKH;
    QLKhoaHocAdapter qlKhoaHocAdapter;
    KhoaHocDAO khoaHocDAO;
    ImageView thoat;
    Button themMoi;
    EditText anh, ma, ten, ngay;
    ArrayList<KhoaHoc> khoaHocList;
    Toolbar tbQuanLyKH;
    ThongBaoDAO thongBaoDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_khoa_hoc);

        lvQuanLyKH = findViewById(R.id.lvQLKhoaHoc);
        tbQuanLyKH = findViewById(R.id.tbKhoaHoc);
        khoaHocDAO = new KhoaHocDAO(QuanLyKhoaHoc.this);
        thongBaoDAO = new ThongBaoDAO(QuanLyKhoaHoc.this);

        khoaHocList = khoaHocDAO.doc();

        qlKhoaHocAdapter = new QLKhoaHocAdapter(QuanLyKhoaHoc.this, khoaHocList);
        lvQuanLyKH.setAdapter(qlKhoaHocAdapter);

        setSupportActionBar(tbQuanLyKH);
        tbQuanLyKH.setNavigationIcon(R.drawable.ic_webview_back);
        tbQuanLyKH.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quanlykh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.themkhoahoc:
                them();
                return true;
            case R.id.xoatatcakh:
                clear();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clear() {
        if(khoaHocList.size()==0){
            dialogThongBao("Danh sách không có sinh viên nào!");
        }else {
            Dialog dialog = new Dialog(QuanLyKhoaHoc.this);
            dialog.setContentView(R.layout.dialog_xoa);
            dialog.show();

            TextView noiDungXoa = dialog.findViewById(R.id.txtNoiDungTBXoa);
            Button xacNhan = dialog.findViewById(R.id.btnXacNhanXoa);
            Button huy = dialog.findViewById(R.id.btnHuyXoa);
            ImageView thoat = dialog.findViewById(R.id.imgThoatXoaQLKH);

            noiDungXoa.setText("Bạn chắc chắn muốn xóa toàn bộ khóa học?");

            xacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    khoaHocDAO.xoaToanBo();
                    khoaHocList = khoaHocDAO.doc();
                    qlKhoaHocAdapter = new QLKhoaHocAdapter(QuanLyKhoaHoc.this, khoaHocList);
                    lvQuanLyKH.setAdapter(qlKhoaHocAdapter);
                    Toast.makeText(QuanLyKhoaHoc.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    thongBaoDAO.them(R.drawable.ic_thongbao_xoa, "Bạn đã xóa toàn bộ khóa học!");
                    Toast.makeText(view.getContext(), "Bạn có thông báo mới!", Toast.LENGTH_SHORT).show();
                }
            });

            huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(QuanLyKhoaHoc.this, "Thao tác bị hủy!", Toast.LENGTH_SHORT).show();
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

    private void them() {
        dialogThemKhoaHoc();
    }

    private void dialogThemKhoaHoc() {
        Dialog dialog = new Dialog(QuanLyKhoaHoc.this);
        dialog.setContentView(R.layout.dialog_themkhoahoc);
        dialog.show();

        thoat = dialog.findViewById(R.id.imgThoatThemQLKH);
        themMoi = dialog.findViewById(R.id.btnThemMoiQLKH);
        anh = dialog.findViewById(R.id.edtTenAnh);
        ma = dialog.findViewById(R.id.edtMaQLKH);
        ten = dialog.findViewById(R.id.edtTenKH);
        ngay = dialog.findViewById(R.id.edtNgayBatDauQLKH);

        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        themMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ma.getText().toString().isEmpty() ||
                        anh.getText().toString().isEmpty() ||
                        ngay.getText().toString().isEmpty() ||
                        ten.getText().toString().isEmpty()){
                    Toast.makeText(QuanLyKhoaHoc.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else{
                    khoaHocList = khoaHocDAO.doc();
                    int i;
                    for(i=0; i<khoaHocList.size(); i++) {
                        if (khoaHocList.get(i).getMa().equalsIgnoreCase(ma.getText().toString()))
                            break;
                    }
                    if(i<khoaHocList.size())
                        Toast.makeText(QuanLyKhoaHoc.this, "Mã khóa học đã được sử dụng!", Toast.LENGTH_SHORT).show();
                    else{
                        khoaHocDAO.them(
                                ma.getText().toString(),
                                anh.getText().toString(),
                                ngay.getText().toString(),
                                ten.getText().toString()
                        );
                        Toast.makeText(QuanLyKhoaHoc.this, "Thêm mới thành công!", Toast.LENGTH_SHORT).show();
                        anh.setText("");
                        ma.setText("");
                        ngay.setText("");
                        ten.setText("");

                        khoaHocList = khoaHocDAO.doc();
                        qlKhoaHocAdapter = new QLKhoaHocAdapter(QuanLyKhoaHoc.this, khoaHocList);
                        lvQuanLyKH.setAdapter(qlKhoaHocAdapter);
                        thongBaoDAO.them(R.drawable.ic_thongbao_themmoi, "Thêm khóa học mới thành công!");
                        Toast.makeText(QuanLyKhoaHoc.this, "Bạn có thông báo mới!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void dialogThongBao(String noiDung) {
        Dialog dialog = new Dialog(QuanLyKhoaHoc.this);
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