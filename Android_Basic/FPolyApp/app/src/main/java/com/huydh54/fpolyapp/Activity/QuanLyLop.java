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

import com.huydh54.fpolyapp.Adapter.CategoryLopAdapter;
import com.huydh54.fpolyapp.Adapter.LopHocAdapter;
import com.huydh54.fpolyapp.DAO.LopHocDAO;
import com.huydh54.fpolyapp.DAO.SinhVienDAO;
import com.huydh54.fpolyapp.DAO.TaiKhoanDAO;
import com.huydh54.fpolyapp.DAO.ThongBaoDAO;
import com.huydh54.fpolyapp.Model.Category;
import com.huydh54.fpolyapp.Model.LopHoc;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;
import java.util.List;

public class QuanLyLop extends AppCompatActivity {

    ListView lvQuanLyLop;
    LopHocAdapter lopHocAdapter;
    LopHocDAO lopHocDAO;
    SinhVienDAO sinhVienDAO;
    TaiKhoanDAO taiKhoanDAO;
    ThongBaoDAO thongBaoDAO;
    ImageView thoat;
    Button themMoi;
    EditText ma, khoa;
    Spinner nganh;
    ArrayList<LopHoc> dsLopHoc;
    Toolbar tbQuanLyLop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_lop);

        lvQuanLyLop = findViewById(R.id.lvQLLop);
        tbQuanLyLop = findViewById(R.id.tbQuanLyLop);
        lopHocDAO = new LopHocDAO(QuanLyLop.this);
        sinhVienDAO = new SinhVienDAO(QuanLyLop.this);
        taiKhoanDAO = new TaiKhoanDAO(QuanLyLop.this);
        thongBaoDAO = new ThongBaoDAO(QuanLyLop.this);

