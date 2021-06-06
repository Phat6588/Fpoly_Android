package com.huydh54.fpolyapp.Fragment.TrangMain;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.huydh54.fpolyapp.Activity.GhiChuActivity;
import com.huydh54.fpolyapp.Activity.MainActivity;
import com.huydh54.fpolyapp.Activity.QuanLyKhoaHoc;
import com.huydh54.fpolyapp.Activity.QuanLyLop;
import com.huydh54.fpolyapp.Activity.QuanLySinhVien;
import com.huydh54.fpolyapp.Activity.QuanLyTaiKhoan;
import com.huydh54.fpolyapp.Activity.TrangDangNhap;
import com.huydh54.fpolyapp.Activity.TrinhDuyetActivity;
import com.huydh54.fpolyapp.Adapter.CaiDatAdapter;
import com.huydh54.fpolyapp.DAO.SinhVienDAO;
import com.huydh54.fpolyapp.Model.CaiDat;
import com.huydh54.fpolyapp.Model.SinhVien;
import com.huydh54.fpolyapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;

public class TaiKhoanFragment extends Fragment {

    ListView dsCaiDat;
    CaiDatAdapter caiDatAdapter;
    ImageView avatar;
    TextView hoTen, mSSV, lop, khoa;
    String maSV = "???????";
    SinhVienDAO sinhVienDAO;
    ArrayList<SinhVien> dsSinhVien = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);

        dsCaiDat = (ListView) view.findViewById(R.id.lvCaiDat);
        hoTen = view.findViewById(R.id.imgTenSV);
        mSSV = view.findViewById(R.id.txtMSSV);
        lop = view.findViewById(R.id.txtLop);
        khoa = view.findViewById(R.id.txtKhoa);
        avatar = view.findViewById(R.id.imgAvatar);
        sinhVienDAO = new SinhVienDAO(getContext());
        dsSinhVien = sinhVienDAO.doc();

        setTaiKhoan();

        ArrayList<CaiDat> caiDatList = new ArrayList<>();
        caiDatList.add(new CaiDat(R.drawable.ic_taikhoan_chinhsua, "Chỉnh sửa trang cá nhân"));
        caiDatList.add(new CaiDat(R.drawable.ic_taikhoan_huongdan, "Hướng dẫn sử dụng"));
        caiDatList.add(new CaiDat(R.drawable.ic_taikhoan_hotro, "Trung tâm hỗ trợ"));
        caiDatList.add(new CaiDat(R.drawable.ic_taikhoan_phanhoi, "Phản hồi về ứng dụng"));
        caiDatList.add(new CaiDat(R.drawable.ic_taikhoan_ghichu, "Ghi chú"));
        caiDatList.add(new CaiDat(R.drawable.ic_taikhoan_trinhduyet, "Trình duyệt Web"));
        if(maSV.equalsIgnoreCase("admin")) {
            caiDatList.add(new CaiDat(R.drawable.ic_taikhoan_danhsach, "Quản lý lớp học"));
            caiDatList.add(new CaiDat(R.drawable.ic_taikhoan_sinhvien, "Quản lý sinh viên"));
            caiDatList.add(new CaiDat(R.drawable.ic_chucnang_diemthi, "Quản lý khóa học"));
            caiDatList.add(new CaiDat(R.drawable.ic_qltaikhoan_user, "Quản lý tài khoản"));
        }
        caiDatList.add(new CaiDat(R.drawable.ic_taikhoan_dangxuat, "Đăng xuất"));

        caiDatAdapter = new CaiDatAdapter(view.getContext(), caiDatList);
        dsCaiDat.setAdapter(caiDatAdapter);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions();
            }
        });

        dsCaiDat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 4) {
                    Intent intent = new Intent(view.getContext(), GhiChuActivity.class);
                    startActivityForResult(intent, 004);
                }
                if (i == 5) {
                    Intent intent = new Intent(view.getContext(), TrinhDuyetActivity.class);
                    startActivityForResult(intent, 005);
                }
                if (i == 6 && maSV.equalsIgnoreCase("admin")) {
                    Intent intent = new Intent(view.getContext(), QuanLyLop.class);
                    startActivityForResult(intent, 005);
                }
                if (i == 7 && maSV.equalsIgnoreCase("admin")) {
                    Intent intent = new Intent(view.getContext(), QuanLySinhVien.class);
                    startActivityForResult(intent, 006);
                }
                if (i == 8 && maSV.equalsIgnoreCase("admin")) {
                    Intent intent = new Intent(view.getContext(), QuanLyKhoaHoc.class);
                    startActivityForResult(intent, 007);
                }
                if (i == 9 && maSV.equalsIgnoreCase("admin")) {
                    Intent intent = new Intent(view.getContext(), QuanLyTaiKhoan.class);
                    startActivity(intent);
                }
                if ((i == 10 && maSV.equalsIgnoreCase("admin")) ||
                        (i == 6 && maSV.equalsIgnoreCase("admin")==false)) {
                    getActivity().finish();
                    SharedPreferences sp = getContext().getSharedPreferences("luudangnhap.txt", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    if(sp.getInt("check", -1)==1) {
                        editor.putInt("check", 0);
                    }
                    editor.commit();
                    Intent intent = new Intent(view.getContext(), TrangDangNhap.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    private void requestPermissions() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImagePicker();
            }
            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(getContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void openImagePicker() {
        TedBottomPicker.OnImageSelectedListener listener = new TedBottomPicker.OnImageSelectedListener() {
            @Override
            public void onImageSelected(Uri uri) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                    avatar.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(getContext()).setOnImageSelectedListener(listener).create();
        tedBottomPicker.show(getActivity().getSupportFragmentManager());
    }

    public void setTaiKhoan(){
        SharedPreferences sp = getContext().getSharedPreferences("luuma.txt", Context.MODE_PRIVATE);
        maSV = sp.getString("maluu", "???????");
        if(maSV!="admin") {
            for (int i = 0; i < dsSinhVien.size(); i++) {
                if (maSV.equalsIgnoreCase(dsSinhVien.get(i).getMa())) {
                    hoTen.setText(dsSinhVien.get(i).getTen());
                    mSSV.setText(maSV);
                    lop.setText(dsSinhVien.get(i).getLop());
                    khoa.setText(dsSinhVien.get(i).getLop().substring(2, 4) + "." + dsSinhVien.get(i).getLop().substring(4, 5));
                    int imgID = getContext().getResources().getIdentifier(
                            dsSinhVien.get(i).getAnh(), "drawable", getContext().getPackageName()
                    );
                    avatar.setImageResource(imgID);
                    break;
                }
            }
        }
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