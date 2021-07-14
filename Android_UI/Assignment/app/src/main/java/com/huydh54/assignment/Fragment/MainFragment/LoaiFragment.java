package com.huydh54.assignment.Fragment.MainFragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayout;
import com.huydh54.assignment.Activity.ThuVien;
import com.huydh54.assignment.Adapter.LoaiAdapter;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;
import com.huydh54.assignment.ViewPager.ViewPagerLoai;

import java.util.ArrayList;

public class LoaiFragment extends Fragment {

    TabLayout menuTab;
    ViewPager viewPager;
    ImageView thoat, khungThemLoai;
    AppCompatRadioButton rdbThu, rdbChi;
    RadioGroup radioGroup;
    FloatingActionButton fab;
    String tenAnh = "", tenAnhGui = "";
    Button nutThemLoai;
    EditText nhapTenLoai;
    LoaiDAO loaiDAO;
    int viTriLoai = -1;
    ArrayList<Loai> dsLoai;
    LoaiAdapter loaiThuAdapter, loaiChiAdapter;
    ViewPagerLoai viewPagerKT;
    AdView adView;
    String tenCapNhat2;
    boolean checkRadio2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_khoang_2, container, false);

        menuTab = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.vpKhung);
        fab = view.findViewById(R.id.fab);
        loaiDAO = new LoaiDAO(view.getContext());
        dsLoai = new ArrayList<>();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogThemLoai();
            }
        });

        viewPagerKT = new ViewPagerLoai(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerKT);
        menuTab.setupWithViewPager(viewPager);
        menuTab.getTabAt(0).select();
        return view;
    }

    private void dialogThemLoai() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_themloai);
        dialog.show();

        thoat = dialog.findViewById(R.id.imgThoatThemSV);
        rdbThu = dialog.findViewById(R.id.rdoThu);
        rdbChi = dialog.findViewById(R.id.rdoChi);
        khungThemLoai = dialog.findViewById(R.id.imgThemLoai);
        nutThemLoai = dialog.findViewById(R.id.btnThemLoai);
        nhapTenLoai = dialog.findViewById(R.id.edtTenLoai);

        rdbThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColorRadioButton();
            }
        });
        rdbChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColorRadioButton();
            }
        });

        khungThemLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ThuVien.class);
                Bundle bundleTrangThai = new Bundle();
                bundleTrangThai.putString("tencapnhat", nhapTenLoai.getText().toString());
                boolean checkRadio;
                if(rdbThu.isChecked()==true) checkRadio = true;
                else checkRadio = false;
                bundleTrangThai.putBoolean("checkradio", checkRadio);
                tenAnhGui = nhapTenLoai.getText().toString();
                bundleTrangThai.putString("tenanhgui", tenAnhGui);
                startActivityForResult(intent, 999);
            }
        });

        nutThemLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tenAnh.isEmpty() ||
                        nhapTenLoai.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    String phanLoai = "";
                    if(rdbThu.isChecked()){
                        phanLoai = "Loại thu";
                    }else {
                        phanLoai = "Loại chi";
                    }
                    loaiDAO.them(
                            tenAnh,
                            nhapTenLoai.getText().toString(),
                            phanLoai
                    );

                    Toast.makeText(getContext(), "Thêm mới thành công!", Toast.LENGTH_SHORT).show();

                    khungThemLoai.setImageResource(R.drawable.load_loai_chamhoi);
//                    tenAnh = "";
                    nhapTenLoai.setText("");
                    rdbThu.setChecked(true);

                    dsLoai = loaiDAO.doc();
                    ArrayList<Loai> dsLoaiThu = new ArrayList<>();
                    ArrayList<Integer> dsViTriThu = new ArrayList<Integer>();
                    ArrayList<Loai> dsLoaiChi = new ArrayList<>();
                    ArrayList<Integer> dsViTriChi = new ArrayList<Integer>();
                    for(int i=0; i<dsLoai.size(); i++){
                        if(dsLoai.get(i).getPhanLoai().equals("Loại thu")){
                            dsLoaiThu.add(dsLoai.get(i));
                            dsViTriThu.add(i);
                        }else{
                            dsLoaiChi.add(dsLoai.get(i));
                            dsViTriChi.add(i);
                        }
                    }

                    loaiThuAdapter = new LoaiAdapter(getContext(), dsLoaiThu, dsViTriThu, viewPager);
                    loaiChiAdapter = new LoaiAdapter(getContext(), dsLoaiChi, dsViTriChi, viewPager);
                    ListView lvLoaiThu = viewPager.getChildAt(0).findViewById(R.id.lvLoaiThu);
                    lvLoaiThu.setAdapter(loaiThuAdapter);
                    ListView lvLoaiChi = viewPager.getChildAt(1).findViewById(R.id.lvLoaiThu);
                    lvLoaiChi.setAdapter(loaiChiAdapter);

                    setColorRadioButton();
                }
            }
        });

        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void setColorRadioButton() {
        if(rdbChi.isChecked()){
            rdbChi.setTextColor(Color.WHITE);
            rdbThu.setTextColor(Color.GRAY);
        }else{
            rdbChi.setTextColor(Color.GRAY);
            rdbThu.setTextColor(Color.WHITE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 999 && resultCode == Activity.RESULT_OK){
            Bundle bundleNhanAnh = data.getBundleExtra("boxanh");
            tenAnh = bundleNhanAnh.getString("tenanh");
            tenCapNhat2 = bundleNhanAnh.getString("tencapnhat2");
            checkRadio2 = bundleNhanAnh.getBoolean("checkradio2");

            int imgID = getActivity().getResources().getIdentifier(
                    tenAnh, "drawable", getActivity().getPackageName()
            );
            try {
                khungThemLoai.setImageResource(imgID);
            }catch (Exception e){
                khungThemLoai.setImageResource(R.drawable.load_loai_chamhoi);
            }

            nhapTenLoai.setText(tenCapNhat2);
            if (checkRadio2==true){
                rdbThu.setChecked(true);
            }else{
                rdbChi.setChecked(true);
            }

        }
    }
}