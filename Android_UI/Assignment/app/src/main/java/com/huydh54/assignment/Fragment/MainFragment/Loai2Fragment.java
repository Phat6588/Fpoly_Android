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
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayout;
import com.huydh54.assignment.Activity.ThuVien;
import com.huydh54.assignment.Adapter.Loai2Adapter;
import com.huydh54.assignment.Adapter.LoaiAdapter;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;
import com.huydh54.assignment.ViewPager.ViewPagerLoai;

import java.util.ArrayList;

public class Loai2Fragment extends Fragment {

    TabLayout menuTab;
    ViewPager viewPager;
    ImageView thoat, khungThemLoai;
    AppCompatRadioButton rdbThu, rdbChi;
    RadioGroup radioGroup;
    FloatingActionButton fab;
    String tenAnh = "";
    Button nutThemLoai;
    EditText nhapTenLoai;
    LoaiDAO loaiDAO;
    int viTriLoai = -1;
    ArrayList<Loai> dsLoai;
    Loai2Adapter loaiThuAdapter, loaiChiAdapter;
    ViewPagerLoai viewPagerKT;
    AdView adView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_khoang_2, container, false);
//        MobileAds.initialize(view.getContext());

        menuTab = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.vpKhung);
        fab = view.findViewById(R.id.fab);
        loaiDAO = new LoaiDAO(view.getContext());
        dsLoai = new ArrayList<>();
//        adView = view.findViewById(R.id.adView);

//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

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
                        if(dsLoai.get(i).getPhanLoai().equals("Vay nợ")){
                            continue;
                        }else if(dsLoai.get(i).getPhanLoai().equals("Loại thu")){
                            dsLoaiThu.add(dsLoai.get(i));
                            dsViTriThu.add(i);
                        }else{
                            dsLoaiChi.add(dsLoai.get(i));
                            dsViTriChi.add(i);
                        }
                    }

                    loaiThuAdapter = new Loai2Adapter(dsLoaiThu, getContext(), dsViTriThu, viewPager);
                    loaiChiAdapter = new Loai2Adapter(dsLoaiChi, getContext(), dsViTriChi, viewPager);
                    RecyclerView rvLoaiThu = viewPager.getChildAt(0).findViewById(R.id.rvLoaiThu);
                    rvLoaiThu.setAdapter(loaiThuAdapter);
                    RecyclerView rvLoaiChi = viewPager.getChildAt(1).findViewById(R.id.rvLoaiThu);
                    rvLoaiChi.setAdapter(loaiChiAdapter);

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

            int imgID = getActivity().getResources().getIdentifier(
                    tenAnh, "drawable", getActivity().getPackageName()
            );
            try {
                khungThemLoai.setImageResource(imgID);
            }catch (Exception e){
                khungThemLoai.setImageResource(R.drawable.load_loai_chamhoi);
            }
        }
//        if(requestCode == 888 && resultCode == Activity.RESULT_OK){
//            Bundle bundleNhanAnh = data.getBundleExtra("boxanh");
//            tenAnh = bundleNhanAnh.getString("tenanh");
//            viTriLoai = bundleNhanAnh.getInt("vitriloai2");
//            Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
//            dialogCapNhat();
//        }
//        if(requestCode == 888 && resultCode== Activity.RESULT_CANCELED){
//            Bundle bundleNhanAnh = data.getBundleExtra("boxanh");
//            viTriLoai = bundleNhanAnh.getInt("vitriloai2");
//            dialogCapNhat();
//        }
    }

