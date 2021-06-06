package com.huydh54.fpolyapp.Activity;

import android.app.Activity;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.huydh54.fpolyapp.Adapter.CategoryAdapter;
import com.huydh54.fpolyapp.Adapter.SinhVienAdapter;
import com.huydh54.fpolyapp.DAO.LopHocDAO;
import com.huydh54.fpolyapp.DAO.SinhVienDAO;
import com.huydh54.fpolyapp.DAO.ThongBaoDAO;
import com.huydh54.fpolyapp.Model.Category;
import com.huydh54.fpolyapp.Model.SinhVien;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;
import java.util.List;

public class QuanLySinhVien extends AppCompatActivity {

    ListView lvQuanLySinhVien;
    SinhVienAdapter sinhVienAdapter;
    SinhVienDAO sinhVienDAO;
    LopHocDAO lopHocDAO;
    ThongBaoDAO thongBaoDAO;
    ImageView thoat;
    Button themMoi;
    ImageView khungAnh;
    EditText ma, ten;
    Spinner lop;
    ArrayList<SinhVien> dsSinhVien;
    ArrayList<String> dsLopHoc;
    CategoryAdapter categoryAdapter;
    Toolbar tbQuanLySV;
    int viTriSV = -1;
    String tenAnh = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_sinh_vien);

        lvQuanLySinhVien = findViewById(R.id.lvQLSinhVien);
        tbQuanLySV = findViewById(R.id.tbQuanLySV);
        sinhVienDAO = new SinhVienDAO(QuanLySinhVien.this);
        lopHocDAO = new LopHocDAO(QuanLySinhVien.this);
        thongBaoDAO = new ThongBaoDAO(QuanLySinhVien.this);

