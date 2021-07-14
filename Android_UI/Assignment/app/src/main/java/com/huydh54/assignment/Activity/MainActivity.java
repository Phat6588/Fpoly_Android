package com.huydh54.assignment.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.navigation.NavigationView;
import com.huydh54.assignment.Adapter.CaiDatAdapter;
import com.huydh54.assignment.Adapter.KhoanAdapter;
import com.huydh54.assignment.Adapter.Loai2Adapter;
import com.huydh54.assignment.Adapter.TienTeAdapter;
import com.huydh54.assignment.DAO.KhoanDAO;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.CaiDat;
import com.huydh54.assignment.Model.Khoan;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.Model.TienTe;
import com.huydh54.assignment.R;
import com.huydh54.assignment.ViewPager.CustomViewPager;
import com.huydh54.assignment.ViewPager.ViewPagerMain;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    Toolbar header;
    String bang = "";
    DrawerLayout toanBo;
    NavigationView khungAn;
    ListView menu;
    CaiDatAdapter caiDatAdapter;
    CustomViewPager khungMain;
    Calendar lich = Calendar.getInstance();
    TienTeAdapter tienTeAdapter;
    ArrayList<CaiDat> caiDatList;
    int viTriLoai = -1;
    String tenAnh = "";
    ArrayList<Loai> dsLoai;
    LoaiDAO loaiDAO;
    KhoanDAO khoanDAO;
    ArrayList<Khoan> dsKhoan;
    LinearLayout layoutMain;
    TextView soTienVao, soTienRa, soTienTong;

    String tenCapNhat2;
    boolean checkRadio2;
    AppCompatRadioButton rdbThu;
    AppCompatRadioButton rdbChi;
    EditText ten;
    ImageView khungAnh;

    private ImageView avatar;
    private TextView name;
    private TextView email;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    String maTaiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        header = findViewById(R.id.toolBar);
        toanBo = findViewById(R.id.drawerLayout);
        khungAn = findViewById(R.id.khungAn);
        menu = findViewById(R.id.lvMenu);
        khungAn = findViewById(R.id.khungAn);
        khungMain = findViewById(R.id.vpKhungMain);
        loaiDAO = new LoaiDAO(MainActivity.this);
        khoanDAO = new KhoanDAO(MainActivity.this);
        layoutMain = findViewById(R.id.layoutMain);

        avatar = findViewById(R.id.imgAvatar);
        name = findViewById(R.id.txtHoTen);
        email = findViewById(R.id.txtEmail);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        khungAn.setPadding(0, getStatusBarHeight(), 0, 0);

        batMenu();

        caiDatList = new ArrayList<>();
        caiDatList.add(new CaiDat(R.drawable.load_nav_loai, "Phân loại"));
        caiDatList.add(new CaiDat(R.drawable.load_nav_khoan, "Lịch sử chi tiêu"));
        caiDatList.add(new CaiDat(R.drawable.load_nav_thongke, "Thống kê"));

        caiDatList.add(new CaiDat(R.drawable.load_nav_vicuatoi, "Ví của tôi"));
        caiDatList.add(new CaiDat(R.drawable.load_nav_nhom, "Chuyên viên"));
        caiDatList.add(new CaiDat(R.drawable.load_nav_sono, "Sổ nợ"));
        caiDatList.add(new CaiDat(R.drawable.load_nav_maytinh, "Tính lãi vay"));
        caiDatList.add(new CaiDat(R.drawable.load_nav_thoigian, "Thời gian"));
        caiDatList.add(new CaiDat(R.drawable.load_nav_caidat, "Cài đặt"));
        caiDatList.add(new CaiDat(R.drawable.load_flag_vietnam, "Đơn vị tiền tệ"));

        caiDatList.add(new CaiDat(R.drawable.load_nav_gioithieu, "Giới thiệu"));
        caiDatList.add(new CaiDat(R.drawable.load_nav_dangxuat, "Đăng xuất"));

        caiDatAdapter = new CaiDatAdapter(MainActivity.this, caiDatList);
        menu.setAdapter(caiDatAdapter);

        ViewPagerMain viewPagerTrangChu = new ViewPagerMain(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        khungMain.setAdapter(viewPagerTrangChu);
        khungMain.setPagingEnabled(false);


        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        khungMain.setCurrentItem(0, false);
                        break;
                    case 1:
                        khungMain.setCurrentItem(1, false);
                        break;
                    case 2:
                        Intent intent = new Intent(MainActivity.this, ThongKeActivity.class);
                        startActivity(intent);
                        break;
                    case 8:
                        caiDat();
                        break;
                    case 9:
                        dialogTienTe();
                        break;
                    case 10:
                        khungMain.setCurrentItem(2, false);
                        break;
                    case 7:
                        openTimePickerDialog();
                        break;
                    case 11:
                        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                if(status.isSuccess()){
                                    gotoDangNhap();
                                }else{
                                    Toast.makeText(MainActivity.this, "Đăng xuất thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                }
                toanBo.closeDrawers();
            }
        });

