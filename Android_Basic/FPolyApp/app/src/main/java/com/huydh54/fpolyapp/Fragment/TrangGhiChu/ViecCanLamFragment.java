package com.huydh54.fpolyapp.Fragment.TrangGhiChu;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huydh54.fpolyapp.Adapter.GhiChuAdapter;
import com.huydh54.fpolyapp.Adapter.ViecCanLamAdapter;
import com.huydh54.fpolyapp.Model.GhiChu;
import com.huydh54.fpolyapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ViecCanLamFragment extends Fragment {

    ListView lvDanhSachVCL;
    ViecCanLamAdapter viecCanLamAdapter;
    ImageView themViec, thoat;
    Button themMoi;
    TextView tenKhung;
    EditText tieuDe, noiDung;
    ArrayList<GhiChu> dsViecCanLam = new ArrayList<>();
    GhiChu ghiChu;
    String fileName = "listviec.txt";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viec_can_lam, container, false);

        lvDanhSachVCL = view.findViewById(R.id.lv_ViecCanLam);
        themViec = view.findViewById(R.id.ic_themviec);
        docFile();
        viecCanLamAdapter = new ViecCanLamAdapter(view.getContext(), dsViecCanLam);
        lvDanhSachVCL.setAdapter(viecCanLamAdapter);

        themViec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogThemSinhVien();
            }
        });

        return view;
    }

    private void dialogThemSinhVien() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_themghichu);
        dialog.show();
        thoat = dialog.findViewById(R.id.imgThoatThemGC);
        themMoi = dialog.findViewById(R.id.btnThemGC);
        tieuDe = dialog.findViewById(R.id.edtTieuDeGC);
        noiDung = dialog.findViewById(R.id.edtNoiDungGC);
        tenKhung = dialog.findViewById(R.id.txtTieuDeThemGC);
        tenKhung.setText("Thêm việc cần làm");


        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        themMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tieuDe.getText().toString().isEmpty() ||
                        noiDung.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    ghiChu = new GhiChu(noiDung.getText().toString(), tieuDe.getText().toString());
                    dsViecCanLam.add(ghiChu);

                    Toast.makeText(view.getContext(), "Thêm việc thành công!", Toast.LENGTH_SHORT).show();
                    tieuDe.setText("");
                    noiDung.setText("");

                    ghiFile();
                    viecCanLamAdapter = new ViecCanLamAdapter(view.getContext(), dsViecCanLam);
                    lvDanhSachVCL.setAdapter(viecCanLamAdapter);
                }
            }
        });
    }

    private void ghiFile() {
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath() + "/listviec.txt";
        try {
            Toast.makeText(getContext(), "ok ghi", Toast.LENGTH_SHORT);
            FileOutputStream fileOutputStream = new FileOutputStream(sdcard);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(dsViecCanLam);
            objectOutputStream.close();
            fileOutputStream.close();
        }catch (Exception e){
            Log.e("Error: ", e.toString());
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT);
        }
    }

    private void docFile() {
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath() + "/listviec.txt";
        try {
            Toast.makeText(getContext(), "ok ghi", Toast.LENGTH_SHORT);
            File file = new File(sdcard);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            dsViecCanLam = (ArrayList<GhiChu>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            Log.e("Error: ", e.toString());
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT);
        }
    }
}