//        lopHocDAO.them("LT16201_3", "16.2", "Lập trình di động");
//        lopHocDAO.them("UD16202_3", "16.2", "Ứng dụng phần mềm");
//        lopHocDAO.them("UD16203_3", "16.1", "Ứng dụng phần mềm");
//        lopHocDAO.them("WEB16201_3", "16.2", "Thiết kế Web");
//        lopHocDAO.them("DIG15303_2", "15.3", "Digital - Marketing");
//        lopHocDAO.them("MAR15203_2", "15.3", "Marketing - Truyền thông");
//        lopHocDAO.them("MUL14301_1", "15.2", "Thiết kế đồ họa");
//        lopHocDAO.them("RES13302_2", "15.1", "Quản trị nhà hàng khách sạn");

        dsLopHoc = lopHocDAO.doc();
        lopHocAdapter = new LopHocAdapter(QuanLyLop.this, dsLopHoc);
        lvQuanLyLop.setAdapter(lopHocAdapter);

        setSupportActionBar(tbQuanLyLop);
        tbQuanLyLop.setNavigationIcon(R.drawable.ic_webview_back);
        tbQuanLyLop.setNavigationOnClickListener(new View.OnClickListener() {
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
        getMenuInflater().inflate(R.menu.menu_quanlylop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.themlop:
                them();
                return true;
            case R.id.xoahetlop:
                clear();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clear() {
        if(dsLopHoc.size()==0){
            dialogThongBao("Danh sách không có lớp học nào!");
        }else {
            Dialog dialog = new Dialog(QuanLyLop.this);
            dialog.setContentView(R.layout.dialog_xoa);
            dialog.show();

            TextView noiDungXoa = dialog.findViewById(R.id.txtNoiDungTBXoa);
            Button xacNhan = dialog.findViewById(R.id.btnXacNhanXoa);
            Button huy = dialog.findViewById(R.id.btnHuyXoa);
            ImageView thoat = dialog.findViewById(R.id.imgThoatXoaQLKH);

            noiDungXoa.setText("Bạn chắc chắn muốn xóa toàn bộ lớp học?");

            xacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        lopHocDAO.xoaToanBo();
                        dsLopHoc = lopHocDAO.doc();
                        lopHocAdapter = new LopHocAdapter(QuanLyLop.this, dsLopHoc);
                        lvQuanLyLop.setAdapter(lopHocAdapter);
                        Toast.makeText(QuanLyLop.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }catch (Exception e) {
                        Dialog dialogXoaSV = new Dialog(QuanLyLop.this);
                        dialogXoaSV.setContentView(R.layout.dialog_xoa);
                        dialogXoaSV.show();

                        TextView noiDungXoaSV = dialogXoaSV.findViewById(R.id.txtNoiDungTBXoa);
                        Button xacNhanSV = dialogXoaSV.findViewById(R.id.btnXacNhanXoa);
                        Button huySV = dialogXoaSV.findViewById(R.id.btnHuyXoa);
                        ImageView thoatSV = dialogXoaSV.findViewById(R.id.imgThoatXoaQLKH);

                        noiDungXoaSV.setText("Lớp học đang hoạt động. Bạn phải xóa toàn bộ dữ liệu sinh viên! Xác nhận xóa?");

                        xacNhanSV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                taiKhoanDAO.xoaToanBo();
                                sinhVienDAO.xoaToanBo();
                                lopHocDAO.xoaToanBo();
                                dsLopHoc = lopHocDAO.doc();
                                lopHocAdapter = new LopHocAdapter(QuanLyLop.this, dsLopHoc);
                                lvQuanLyLop.setAdapter(lopHocAdapter);
                                Toast.makeText(QuanLyLop.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                dialogXoaSV.dismiss();
                                dialog.dismiss();
                                thongBaoDAO.them(R.drawable.ic_thongbao_xoa, "Bạn đã xóa toàn bộ lớp học và sinh viên!");
                                Toast.makeText(view.getContext(), "Bạn có thông báo mới!", Toast.LENGTH_SHORT).show();
                            }
                        });

                        huySV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(QuanLyLop.this, "Thao tác bị hủy!", Toast.LENGTH_SHORT).show();
                                dialogXoaSV.dismiss();
                                dialog.dismiss();
                            }
                        });

                        thoatSV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogXoaSV.dismiss();
                                dialog.dismiss();
                            }
                        });
                    }
                }
            });

            huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(QuanLyLop.this, "Thao tác bị hủy!", Toast.LENGTH_SHORT).show();
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
        dialogThemLop();
    }

    private void dialogThemLop() {
        Dialog dialog = new Dialog(QuanLyLop.this);
        dialog.setContentView(R.layout.dialog_themlop2);
        dialog.show();

        thoat = dialog.findViewById(R.id.imgThoatThemQLL);
        themMoi = dialog.findViewById(R.id.btnThemMoiQLL);
        ma = dialog.findViewById(R.id.edtMaQLL);
        khoa = dialog.findViewById(R.id.edtKhoaDaoTao);
        nganh = dialog.findViewById(R.id.spnNganhDaoTao);
        ArrayList<String> dsNganh = new ArrayList();
        dsNganh.add("Lập trình di động");
        dsNganh.add("Ứng dụng phần mềm");
        dsNganh.add("Thiết kế Web");
        dsNganh.add("Digital marketing");
        dsNganh.add("Truyền thông");
        dsNganh.add("Thiết kế đồ họa");
        dsNganh.add("Nhà hàng khách sạn");
        CategoryLopAdapter categoryAdapter;

        List<Category> listNganh = new ArrayList<>();
        for (int i=0; i<dsNganh.size(); i++){
            Category category = new Category(dsNganh.get(i));
            listNganh.add(category);
        }
        categoryAdapter = new CategoryLopAdapter(QuanLyLop.this, R.layout.item_giaodienlop_spinner, listNganh);
        nganh.setAdapter(categoryAdapter);

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
                        khoa.getText().toString().isEmpty()){
                    Toast.makeText(QuanLyLop.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else{
                    dsLopHoc = lopHocDAO.doc();
                    int i;
                    for(i=0; i<dsLopHoc.size(); i++) {
                        if (dsLopHoc.get(i).getMa().equalsIgnoreCase(ma.getText().toString()))
                            break;
                    }
                    if(i<dsLopHoc.size())
                        Toast.makeText(QuanLyLop.this, "Mã khóa học đã được sử dụng!", Toast.LENGTH_SHORT).show();
                    else{
                        String tenNganh = dsNganh.get(nganh.getSelectedItemPosition());
                        lopHocDAO.them(
                                ma.getText().toString(),
                                khoa.getText().toString(),
                                tenNganh
                        );
                        Toast.makeText(QuanLyLop.this, "Thêm mới thành công!", Toast.LENGTH_SHORT).show();
                        ma.setText("");
                        khoa.setText("");
                        nganh.setSelection(0);

                        dsLopHoc = lopHocDAO.doc();
                        lopHocAdapter = new LopHocAdapter(QuanLyLop.this, dsLopHoc);
                        lvQuanLyLop.setAdapter(lopHocAdapter);
                        thongBaoDAO.them(R.drawable.ic_thongbao_themmoi, "Thêm lớp học mới thành công!");
                        Toast.makeText(QuanLyLop.this, "Bạn có thông báo mới!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void dialogThongBao(String noiDung) {
        Dialog dialog = new Dialog(QuanLyLop.this);
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