//        CountDownTimer timer = new CountDownTimer(5000, 1000) {
//            @Override
//            public void onTick(long l) {
//
//            }
//
//            @Override
//            public void onFinish() {
//                Snackbar snackbar = Snackbar.make(layoutMain, "", BaseTransientBottomBar.LENGTH_INDEFINITE);
//                View custom = getLayoutInflater().inflate(R.layout.snackbar_custom, null);
//                snackbar.getView().setBackgroundColor(Color.parseColor("#B3000000"));
//                Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
//                snackbarLayout.setPadding(0, 0, 0, 0);
//                custom.findViewById(R.id.btnDownloadQC).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(MainActivity.this, "Chuyển trang", Toast.LENGTH_SHORT).show();
//                        snackbar.dismiss();
//                    }
//                });
//                snackbarLayout.addView(custom, 0);
//                snackbar.show();
//            }
//        }.start();

//        khungMain.setOnTouchListener(new View.OnTouchListener()
//        {
//            @Override
//            public boolean onTouch(View v, MotionEvent event)
//            {
//                return true;
//            }
//        });

    }

    private void caiDat() {
        Intent intent = new Intent(MainActivity.this, TruotActivity.class);
        startActivity(intent);
    }

    private void windowPhone() {
        Intent intent = new Intent(MainActivity.this, GuidelineActivity.class);
        startActivity(intent);
    }

    private void dialogTienTe() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_tiente);
        dialog.show();
        ImageView thoat = dialog.findViewById(R.id.imgThoatChonTT);
        ListView dsTienTe = dialog.findViewById(R.id.lvTienTe);

        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ArrayList<TienTe> tienTeList = new ArrayList<>();
        tienTeList.add(new TienTe(R.drawable.load_flag_vietnam, "Việt Nam Đồng", "₫"));
        tienTeList.add(new TienTe(R.drawable.load_flag_my, "United State Dollar", "$"));
        tienTeList.add(new TienTe(R.drawable.load_flag_euro, "Euro", "€"));
        tienTeList.add(new TienTe(R.drawable.load_flag_china, "Yuan Renminbi", "¥"));
        tienTeList.add(new TienTe(R.drawable.load_flag_japan, "Yen", "¥"));
        tienTeList.add(new TienTe(R.drawable.load_flag_korea, "Won", "₩"));

        tienTeAdapter = new TienTeAdapter(MainActivity.this, tienTeList);
        dsTienTe.setAdapter(tienTeAdapter);

        dsTienTe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CaiDat caiDat = new CaiDat(tienTeList.get(i).getAnh(), "Đơn vị tiền tệ");
                caiDatList.set(9, caiDat);
                caiDatAdapter = new CaiDatAdapter(MainActivity.this, caiDatList);
                menu.setAdapter(caiDatAdapter);
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Tiền tệ hiện tại: " + tienTeList.get(i).getTen(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openTimePickerDialog() {
        TimePickerDialog chonGio = new TimePickerDialog(
            MainActivity.this, R.style.DialogTheme,
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    String phut = i1 + "";
                    if(phut.length()==1) phut = "0" + phut;
                    if(i>12){
                        int h = i - 12;
                        String gio = h + "";
                        if(gio.length()==1) gio = "0" + gio;
                        Toast.makeText(MainActivity.this, gio + ":" + phut + " CH", Toast.LENGTH_SHORT).show();
                    }else{
                        String gio = i + "";
                        if(gio.length()==1) gio = "0" + gio;
                        Toast.makeText(MainActivity.this, gio + ":" + phut + " SA", Toast.LENGTH_SHORT).show();
                    }
                }
            },
            lich.get(Calendar.HOUR_OF_DAY),
            lich.get(Calendar.MINUTE),
            false
        );
        chonGio.show();
    }

    private void batMenu() {
        setSupportActionBar(header);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        header.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toanBo.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_goc, menu);
        return true;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void dialogCapNhat() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_themloai);
        dialog.show();
        TextView tieuDe = dialog.findViewById(R.id.txtTieuDeThemSV);
        ImageView thoat = dialog.findViewById(R.id.imgThoatThemSV);
        Button capNhat = dialog.findViewById(R.id.btnThemLoai);
        khungAnh = dialog.findViewById(R.id.imgThemLoai);
        rdbThu = dialog.findViewById(R.id.rdoThu);
        rdbChi = dialog.findViewById(R.id.rdoChi);
        ten = dialog.findViewById(R.id.edtTenLoai);
        RadioGroup radioGroup = dialog.findViewById(R.id.rdoGroup);
        dsLoai = new ArrayList<>();
        dsLoai = loaiDAO.doc();

        tieuDe.setText("Cập nhật loại chi tiêu");
        int imgID = MainActivity.this.getResources().getIdentifier(
                tenAnh, "drawable", MainActivity.this.getPackageName()
        );
        try {
            khungAnh.setImageResource(imgID);
        }catch (Exception e){
            khungAnh.setImageResource(R.drawable.load_loai_chamhoi);
        }
        ten.setText(dsLoai.get(viTriLoai).getTen());
        if(dsLoai.get(viTriLoai).getPhanLoai().equals("Loại thu")){
            rdbThu.setChecked(true);
        }else{
            rdbChi.setChecked(true);
        }
        if(rdbChi.isChecked()){
            rdbChi.setTextColor(Color.WHITE);
            rdbThu.setTextColor(Color.GRAY);
        }else{
            rdbChi.setTextColor(Color.GRAY);
            rdbThu.setTextColor(Color.WHITE);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(rdbChi.isChecked()){
                    rdbChi.setTextColor(Color.WHITE);
                    rdbThu.setTextColor(Color.GRAY);
                }else{
                    rdbChi.setTextColor(Color.GRAY);
                    rdbThu.setTextColor(Color.WHITE);
                }
            }
        });
        capNhat.setText("Cập nhật");

        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        capNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ten.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    String stringPhanLoai = "";
                    String phanLoaiGoc = dsLoai.get(viTriLoai).getPhanLoai();
                    if(rdbThu.isChecked()) stringPhanLoai = "Loại thu";
                    else stringPhanLoai = "Loại chi";
                    loaiDAO.capNhat(
                            viTriLoai,
                            tenAnh,
                            ten.getText().toString(),
                            stringPhanLoai
                    );
                    dsLoai.clear();
                    ArrayList<Loai> dsTong = loaiDAO.doc();
                    ArrayList<Integer> dsViTri = new ArrayList<>();
                    ArrayList<Loai> dsLoai2 = new ArrayList<>();
                    ArrayList<Integer> dsViTri2 = new ArrayList<>();
                    for(int i=0; i<dsTong.size(); i++){
                        if(dsTong.get(i).getPhanLoai().equals("Loại thu")) {
                            dsLoai.add(dsTong.get(i));
                            dsViTri.add(i);
                        }else if(dsTong.get(i).getPhanLoai().equals("Loại chi")){
                            dsLoai2.add(dsTong.get(i));
                            dsViTri2.add(i);
                        }
                    }
