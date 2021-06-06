package com.huydh54.fpolyapp.Fragment.TrangGhiChu;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.huydh54.fpolyapp.Adapter.GhiChuAdapter;
import com.huydh54.fpolyapp.Model.GhiChu;
import com.huydh54.fpolyapp.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GhiChuFragment extends Fragment{

    ListView lvDanhSachGC;
    GhiChuAdapter ghiChuAdapter;
    ImageView themGhiChu, thoat;
    Button themMoi;
    EditText tieuDe, noiDung;
    ArrayList<GhiChu> dsGhiChu = new ArrayList<>();
    GhiChu ghiChu;
    String fileName = "listghichu.txt";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ghi_chu, container, false);

        lvDanhSachGC = view.findViewById(R.id.lv_GhiChu);
        themGhiChu = view.findViewById(R.id.ic_themghichu);
        docFile();
        ghiChuAdapter = new GhiChuAdapter(view.getContext(), dsGhiChu);
        lvDanhSachGC.setAdapter(ghiChuAdapter);

        themGhiChu.setOnClickListener(new View.OnClickListener() {
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
                    dsGhiChu.add(ghiChu);

                    Toast.makeText(view.getContext(), "Ghi chú thành công!", Toast.LENGTH_SHORT).show();
                    tieuDe.setText("");
                    noiDung.setText("");

                    ghiFile();
                    ghiChuAdapter = new GhiChuAdapter(view.getContext(), dsGhiChu);
                    lvDanhSachGC.setAdapter(ghiChuAdapter);
                }
            }
        });
    }

    private void ghiFile() {
        try {
            FileOutputStream fileOutputStream = getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(dsGhiChu);
            objectOutputStream.close();
            fileOutputStream.close();
        }catch (Exception e){
            Log.e("Error: ", e.toString());
        }
    }

    private void docFile() {
        try {
            FileInputStream fis = getContext().openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            dsGhiChu = (ArrayList<GhiChu>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            Log.e("Error: ", e.toString());
        }
    }
}