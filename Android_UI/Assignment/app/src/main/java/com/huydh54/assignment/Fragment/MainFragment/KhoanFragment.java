package com.huydh54.assignment.Fragment.MainFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.huydh54.assignment.Activity.ChonLoaiActivity;
import com.huydh54.assignment.Activity.ThongKeActivity;
import com.huydh54.assignment.Adapter.KhoanAdapter;
import com.huydh54.assignment.Adapter.Loai2Adapter;
import com.huydh54.assignment.DAO.KhoanDAO;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.Khoan;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;
import com.huydh54.assignment.ViewPager.ViewPagerKhoan;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

public class KhoanFragment extends Fragment {

    TabLayout menuTab;
    ViewPager viewPager;
    ImageView thoat;
    FloatingActionButton fab;
    EditText soTien;
    TextView chonNgay, chonVi, chonLoai;
    Calendar lich = Calendar.getInstance();
    Button themKhoan, chon, thongKe;
    RadioGroup radioGroupVi;
    LoaiDAO loaiDAO;
    ArrayList<Loai> dsLoai;
    ArrayList<Khoan> dsKhoan;
    KhoanAdapter khoanThuAdapter, khoanChiAdapter;
    KhoanDAO khoanDAO;
    TextView soTienVao, soTienRa, soTienTong;
    int ma;
    Loai loaiChon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_khoang, container, false);

        menuTab = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.vpKhung);
        fab = view.findViewById(R.id.fab);
        loaiDAO = new LoaiDAO(getContext());
        dsLoai = new ArrayList<>();
        khoanDAO = new KhoanDAO(view.getContext());
        soTienVao = view.findViewById(R.id.txtSoTienVao);
        soTienRa = view.findViewById(R.id.txtSoTienRa);
        soTienTong = view.findViewById(R.id.txtSoTienTong);
        thongKe = view.findViewById(R.id.btnThongKe);

        thongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ThongKeActivity.class);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogThemLoai();
            }
        });

        ViewPagerKhoan viewPagerKC = new ViewPagerKhoan(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerKC);
        menuTab.setupWithViewPager(viewPager);
        menuTab.getTabAt(0).select();

        return view;
    }

    private void dialogThemLoai() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_themkhoan);
        dialog.show();
        thoat = dialog.findViewById(R.id.imgThoatThemKhoan);
        soTien = dialog.findViewById(R.id.edtSoTien);
        chonLoai = dialog.findViewById(R.id.txtChonLoai);
        chonNgay = dialog.findViewById(R.id.txtChonNgay);
        chonVi = dialog.findViewById(R.id.edtChonVi);
        themKhoan = dialog.findViewById(R.id.btnThemKhoan);

        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        chonLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChonLoaiActivity.class);
                startActivityForResult(intent, 123);
            }
        });

        String ngay = lich.get(Calendar.DAY_OF_MONTH) + "";
        if(ngay.length()==1) ngay = "0" + ngay;

        String thang = (lich.get(Calendar.MONTH)+1) + "";
        if(thang.length()==1) thang = "0" + thang;

        chonNgay.setText(ngay +
                "/" + thang +
                "/" + lich.get(Calendar.YEAR));
        chonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hienThiNgay(chonNgay);
            }
        });

        chonVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogChonVi();
            }
        });

        themKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(soTien.getText().toString().isEmpty() ||
                        chonLoai.getText().toString().equals("Chọn loại") ||
                        chonNgay.getText().toString().equals("Chọn ngày")){
                    dialogThongBao("Vui lòng nhập đủ thông tin!");
                }else {
                    khoanDAO.them(
                            Double.parseDouble(soTien.getText().toString()),
                            chonNgay.getText().toString(),
                            chonVi.getText().toString(),
                            ma
                    );
                    Toast.makeText(view.getContext(), "Thêm mới thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

//                    RecyclerView rvKhoanThu = viewPager.getChildAt(0).findViewById(R.id.rvKhoanThu);
//                    rvKhoanThu.getAdapter().notifyDataSetChanged();
//                    RecyclerView rvKhoanChi = viewPager.getChildAt(1).findViewById(R.id.rvKhoanThu);
//                    rvKhoanChi.getAdapter().notifyDataSetChanged();

                    double tongThu=0, tongChi=0;
                    dsKhoan = khoanDAO.doc();
                    ArrayList<Khoan> dsKhoanChi = new ArrayList<>();
                    ArrayList<Khoan> dsKhoanThu = new ArrayList<>();
                    ArrayList<Integer> dsViTriChi = new ArrayList<>();
                    ArrayList<Integer> dsViTriThu = new ArrayList<>();
                    for (int i=0; i<dsKhoan.size(); i++){
                        int maLop = dsKhoan.get(i).getMaLop();
                        Loai loai = loaiDAO.timTheoMa(maLop);
                        if(loai.getPhanLoai().equals("Loại chi")){
                            dsKhoanChi.add(dsKhoan.get(i));
                            dsViTriChi.add(i);
                            tongChi += dsKhoan.get(i).getSoTien();
                        }else if(loai.getPhanLoai().equals("Vay nợ") &&
                                (loai.getTen().equals("Cho vay") || loai.getTen().equals("Trả nợ"))){
                            dsKhoanChi.add(dsKhoan.get(i));
                            dsViTriChi.add(i);
                            tongChi += dsKhoan.get(i).getSoTien();
                        }
                        if(loai.getPhanLoai().equals("Loại thu")){
                            dsKhoanThu.add(dsKhoan.get(i));
                            dsViTriThu.add(i);
                            tongThu += dsKhoan.get(i).getSoTien();
                        }else if(loai.getPhanLoai().equals("Vay nợ") &&
                                (loai.getTen().equals("Đi vay") || loai.getTen().equals("Thu nợ"))){
                            dsKhoanThu.add(dsKhoan.get(i));
                            dsViTriThu.add(i);
                            tongThu += dsKhoan.get(i).getSoTien();
                        }
                    }
                    khoanThuAdapter = new KhoanAdapter(dsKhoanThu, getContext(), loaiDAO, dsViTriThu, khoanDAO);
                    khoanChiAdapter = new KhoanAdapter(dsKhoanChi, getContext(), loaiDAO, dsViTriChi, khoanDAO);
                    RecyclerView rvKhoanThu = viewPager.getChildAt(0).findViewById(R.id.rvKhoanThu);
                    rvKhoanThu.setAdapter(khoanThuAdapter);
                    RecyclerView rvKhoanChi = viewPager.getChildAt(1).findViewById(R.id.rvKhoanThu);
                    rvKhoanChi.setAdapter(khoanChiAdapter);

                    DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
                    decimalFormatSymbols.setDecimalSeparator('.');
                    decimalFormatSymbols.setGroupingSeparator(',');
                    String patternTienTe = "###,###,### ₫";
                    DecimalFormat formatTienTe = new DecimalFormat(patternTienTe, decimalFormatSymbols);

                    TextView txtTongThu = viewPager.getChildAt(0).findViewById(R.id.txtTong);
                    txtTongThu.setText(formatTienTe.format(tongThu));
                    soTienVao.setText(formatTienTe.format(tongThu));
                    TextView txtTongChi = viewPager.getChildAt(1).findViewById(R.id.txtTong);
                    txtTongChi.setText(formatTienTe.format(tongChi));
                    soTienRa.setText(formatTienTe.format(tongChi));
                    soTienTong.setText(formatTienTe.format(tongThu - tongChi));

                    Button buttonThu = viewPager.getChildAt(0).findViewById(R.id.btnThemKhoan);
                    if(dsKhoanThu.size()==0){
                        buttonThu.setVisibility(View.VISIBLE);
                    }else {
                        buttonThu.setVisibility(View.GONE);
                    }
                    Button buttonChi = viewPager.getChildAt(1).findViewById(R.id.btnThemKhoan);
                    if(dsKhoanChi.size()==0){
                        buttonChi.setVisibility(View.VISIBLE);
                    }else {
                        buttonChi.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void dialogChonVi() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_chonvi);
        dialog.show();
        thoat = dialog.findViewById(R.id.imgThoatChonVi);
        AppCompatRadioButton coBan, lienKet, tinDung, tietKiem;
        coBan = dialog.findViewById(R.id.rdoViCoBan);
        lienKet = dialog.findViewById(R.id.rdoViLienKet);
        tinDung = dialog.findViewById(R.id.rdoViTinDung);
        tietKiem = dialog.findViewById(R.id.rdoViTietKiem);
        chon = dialog.findViewById(R.id.btnChon);
        radioGroupVi = dialog.findViewById(R.id.rdoGroupVi);

        ArrayList<AppCompatRadioButton> listRdo = new ArrayList<>();
        listRdo.add(coBan);
        listRdo.add(lienKet);
        listRdo.add(tinDung);
        listRdo.add(tietKiem);

        for(int i=0; i<listRdo.size(); i++){
            if(chonVi.getText().toString().equals(listRdo.get(i).getText().toString())){
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
                        chonVi.setText(listRdo.get(i).getText());
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

    private void hienThiNgay(TextView textView) {
        DatePickerDialog chonNgay = new DatePickerDialog(
            getContext(),
                R.style.DialogTheme,
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    String ngay = i2 + "";
                    if(ngay.length()==1) ngay = "0" + ngay;

                    String thang = (i1 + 1) + "";
                    if(thang.length()==1) thang = "0" + thang;

                    textView.setText(ngay +
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            Bundle bundleNhanMa = data.getBundleExtra("boxma");
            ma = bundleNhanMa.getInt("ma");

            dsLoai = loaiDAO.doc();
            for (int i=0; i<dsLoai.size(); i++){
                if(loaiDAO.getMa(i)==ma){
                    loaiChon = dsLoai.get(i);
                }
            }
            if(loaiChon.getPhanLoai().equals("Loại thu")){
                chonLoai.setText(loaiChon.getTen()+" (Thu)");
            }else if(loaiChon.getPhanLoai().equals("Loại chi")){
                chonLoai.setText(loaiChon.getTen()+" (Chi)");
            }else{
                chonLoai.setText(loaiChon.getTen()+" (Vay)");
            }
        }
    }
}