//        sinhVienDAO.them("PS14760", "load_st1", "Đặng Hoàng Huy", "LT16201_3");
//        sinhVienDAO.them("PS14761", "load_st2", "Phan Hồng Quân", "LT16201_3");
//        sinhVienDAO.them("PS13212", "load_st3", "Phạm Nhật Nghĩa", "UD16202_3");
//        sinhVienDAO.them("PS13342", "load_st4", "Huỳnh Tấn Thành", "UD16203_3");
//        sinhVienDAO.them("PS14542", "load_st5", "Trần Phước Sanh", "LT16201_3");
//        sinhVienDAO.them("PS12542", "load_st6", "Phạm Thành Khương","WD16201_3");

        dsSinhVien = sinhVienDAO.doc();
        dsLopHoc = lopHocDAO.docMaLop();
        sinhVienAdapter = new SinhVienAdapter(QuanLySinhVien.this, dsSinhVien);
        lvQuanLySinhVien.setAdapter(sinhVienAdapter);

        setSupportActionBar(tbQuanLySV);
        tbQuanLySV.setNavigationIcon(R.drawable.ic_webview_back);
        tbQuanLySV.setNavigationOnClickListener(new View.OnClickListener() {
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
        getMenuInflater().inflate(R.menu.menu_quanlysv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.themsinhvien:
                them();
                return true;
            case R.id.xoatatca:
                clear();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clear() {
        if(dsSinhVien.size()==0){
            dialogThongBao("Danh sách không có sinh viên nào!");
        }else {
            Dialog dialog = new Dialog(QuanLySinhVien.this);
            dialog.setContentView(R.layout.dialog_xoa);
            dialog.show();

            TextView noiDungXoa = dialog.findViewById(R.id.txtNoiDungTBXoa);
            Button xacNhan = dialog.findViewById(R.id.btnXacNhanXoa);
            Button huy = dialog.findViewById(R.id.btnHuyXoa);
            ImageView thoat = dialog.findViewById(R.id.imgThoatXoaQLKH);

            noiDungXoa.setText("Bạn chắc chắn muốn xóa toàn bộ sinh viên?");

            xacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sinhVienDAO.xoaToanBo();
                    dsSinhVien = sinhVienDAO.doc();
                    sinhVienAdapter = new SinhVienAdapter(QuanLySinhVien.this, dsSinhVien);
                    lvQuanLySinhVien.setAdapter(sinhVienAdapter);
                    Toast.makeText(QuanLySinhVien.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    thongBaoDAO.them(R.drawable.ic_thongbao_xoa, "Bạn đã xóa toàn bộ sinh viên!");
                    Toast.makeText(view.getContext(), "Bạn có thông báo mới!", Toast.LENGTH_SHORT).show();
                }
            });

            huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(QuanLySinhVien.this, "Thao tác bị hủy!", Toast.LENGTH_SHORT).show();
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
        if(dsLopHoc.size()==0){
            dialogThongBao("Chưa có lớp học nào được tạo, hãy tạo lớp học!");
        }else {
            dialogThemSinhVien();
        }
    }

    private void dialogThemSinhVien() {
        Dialog dialog = new Dialog(QuanLySinhVien.this);
        dialog.setContentView(R.layout.dialog_themsinhvien2);
        dialog.show();
        thoat = dialog.findViewById(R.id.imgThoatThemSV);
        themMoi = dialog.findViewById(R.id.btnThemMoiSV);
        ma = dialog.findViewById(R.id.edtMaSV);
        khungAnh = dialog.findViewById(R.id.imgAvatarSV);
        ten = dialog.findViewById(R.id.edtTenSV);
        lop = dialog.findViewById(R.id.spnMaLop);

        List<Category> listLop = new ArrayList<>();
        for (int i=0; i<dsLopHoc.size(); i++){
            Category category = new Category(dsLopHoc.get(i));
            listLop.add(category);
        }

        categoryAdapter = new CategoryAdapter(QuanLySinhVien.this, R.layout.item_giaodien_spinner, listLop);
        lop.setAdapter(categoryAdapter);

        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        khungAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ThuVien.class);
                startActivityForResult(intent, 999);
            }
        });

        themMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ma.getText().toString().isEmpty() ||
                        tenAnh.isEmpty() ||
                        ten.getText().toString().isEmpty()){
                    Toast.makeText(QuanLySinhVien.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    String maLop = dsLopHoc.get(lop.getSelectedItemPosition());
                    try {
                        sinhVienDAO.them(
                                ma.getText().toString(),
                                tenAnh,
                                ten.getText().toString(),
                                maLop
                        );

                        Toast.makeText(QuanLySinhVien.this, "Thêm mới thành công!", Toast.LENGTH_SHORT).show();
                        ma.setText("");
                        tenAnh = "";
                        int imgID = QuanLySinhVien.this.getResources().getIdentifier(
                                "avatar_loi", "drawable", QuanLySinhVien.this.getPackageName()
                        );
                        try {
                            khungAnh.setImageResource(imgID);
                        }catch (Exception e){
                            khungAnh.setImageResource(R.drawable.avatar_loi);
                        }
                        ten.setText("");
                        lop.setSelection(0);

                        dsSinhVien = sinhVienDAO.doc();
                        sinhVienAdapter = new SinhVienAdapter(QuanLySinhVien.this, dsSinhVien);
                        lvQuanLySinhVien.setAdapter(sinhVienAdapter);
                        thongBaoDAO.them(R.drawable.ic_thongbao_themmoi, "Thêm sinh viên mới thành công!");
                        Toast.makeText(QuanLySinhVien.this, "Bạn có thông báo mới!", Toast.LENGTH_SHORT).show();
                    } catch (android.database.sqlite.SQLiteConstraintException e) {
                        dialogThongBao("Mã sinh viên đã tồn tại!");
                        ma.setText("");
                        ma.requestFocus();
                    }
                }
            }
        });
    }

    private void dialogThongBao(String noiDung) {
        Dialog dialog = new Dialog(QuanLySinhVien.this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 999 && resultCode == RESULT_OK){
            Bundle bundleNhanAnh = data.getBundleExtra("boxanh");
            tenAnh = bundleNhanAnh.getString("tenanh");

            int imgID = QuanLySinhVien.this.getResources().getIdentifier(
                    tenAnh, "drawable", QuanLySinhVien.this.getPackageName()
            );
            try {
                khungAnh.setImageResource(imgID);
            }catch (Exception e){
                khungAnh.setImageResource(R.drawable.avatar_loi);
            }
        }
        if(requestCode == 888 && resultCode==RESULT_OK){
            Bundle bundleNhanAnh = data.getBundleExtra("boxanh");
            tenAnh = bundleNhanAnh.getString("tenanh");
            viTriSV = bundleNhanAnh.getInt("vitrisv2");
            dialogCapNhat();
        }
        if(requestCode == 888 && resultCode==RESULT_CANCELED){
            Bundle bundleNhanAnh = data.getBundleExtra("boxanh");
            viTriSV = bundleNhanAnh.getInt("vitrisv2");
            dialogCapNhat();
        }
    }

    private void dialogCapNhat() {
        Dialog dialog = new Dialog(QuanLySinhVien.this);
        dialog.setContentView(R.layout.dialog_themsinhvien2);
        dialog.show();
        TextView tieuDe = dialog.findViewById(R.id.txtTieuDeThemSV);
        ImageView thoat = dialog.findViewById(R.id.imgThoatThemSV);
        Button capNhat = dialog.findViewById(R.id.btnThemMoiSV);
        EditText ma = dialog.findViewById(R.id.edtMaSV);
        ImageView khungAnh = dialog.findViewById(R.id.imgAvatarSV);
        EditText ten = dialog.findViewById(R.id.edtTenSV);
        Spinner lop = dialog.findViewById(R.id.spnMaLop);
        SinhVienDAO sinhVienDAO = new SinhVienDAO(QuanLySinhVien.this);
        ArrayList<String> dsLopHoc;
        CategoryAdapter categoryAdapter;
        LopHocDAO lopHocDAO = new LopHocDAO(QuanLySinhVien.this);
        dsLopHoc = lopHocDAO.docMaLop();
        List<Category> listLop = new ArrayList<>();
        for (int i=0; i<dsLopHoc.size(); i++){
            Category category = new Category(dsLopHoc.get(i));
            listLop.add(category);
        }
        categoryAdapter = new CategoryAdapter(QuanLySinhVien.this, R.layout.item_giaodien_spinner, listLop);
        lop.setAdapter(categoryAdapter);

        tieuDe.setText("Cập nhật sinh viên");
        ma.setText(dsSinhVien.get(viTriSV).getMa());
        ma.setEnabled(false);
        if(tenAnh.isEmpty()){
            tenAnh = dsSinhVien.get(viTriSV).getAnh();
        }
        int imgID = QuanLySinhVien.this.getResources().getIdentifier(
                tenAnh, "drawable", QuanLySinhVien.this.getPackageName()
        );
        try {
            khungAnh.setImageResource(imgID);
        }catch (Exception e){
            khungAnh.setImageResource(R.drawable.avatar_loi);
        }
//                ten.setText("");
        ten.setText(dsSinhVien.get(viTriSV).getTen());
        for (int j=0; j<dsLopHoc.size(); j++){
            if (dsSinhVien.get(viTriSV).getLop().equalsIgnoreCase(dsLopHoc.get(j))){
                lop.setSelection(j);
                break;
            }
        }
        capNhat.setText("Cập nhật");

        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viTriSV = -1;
                tenAnh = "";
                dialog.dismiss();
            }
        });

        capNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ten.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    String maLop = dsLopHoc.get(lop.getSelectedItemPosition());
                    sinhVienDAO.capNhat(
                            viTriSV,
                            ma.getText().toString(),
                            tenAnh,
                            ten.getText().toString(),
                            maLop
                    );
                    dsSinhVien = sinhVienDAO.doc();
                    sinhVienAdapter = new SinhVienAdapter(QuanLySinhVien.this, dsSinhVien);
                    lvQuanLySinhVien.setAdapter(sinhVienAdapter);
                    sinhVienAdapter.notifyDataSetChanged();
                    Toast.makeText(view.getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    viTriSV = -1;
                    tenAnh = "";
                    dialog.dismiss();
                }
            }
        });

        khungAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ThuVien.class);
                Bundle bundleSV = new Bundle();
                bundleSV.putInt("vitrisv", viTriSV);
                intent.putExtra("boxsv", bundleSV);
                startActivityForResult(intent, 888);
                dialog.dismiss();
            }
        });
    }
}