//    private void dialogCapNhat() {
//        Dialog dialog = new Dialog(getContext());
//        dialog.setContentView(R.layout.dialog_themloai);
//        dialog.show();
//        TextView tieuDe = dialog.findViewById(R.id.txtTieuDeThemSV);
//        ImageView thoat = dialog.findViewById(R.id.imgThoatThemSV);
//        Button capNhat = dialog.findViewById(R.id.btnThemLoai);
//        ImageView khungAnh = dialog.findViewById(R.id.imgThemLoai);
//        EditText ten = dialog.findViewById(R.id.edtTenLoai);
//        LoaiDAO loaiDAO = new LoaiDAO(getContext());
//
//        tieuDe.setText("Cập nhật loại chi tiêu");
//        tenAnh = dsLoai.get(viTriLoai).getAnh();
//        int imgID = getContext().getResources().getIdentifier(
//                tenAnh, "drawable", getContext().getPackageName()
//        );
//        try {
//            khungAnh.setImageResource(imgID);
//        }catch (Exception e){
//            khungAnh.setImageResource(R.drawable.load_loai_chamhoi);
//        }
//        ten.setText(dsLoai.get(viTriLoai).getTen());
//        if(dsLoai.get(viTriLoai).getPhanLoai().equals("Loại thu")){
//            rdbThu.setChecked(true);
//        }else{
//            rdbChi.setChecked(true);
//        }
//        if(rdbChi.isChecked()){
//            rdbChi.setTextColor(Color.WHITE);
//            rdbThu.setTextColor(Color.GRAY);
//        }else{
//            rdbChi.setTextColor(Color.GRAY);
//            rdbThu.setTextColor(Color.WHITE);
//        }
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                if(rdbChi.isChecked()){
//                    rdbChi.setTextColor(Color.WHITE);
//                    rdbThu.setTextColor(Color.GRAY);
//                }else{
//                    rdbChi.setTextColor(Color.GRAY);
//                    rdbThu.setTextColor(Color.WHITE);
//                }
//            }
//        });
//        capNhat.setText("Cập nhật");
//
//        thoat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        capNhat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(ten.getText().toString().isEmpty()){
//                    Toast.makeText(view.getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
//                }else {
//                    String stringPhanLoai = "";
//                    String phanLoaiGoc = dsLoai.get(viTriLoai).getPhanLoai();
//                    if(rdbThu.isChecked()) stringPhanLoai = "Loại thu";
//                    else stringPhanLoai = "Loại chi";
//                    loaiDAO.capNhat(
//                        viTriLoai,
//                        tenAnh,
//                        ten.getText().toString(),
//                        stringPhanLoai
//                    );
//                    dsLoai.clear();
//                    ArrayList<Loai> dsTong = loaiDAO.doc();
//                    ArrayList<Integer> dsViTri = new ArrayList<>();
//                    ArrayList<Loai> dsLoai2 = new ArrayList<>();
//                    ArrayList<Integer> dsViTri2 = new ArrayList<>();
//                    for(int i=0; i<dsTong.size(); i++){
//                        if(dsTong.get(i).getPhanLoai().equals(phanLoaiGoc)) {
//                            dsLoai.add(dsTong.get(i));
//                            dsViTri.add(i);
//                        }else{
//                            dsLoai2.add(dsTong.get(i));
//                            dsViTri2.add(i);
//                        }
//                    }
//                    int viTri = viewPager.getCurrentItem();
//                    int viTri2 = -1;
//                    if(viTri == 0) viTri2 = 1;
//                    else viTri2 = 0;
//
//                    ListView lvLoai = viewPager.getChildAt(viTri).findViewById(R.id.lvLoaiThu);
//                    LoaiAdapter loaiAdapter = new LoaiAdapter(getContext(), dsLoai, dsViTri, viewPager);
//                    ListView lvLoai2 = viewPager.getChildAt(viTri2).findViewById(R.id.lvLoaiThu);
//                    LoaiAdapter loaiAdapter2 = new LoaiAdapter(getContext(), dsLoai2, dsViTri2, viewPager);
//                    lvLoai.setAdapter(loaiAdapter);
//                    lvLoai2.setAdapter(loaiAdapter2);
//                    Toast.makeText(view.getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                }
//            }
//        });
//
//        khungAnh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), ThuVien.class);
//                Bundle bundleLoai = new Bundle();
//                bundleLoai.putInt("vitriloai", viTriLoai);
//                intent.putExtra("boxloai", bundleLoai);
//                startActivityForResult(intent, 888);
//                dialog.dismiss();
//            }
//        });
//    }
}