package com.huydh54.assignment.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.huydh54.assignment.DAO.KhoanDAO;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.Khoan;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChiTietKhoanActivity extends AppCompatActivity {

    Toolbar toolbar;
    int maKhoan, viTri, maLoaiCapNhat=-1, viTriData;
//    String bang = "";
    CircleImageView anh;
    TextView ten, soTien, ngay, vi;
    KhoanDAO khoanDAO;
    LoaiDAO loaiDAO;
    Khoan khoan;
    Loai loai, loaiCapNhat;
    TextView tieuDeCN, chonLoaiCN, chonNgayCN, chonViCN;
    ImageView thoatCN;
    EditText soTienCN;
    Calendar lich = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_khoan);
        toolbar = findViewById(R.id.tbChiTietKhoan);
        anh = findViewById(R.id.imgAnhChiTietKhoan);
        ten = findViewById(R.id.txtTenChiTietKhoan);
        soTien = findViewById(R.id.txtSoTienCT);
        ngay = findViewById(R.id.txtNgayCT);
        vi = findViewById(R.id.txtViCT);
        khoanDAO = new KhoanDAO(ChiTietKhoanActivity.this);
        loaiDAO = new LoaiDAO(ChiTietKhoanActivity.this);

        toolbar.inflateMenu(R.menu.menu_chitiet);

        try {
            Bundle bundleNhanMa = getIntent().getBundleExtra("boxma");
            maKhoan = bundleNhanMa.getInt("makhoan");
            viTri = bundleNhanMa.getInt("vitri");
            viTriData = bundleNhanMa.getInt("vitridata");
//            bang = bundleNhanMa.getString("bang");
        } catch (Exception e) {}

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("boxao", false);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        khoan = khoanDAO.timTheoMa(maKhoan);
        loai = loaiDAO.timTheoMa(khoan.getMaLop());

        if(loai.getPhanLoai().equals("Loại thu")){
            soTien.setTextColor(Color.parseColor("#2196F3"));
        }

        if(loai.getPhanLoai().equals("Vay nợ") &&
                (loai.getTen().equals("Đi vay") || loai.getTen().equals("Thu nợ"))){
            soTien.setTextColor(Color.parseColor("#2196F3"));
        }

        String tenAnh = loai.getAnh();
        int imgID = ChiTietKhoanActivity.this.getResources().getIdentifier(
                tenAnh, "drawable", ChiTietKhoanActivity.this.getPackageName()
        );
        try {
            anh.setImageResource(imgID);
        }catch (Exception e){
            anh.setImageResource(R.drawable.load_loai_loi);
        }

        ten.setText(loai.getTen());

        String stringTienTe = dinhDangTien(khoan.getSoTien());

        soTien.setText(stringTienTe);
        ngay.setText(khoan.getNgay());
        vi.setText(khoan.getVi());

        toolbar.getMenu().getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                xoaKhoan();
                return true;
            }
        });
        toolbar.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                dialogChinhSuaKhoan();
                return true;
            }
        });

    }

    private String dinhDangTien(double soTien) {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        String patternTienTe = "###,###,### ₫";
        DecimalFormat formatTienTe = new DecimalFormat(patternTienTe, decimalFormatSymbols);
        return formatTienTe.format(soTien);
    }

    private void dialogChinhSuaKhoan() {
        Dialog dialog = new Dialog(ChiTietKhoanActivity.this);
        dialog.setContentView(R.layout.dialog_themkhoan);
        dialog.show();
        tieuDeCN = dialog.findViewById(R.id.txtTieuDeSuaKhoan);
        thoatCN = dialog.findViewById(R.id.imgThoatThemKhoan);
        soTienCN = dialog.findViewById(R.id.edtSoTien);
        chonLoaiCN = dialog.findViewById(R.id.txtChonLoai);
        chonNgayCN = dialog.findViewById(R.id.txtChonNgay);
        chonViCN = dialog.findViewById(R.id.edtChonVi);
        Button capNhat = dialog.findViewById(R.id.btnThemKhoan);
        maLoaiCapNhat = khoan.getMaLop();
        loaiCapNhat = loaiDAO.timTheoMa(maLoaiCapNhat);

        thoatCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tieuDeCN.setText("Cập nhật chi tiêu");
        capNhat.setText("CẬP NHẬT");

        soTienCN.setText((int)khoan.getSoTien()+"");
        chonLoaiCN.setText(loai.getTen());
        chonNgayCN.setText(khoan.getNgay());
        chonViCN.setText(khoan.getVi());

        chonLoaiCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChonLoaiActivity.class);
                startActivityForResult(intent, 444);
            }
        });

        chonNgayCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hienThiNgay(chonNgayCN);
            }
        });

        chonViCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogChonVi();
            }
        });

        capNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(soTienCN.getText().toString().isEmpty()){
                    dialogThongBao("Vui lòng nhập đủ thông tin!");
                }else{
                    khoanDAO.capNhat(
                            viTriData,
                            Double.parseDouble(soTienCN.getText().toString()),
                            chonNgayCN.getText().toString(),
                            chonViCN.getText().toString(),
                            maLoaiCapNhat
                    );
                    double tien = Double.parseDouble(soTienCN.getText().toString());
                    soTien.setText(dinhDangTien(tien));
                    ten.setText(loaiCapNhat.getTen());
                    ngay.setText(chonNgayCN.getText().toString());
                    vi.setText(chonViCN.getText().toString());
                    dialog.dismiss();

                    String tenAnh = loaiCapNhat.getAnh();
                    int imgID = getResources().getIdentifier(
                            tenAnh, "drawable", getPackageName()
                    );
                    try {
                        anh.setImageResource(imgID);
                    }catch (Exception e){
                        anh.setImageResource(R.drawable.load_loai_loi);
                    }

                    khoan.setSoTien(tien);
                    khoan.setNgay(ngay.getText().toString());
                    khoan.setVi(vi.getText().toString());
                    khoan.setMaLop(maLoaiCapNhat);
                    loai =loaiCapNhat;


                }
            }
        });

    }

    private void dialogChonVi() {
        Dialog dialog = new Dialog(ChiTietKhoanActivity.this);
        dialog.setContentView(R.layout.dialog_chonvi);
        dialog.show();
        ImageView thoat = dialog.findViewById(R.id.imgThoatChonVi);
        AppCompatRadioButton coBan, lienKet, tinDung, tietKiem;
        coBan = dialog.findViewById(R.id.rdoViCoBan);
        lienKet = dialog.findViewById(R.id.rdoViLienKet);
        tinDung = dialog.findViewById(R.id.rdoViTinDung);
        tietKiem = dialog.findViewById(R.id.rdoViTietKiem);
        Button chon = dialog.findViewById(R.id.btnChon);
        RadioGroup radioGroupVi = dialog.findViewById(R.id.rdoGroupVi);

        ArrayList<AppCompatRadioButton> listRdo = new ArrayList<>();
        listRdo.add(coBan);
        listRdo.add(lienKet);
        listRdo.add(tinDung);
        listRdo.add(tietKiem);

        for(int i=0; i<listRdo.size(); i++){
            if(chonViCN.getText().toString().equals(listRdo.get(i).getText().toString())){
                listRdo.get(i).setChecked(true);
            }
        }

        setMauSac(listRdo);

        radioGroupVi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                setMauSac(listRdo);
            }
        });

        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        chon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<listRdo.size(); i++){
                    if(listRdo.get(i).isChecked()) {
                        chonViCN.setText(listRdo.get(i).getText());
                        break;
                    }
                }
                dialog.dismiss();
            }
        });
    }

    private void setMauSac(ArrayList<AppCompatRadioButton> list) {
        for(int i=0; i<list.size(); i++){
            if(list.get(i).isChecked()){
                list.get(i).setTextColor(Color.parseColor("#00B359"));
            }else{
                list.get(i).setTextColor(Color.parseColor("#AAAAAA"));
            }
        }
    }

    private void hienThiNgay(TextView chonNgayCN) {
        DatePickerDialog chonNgay = new DatePickerDialog(
                ChiTietKhoanActivity.this,
                R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String ngay = i2 + "";
                        if(ngay.length()==1) ngay = "0" + ngay;

                        String thang = (i1 + 1) + "";
                        if(thang.length()==1) thang = "0" + thang;

                        chonNgayCN.setText(ngay +
                                "/" + thang +
                                "/" + i);
                    }
                },
                lich.get(Calendar.YEAR),
                lich.get(Calendar.MONTH),
                lich.get(Calendar.DAY_OF_MONTH)
        );
        chonNgay.show();
    }

    private void xoaKhoan() {
        Dialog dialog = new Dialog(ChiTietKhoanActivity.this);
        dialog.setContentView(R.layout.dialog_xoa);
        dialog.show();

        TextView noiDungXoa = dialog.findViewById(R.id.txtNoiDungTBXoa);
        Button xacNhan = dialog.findViewById(R.id.btnXacNhanXoa);
        Button huy = dialog.findViewById(R.id.btnHuyXoa);
        ImageView thoat = dialog.findViewById(R.id.imgThoatXoaQLKH);

        noiDungXoa.setText("Bạn chắc chắn muốn xóa danh mục này?");

        xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                khoanDAO.xoa(maKhoan);
                Intent intent = new Intent();
                intent.putExtra("boxao", true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChiTietKhoanActivity.this, "Thao tác bị hủy!", Toast.LENGTH_SHORT).show();
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

    private void dialogThongBao(String noiDung) {
        Dialog dialog = new Dialog(ChiTietKhoanActivity.this);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 444 && resultCode == Activity.RESULT_OK){
            Bundle bundleNhanMa = data.getBundleExtra("boxma");
            maLoaiCapNhat = bundleNhanMa.getInt("ma");
            loaiCapNhat = loaiDAO.timTheoMa(maLoaiCapNhat);
            chonLoaiCN.setText(loaiCapNhat.getTen());
        }
    }
}