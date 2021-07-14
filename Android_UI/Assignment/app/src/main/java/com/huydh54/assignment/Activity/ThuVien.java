package com.huydh54.assignment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.huydh54.assignment.Adapter.AnhThuVienAdapter;
import com.huydh54.assignment.Model.AnhThuVien;
import com.huydh54.assignment.R;

import java.util.ArrayList;

public class ThuVien extends AppCompatActivity {

    Toolbar tbThuVien;
    GridView gvThuVien;
    ArrayList<AnhThuVien> dsTenAnh;
    AnhThuVienAdapter anhAdapter;
    int viTriLoai = -1;
    String tenCapNhat, tenAnhGui;
    boolean checkRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_vien);

        tbThuVien = findViewById(R.id.tbThuVien);
        gvThuVien = findViewById(R.id.gvThuVien);
        dsTenAnh = new ArrayList<>();

        try {
            Bundle bundleNhanLoai = getIntent().getBundleExtra("boxloai");
            viTriLoai = bundleNhanLoai.getInt("vitriloai");
            tenCapNhat = bundleNhanLoai.getString("tencapnhat");
            checkRadio = bundleNhanLoai.getBoolean("checkradio");
            tenAnhGui = bundleNhanLoai.getString("tenanhgui");
        } catch (Exception e) {}

        String[] stringTenAnh = {
                "load_loai_birthday", "load_loai_blood", "load_loai_box", "load_loai_bus",
                "load_loai_cake", "load_loai_calculator", "load_loai_camera", "load_loai_card",
                "load_loai_cart", "load_loai_casino", "load_loai_cat", "load_loai_dog",
                "load_loai_champagne", "load_loai_chart", "load_loai_chess", "load_loai_christmas",
                "load_loai_church", "load_loai_cinema", "load_loai_desktop", "load_loai_watermelon",
                "load_loai_dice", "load_loai_dj", "load_loai_doctor", "load_loai_medicine",
                "load_loai_donut", "load_loai_eat", "load_loai_noodles", "load_loai_coffee",
                "load_loai_icream", "load_loai_fix", "load_loai_car", "load_loai_motor",
                "load_loai_gas", "load_loai_oils", "load_loai_game", "load_loai_guitar",
                "load_loai_hair", "load_loai_heart", "load_loai_diamond", "load_loai_house",
                "load_loai_image", "load_loai_iphone", "load_loai_lift_weight", "load_loai_luggage",
                "load_loai_olive", "load_loai_pine", "load_loai_plane", "load_loai_ring",
                "load_loai_save_money", "load_loai_shirt", "load_loai_shopping", "load_loai_shower",
                "load_loai_soccer", "load_loai_sushi", "load_loai_swimming",
                "load_loai_salary", "load_loai_salary2", "load_loai_victory", "load_loai_team",
                "load_loai_chamhoi"
        };
        for(int i=0; i<stringTenAnh.length; i++){
            dsTenAnh.add(new AnhThuVien(stringTenAnh[i]));
        }

        anhAdapter = new AnhThuVienAdapter(ThuVien.this, dsTenAnh);
        gvThuVien.setAdapter(anhAdapter);

        setSupportActionBar(tbThuVien);
        tbThuVien.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundleAnh = new Bundle();
                bundleAnh.putInt("vitriloai2", viTriLoai);
                intent.putExtra("boxanh", bundleAnh);
                bundleAnh.putString("tencapnhat2", tenCapNhat);
                bundleAnh.putBoolean("checkradio2", checkRadio);
                bundleAnh.putString("tenanh", tenAnhGui);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        gvThuVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String tenAnh = dsTenAnh.get(i).getTenAnh();
                Intent intent = new Intent();
                Bundle bundleAnh = new Bundle();
                bundleAnh.putString("tenanh", tenAnh);
                bundleAnh.putInt("vitriloai2", viTriLoai);
                bundleAnh.putString("tencapnhat2", tenCapNhat);
                bundleAnh.putBoolean("checkradio2", checkRadio);
                intent.putExtra("boxanh", bundleAnh);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}