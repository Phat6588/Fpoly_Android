package com.huydh54.assignment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.huydh54.assignment.Adapter.ChonLoai2Adapter;
import com.huydh54.assignment.Adapter.ChonLoaiAdapter;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;

import java.util.ArrayList;

public class ChonLoai2Activity extends AppCompatActivity {

    ListView lvLoaiVay, lvLoaiThu, lvLoaiChi;
    ChonLoai2Adapter chonLoaiVayAdapter, chonLoaiThuAdapter, chonLoaiChiAdapter;
    ArrayList<Loai> dsLoai, dsLoaiVay, dsLoaiThu, dsLoaiChi;
    ArrayList<Integer> dsViTriVay, dsViTriThu, dsViTriChi;
    LoaiDAO loaiDAO;
    Toolbar toolbar;
    int chieuCao0, chieuCao1, chieuCao2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_loai2);
        toolbar = findViewById(R.id.tbChonLoai);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        lvLoaiVay = findViewById(R.id.lvVayNoBS);
        lvLoaiThu = findViewById(R.id.lvLoaiThuBS);
        lvLoaiChi = findViewById(R.id.lvLoaiChiBS);

        dsLoai = new ArrayList<>();
        loaiDAO = new LoaiDAO(ChonLoai2Activity.this);
        dsLoai = loaiDAO.doc();

        dsLoaiVay = new ArrayList<>();
        dsLoaiThu = new ArrayList<>();
        dsLoaiChi = new ArrayList<>();
        dsViTriVay = new ArrayList<>();
        dsViTriThu = new ArrayList<>();
        dsViTriChi = new ArrayList<>();

        for (int i=0; i<dsLoai.size(); i++){
            if(dsLoai.get(i).getPhanLoai().equals("Vay nợ")){
                dsLoaiVay.add(dsLoai.get(i));
                dsViTriVay.add(i);
            }else if(dsLoai.get(i).getPhanLoai().equals("Loại chi")){
                dsLoaiChi.add(dsLoai.get(i));
                dsViTriChi.add(i);
            }else if(dsLoai.get(i).getPhanLoai().equals("Loại thu")){
                dsLoaiThu.add(dsLoai.get(i));
                dsViTriThu.add(i);
            }
        }

        chonLoaiVayAdapter = new ChonLoai2Adapter(dsLoaiVay, ChonLoai2Activity.this, loaiDAO, dsViTriVay);
        chonLoaiThuAdapter = new ChonLoai2Adapter(dsLoaiThu, ChonLoai2Activity.this, loaiDAO, dsViTriThu);
        chonLoaiChiAdapter = new ChonLoai2Adapter(dsLoaiChi, ChonLoai2Activity.this, loaiDAO, dsViTriChi);

        lvLoaiVay.setAdapter(chonLoaiVayAdapter);
        lvLoaiThu.setAdapter(chonLoaiThuAdapter);
        lvLoaiChi.setAdapter(chonLoaiChiAdapter);

        chieuCao0 = 168*4;
        chieuCao1 = 168*dsLoaiThu.size();
        chieuCao2 = 168*dsViTriChi.size();
        LinearLayout.LayoutParams layoutParams0 = (LinearLayout.LayoutParams) lvLoaiVay.getLayoutParams();
        layoutParams0.height = chieuCao0;
        lvLoaiVay.setLayoutParams(layoutParams0);
        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) lvLoaiThu.getLayoutParams();
        layoutParams1.height = chieuCao1;
        lvLoaiThu.setLayoutParams(layoutParams1);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) lvLoaiChi.getLayoutParams();
        layoutParams2.height = chieuCao2;
        lvLoaiChi.setLayoutParams(layoutParams2);

        lvLoaiVay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int maLoaiCapNhat = loaiDAO.getMa(dsViTriVay.get(i));
                Intent intent = new Intent();
                Bundle bundleMa = new Bundle();
                bundleMa.putInt("ma", maLoaiCapNhat);
                intent.putExtra("boxma", bundleMa);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        lvLoaiThu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int maLoaiCapNhat = loaiDAO.getMa(dsViTriThu.get(i));
                Intent intent = new Intent();
                Bundle bundleMa = new Bundle();
                bundleMa.putInt("ma", maLoaiCapNhat);
                intent.putExtra("boxma", bundleMa);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        lvLoaiChi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int maLoaiCapNhat = loaiDAO.getMa(dsViTriChi.get(i));
                Intent intent = new Intent();
                Bundle bundleMa = new Bundle();
                bundleMa.putInt("ma", maLoaiCapNhat);
                intent.putExtra("boxma", bundleMa);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }
}