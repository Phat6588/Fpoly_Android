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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.huydh54.fpolyapp.Adapter.CategoryAdapter;
import com.huydh54.fpolyapp.Adapter.TaiKhoanAdapter;
import com.huydh54.fpolyapp.DAO.SinhVienDAO;
import com.huydh54.fpolyapp.DAO.TaiKhoanDAO;
import com.huydh54.fpolyapp.DAO.ThongBaoDAO;
import com.huydh54.fpolyapp.Model.Category;
import com.huydh54.fpolyapp.Model.TaiKhoan;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;
import java.util.List;

public class QuanLyTaiKhoan extends AppCompatActivity {

    ListView lvQuanLyTK;
    TaiKhoanAdapter taiKhoanAdapter;
    TaiKhoanDAO taiKhoanDAO;
    SinhVienDAO sinhVienDAO;
    ImageView thoat;
    Button themMoi;
    EditText taiKhoan, matKhau;
    Spinner maSinhVien;
    ArrayList<TaiKhoan> dsTaiKhoan;
    ArrayList<String> dsSinhVien;
    CategoryAdapter categoryAdapter;
    Toolbar tbQuanLyTK;
    ThongBaoDAO thongBaoDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_tai_khoan);

        lvQuanLyTK = findViewById(R.id.lvQuanLyTK);
        tbQuanLyTK = findViewById(R.id.tbQuanLyTK);
        taiKhoanDAO = new TaiKhoanDAO(QuanLyTaiKhoan.this);
        sinhVienDAO = new SinhVienDAO(QuanLyTaiKhoan.this);

        dsTaiKhoan = taiKhoanDAO.doc();
        taiKhoanAdapter = new TaiKhoanAdapter(QuanLyTaiKhoan.this, dsTaiKhoan);
        lvQuanLyTK.setAdapter(taiKhoanAdapter);
        thongBaoDAO = new ThongBaoDAO(QuanLyTaiKhoan.this);

        setSupportActionBar(tbQuanLyTK);
        tbQuanLyTK.setNavigationIcon(R.drawable.ic_webview_back);
        tbQuanLyTK.setNavigationOnClickListener(new View.OnClickListener() {
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
        getMenuInflater().inflate(R.menu.menu_quanlytk, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.themtaikhoan:
                them();
                return true;
            case R.id.xoatatcatk:
                clear();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void them() {
        dsSinhVien = sinhVienDAO.docMaSinhVien();
        for (int j=0; j<dsSinhVien.size(); j++) {
            for (int k=0; k < dsTaiKhoan.size(); k++) {
                if (dsSinhVien.get(j).equalsIgnoreCase(dsTaiKhoan.get(k).getMaSV())) {
                    dsSinhVien.remove(j);
                    j--;
                    break;
                }
            }
        }
        if(dsSinhVien.size()==0){
            dialogThongBao("Chưa có sinh viên nào cần tài khoản!");
        }else {
            dialogThemTK();
        }
    }

    private void clear() {
        if(dsTaiKhoan.size()==0){
            dialogThongBao("Danh sách không có tài khoản nào!");
        }else {
            Dialog dialog = new Dialog(QuanLyTaiKhoan.this);
            dialog.setContentView(R.layout.dialog_xoa);
            dialog.show();

            TextView noiDungXoa = dialog.findViewById(R.id.txtNoiDungTBXoa);
            Button xacNhan = dialog.findViewById(R.id.btnXacNhanXoa);
            Button huy = dialog.findViewById(R.id.btnHuyXoa);
            ImageView thoat = dialog.findViewById(R.id.imgThoatXoaQLKH);

            noiDungXoa.setText("Bạn chắc chắn muốn xóa toàn bộ tài khoản?");

            xacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    taiKhoanDAO.xoaToanBo();
                    dsTaiKhoan = taiKhoanDAO.doc();
                    taiKhoanAdapter = new TaiKhoanAdapter(QuanLyTaiKhoan.this, dsTaiKhoan);
                    lvQuanLyTK.setAdapter(taiKhoanAdapter);
                    Toast.makeText(QuanLyTaiKhoan.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    thongBaoDAO.them(R.drawable.ic_thongbao_xoa, "Bạn đã xóa toàn bộ tài khoản!");
                    Toast.makeText(view.getContext(), "Bạn có thông báo mới!", Toast.LENGTH_SHORT).show();
                }
            });

            huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(QuanLyTaiKhoan.this, "Thao tác bị hủy!", Toast.LENGTH_SHORT).show();
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

    private void dialogThemTK() {
        Dialog dialog = new Dialog(QuanLyTaiKhoan.this);
        dialog.setContentView(R.layout.dialog_themtaikhoan);
        dialog.show();
        thoat = dialog.findViewById(R.id.imgThoatThemTK);
        themMoi = dialog.findViewById(R.id.btnThemMoiTK);
        taiKhoan = dialog.findViewById(R.id.edtTaiKhoanTTK);
        matKhau = dialog.findViewById(R.id.edtMatKhauTTK);
        maSinhVien = dialog.findViewById(R.id.spnMaSVTTK);

        List<Category> listSinhVien = new ArrayList<>();
        for (int i=0; i<dsSinhVien.size(); i++){
            Category category = new Category(dsSinhVien.get(i));
            listSinhVien.add(category);
        }

        categoryAdapter = new CategoryAdapter(QuanLyTaiKhoan.this, R.layout.item_giaodien_spinner, listSinhVien);
        maSinhVien.setAdapter(categoryAdapter);

        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        themMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(taiKhoan.getText().toString().isEmpty() ||
                        matKhau.getText().toString().isEmpty()){
                    Toast.makeText(QuanLyTaiKhoan.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    String maSV = dsSinhVien.get(maSinhVien.getSelectedItemPosition());
                    try {
                        taiKhoanDAO.them(
                                taiKhoan.getText().toString(),
                                matKhau.getText().toString(),
                                maSV
                        );

                        Toast.makeText(QuanLyTaiKhoan.this, "Thêm mới thành công!", Toast.LENGTH_SHORT).show();
                        taiKhoan.setText("");
                        matKhau.setText("");
                        maSinhVien.setSelection(0);

                        dsTaiKhoan = taiKhoanDAO.doc();
                        taiKhoanAdapter = new TaiKhoanAdapter(QuanLyTaiKhoan.this, dsTaiKhoan);
                        lvQuanLyTK.setAdapter(taiKhoanAdapter);
                        dialog.dismiss();
                        thongBaoDAO.them(R.drawable.ic_thongbao_themmoi, "Thêm tài khoản mới thành công!");
                        Toast.makeText(QuanLyTaiKhoan.this, "Bạn có thông báo mới!", Toast.LENGTH_SHORT).show();
                    } catch (android.database.sqlite.SQLiteConstraintException e) {
                        dialogThongBao("Trùng lặp dữ liệu!");
                    }
                }
            }
        });
    }

    private void dialogThongBao(String noiDung) {
        Dialog dialog = new Dialog(QuanLyTaiKhoan.this);
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