//                    int viTri = -1, viTri2 = -1;
//                    if(phanLoaiGoc.equals("Loại thu"))viTri = 0;
//                    else viTri = 1;/////////////
//                    if(viTri == 0) viTri2 = 1;
//                    else viTri2 = 0;

                    ViewPager viewPager = findViewById(R.id.vpKhung);
                    RecyclerView rvLoai = viewPager.getChildAt(0).findViewById(R.id.rvLoaiThu);
                    Loai2Adapter loai2Adapter = new Loai2Adapter(dsLoai, MainActivity.this, dsViTri, viewPager);
                    RecyclerView rvLoai2 = viewPager.getChildAt(1).findViewById(R.id.rvLoaiThu);
                    Loai2Adapter loai2Adapter2 = new Loai2Adapter(dsLoai2, MainActivity.this, dsViTri2, viewPager);
                    rvLoai.setAdapter(loai2Adapter);
                    rvLoai2.setAdapter(loai2Adapter2);
                    Toast.makeText(view.getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    viTriLoai = -1;
                    tenAnh = "";
//                    viTri = -1;
//                    viTri2 = -1;

                    dialog.dismiss();
                }
            }
        });

        khungAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ThuVien.class);
                Bundle bundleLoai = new Bundle();
                bundleLoai.putInt("vitriloai", viTriLoai);
                intent.putExtra("boxloai", bundleLoai);
                boolean checkRadio;
                if(rdbThu.isChecked()==true) checkRadio = true;
                else checkRadio = false;
                bundleLoai.putBoolean("checkradio", checkRadio);
                String tenLoaiGui, tenAnhGui;
                tenLoaiGui = ten.getText().toString();
                bundleLoai.putString("tencapnhat", tenLoaiGui);
                bundleLoai.putString("tenanhgui", tenAnh);
                startActivityForResult(intent, 888);
                dialog.dismiss();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 888 && resultCode == Activity.RESULT_OK){
            Bundle bundleNhanAnh = data.getBundleExtra("boxanh");
            tenAnh = bundleNhanAnh.getString("tenanh");
            viTriLoai = bundleNhanAnh.getInt("vitriloai2");

            tenCapNhat2 = bundleNhanAnh.getString("tencapnhat2");
            checkRadio2 = bundleNhanAnh.getBoolean("checkradio2");

            dialogCapNhat();

            ten.setText(tenCapNhat2);
            if (checkRadio2==true){
                rdbThu.setChecked(true);
            }else{
                rdbChi.setChecked(true);
            }
        }
        if(requestCode == 888 && resultCode== Activity.RESULT_CANCELED){
            Bundle bundleNhanAnh = data.getBundleExtra("boxanh");
            viTriLoai = bundleNhanAnh.getInt("vitriloai2");
            tenAnh = bundleNhanAnh.getString("tenanh");
            tenCapNhat2 = bundleNhanAnh.getString("tencapnhat2");
            checkRadio2 = bundleNhanAnh.getBoolean("checkradio2");
            dialogCapNhat();

            int imgID = MainActivity.this.getResources().getIdentifier(
                    tenAnh, "drawable",MainActivity.this.getPackageName()
            );
            try {
                khungAnh.setImageResource(imgID);
            }catch (Exception e){
                khungAnh.setImageResource(R.drawable.load_loai_chamhoi);
            }

            ten.setText(tenCapNhat2);
            if (checkRadio2==true){
                rdbThu.setChecked(true);
            }else{
                rdbChi.setChecked(true);
            }
        }

        if(requestCode == 222){
            Bundle bundleAo = data.getBundleExtra("boxao");

            dsKhoan = khoanDAO.doc();
            ArrayList<Khoan> dsKhoanThu = new ArrayList<>();
            ArrayList<Integer> dsViTriThu = new ArrayList<>();
            ArrayList<Khoan> dsKhoanChi = new ArrayList<>();
            ArrayList<Integer> dsViTriChi = new ArrayList<>();
            double tongThu = 0, tongChi = 0;
            for (int i = 0; i < dsKhoan.size(); i++) {
                Loai loai = loaiDAO.timTheoMa(dsKhoan.get(i).getMaLop());
                if (loai.getPhanLoai().equals("Loại thu") ||
                        (loai.getPhanLoai().equals("Vay nợ") &&
                                (loai.getTen().equals("Đi vay") || loai.getTen().equals("Thu nợ")))) {
                    dsKhoanThu.add(dsKhoan.get(i));
                    dsViTriThu.add(i);
                    tongThu += dsKhoan.get(i).getSoTien();
                } else {
                    dsKhoanChi.add(dsKhoan.get(i));
                    dsViTriChi.add(i);
                    tongChi += dsKhoan.get(i).getSoTien();
                }
            }
            ViewPager viewPager = khungMain.getChildAt(1).findViewById(R.id.vpKhung);
            RecyclerView rvKhoanThu = viewPager.getChildAt(0).findViewById(R.id.rvKhoanThu);
            KhoanAdapter khoanAdapterThu = new KhoanAdapter(dsKhoanThu, MainActivity.this, loaiDAO, dsViTriThu, khoanDAO);
            rvKhoanThu.setAdapter(khoanAdapterThu);
            RecyclerView rvKhoanChi = viewPager.getChildAt(1).findViewById(R.id.rvKhoanThu);
            KhoanAdapter khoanAdapterChi = new KhoanAdapter(dsKhoanChi, MainActivity.this, loaiDAO, dsViTriChi, khoanDAO);
            rvKhoanChi.setAdapter(khoanAdapterChi);
            Button buttonThu = viewPager.getChildAt(0).findViewById(R.id.btnThemKhoan);
            if (dsKhoanThu.size() == 0) {
                buttonThu.setVisibility(View.VISIBLE);
            } else {
                buttonThu.setVisibility(View.GONE);
            }
            Button buttonChi = viewPager.getChildAt(1).findViewById(R.id.btnThemKhoan);
            if (dsKhoanChi.size() == 0) {
                buttonChi.setVisibility(View.VISIBLE);
            } else {
                buttonChi.setVisibility(View.GONE);
            }

            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
            decimalFormatSymbols.setDecimalSeparator('.');
            decimalFormatSymbols.setGroupingSeparator(',');
            String patternTienTe = "###,###,### ₫";
            DecimalFormat formatTienTe = new DecimalFormat(patternTienTe, decimalFormatSymbols);
            TextView txtTongThu = viewPager.getChildAt(0).findViewById(R.id.txtTong);
            txtTongThu.setText(formatTienTe.format(tongThu));
            TextView txtTongChi = viewPager.getChildAt(1).findViewById(R.id.txtTong);
            txtTongChi.setText(formatTienTe.format(tongChi));

            TextView soTienVao = khungMain.getChildAt(1).findViewById(R.id.txtSoTienVao);
            TextView soTienRa = khungMain.getChildAt(1).findViewById(R.id.txtSoTienRa);
            TextView soTienTong = khungMain.getChildAt(1).findViewById(R.id.txtSoTienTong);
            soTienVao.setText(formatTienTe.format(tongThu));
            soTienRa.setText(formatTienTe.format(tongChi));

            double tienVao = chuyenChuThanhSo(soTienVao.getText().toString());
            double tienRa = chuyenChuThanhSo(soTienRa.getText().toString());
            double tienTong = tienVao - tienRa;
            soTienTong.setText(formatTienTe.format(tienTong));
        }
    }

    private double chuyenChuThanhSo(String stringTienTe) {
        String[] daySo = stringTienTe.substring(0, stringTienTe.length()-2).split("");
        String soNoi = "";
        for(int i=0; i<daySo.length; i++){
            if(daySo[i].equals(",")){
                continue;
            }else{
                soNoi+=daySo[i];
            }
        }
        return Double.parseDouble(soNoi);
    }

    private void gotoDangNhap() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String newName = account.getDisplayName();
            if(newName.length()>17){
                newName = newName.substring(0, 18) + "...";
            }
            name.setText(newName);
            email.setText(account.getEmail());
            Picasso.get().load(account.getPhotoUrl()).placeholder(R.mipmap.ic_launcher).into(avatar);
//            String id_token = account.getIdToken();
//            saveLoginState(id_token);
        }else{
            gotoDangNhap();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    handleSignInResult(result);
                }
            });
        }
    }
}