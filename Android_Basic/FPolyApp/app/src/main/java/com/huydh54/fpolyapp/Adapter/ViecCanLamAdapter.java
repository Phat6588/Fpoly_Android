package com.huydh54.fpolyapp.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huydh54.fpolyapp.Model.GhiChu;
import com.huydh54.fpolyapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ViecCanLamAdapter extends BaseAdapter {
    Context context;
    ArrayList<GhiChu> dsViecCanLam = new ArrayList<GhiChu>();

    public ViecCanLamAdapter(Context context, ArrayList<GhiChu> dsGhiChu) {
        this.context = context;
        this.dsViecCanLam = dsGhiChu;
    }

    @Override
    public int getCount() {
        return dsViecCanLam.size();
    }

    @Override
    public Object getItem(int i) {
        return dsViecCanLam.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViecCanLamAdapter.ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViecCanLamAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_ghichu, null);
            viewHolder.tieuDe = view.findViewById(R.id.txtTieuDeGC);
            viewHolder.noiDung = view.findViewById(R.id.txtNoiDungGC);
            viewHolder.edit = view.findViewById(R.id.imgSuaGC);
            viewHolder.delete = view.findViewById(R.id.imgXoaGC);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViecCanLamAdapter.ViewHolder)view.getTag();
        }
        viewHolder.tieuDe.setText(dsViecCanLam.get(i).getTieuDe());
        viewHolder.noiDung.setText(dsViecCanLam.get(i).getNoiDung());

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_xoa);
                dialog.show();

                TextView noiDungXoa = dialog.findViewById(R.id.txtNoiDungTBXoa);
                Button xacNhan = dialog.findViewById(R.id.btnXacNhanXoa);
                Button huy = dialog.findViewById(R.id.btnHuyXoa);
                ImageView thoat = dialog.findViewById(R.id.imgThoatXoaQLKH);

                noiDungXoa.setText("Bạn chắc chắn muốn xóa ghi chú này?");

                xacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dsViecCanLam.remove(i);
                        ghiFile();
                        ViecCanLamAdapter.this.notifyDataSetChanged();
                        Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "Thao tác bị hủy!", Toast.LENGTH_SHORT).show();
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
        });

        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_themghichu);
                dialog.show();
                TextView tenKhung = dialog.findViewById(R.id.txtTieuDeThemGC);
                EditText tieuDe = dialog.findViewById(R.id.edtTieuDeGC);
                EditText noiDung = dialog.findViewById(R.id.edtNoiDungGC);
                Button capNhat = dialog.findViewById(R.id.btnThemGC);
                ImageView thoat = dialog.findViewById(R.id.imgThoatThemGC);

                tenKhung.setText("Cập nhật ghi chú");
                tieuDe.setText(dsViecCanLam.get(i).getTieuDe());
                noiDung.setText(dsViecCanLam.get(i).getNoiDung());
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
                        if(tieuDe.getText().toString().isEmpty() ||
                                noiDung.getText().toString().isEmpty()){
                            Toast.makeText(view.getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                        }else {
                            dsViecCanLam.get(i).setTieuDe(tieuDe.getText().toString());
                            dsViecCanLam.get(i).setNoiDung(noiDung.getText().toString());

                            ghiFile();

                            ViecCanLamAdapter.this.notifyDataSetChanged();
                            Toast.makeText(view.getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        return view;
    }

    public class ViewHolder{
        TextView noiDung;
        TextView tieuDe;
        ImageView delete, edit;
    }

    private void ghiFile() {
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath() + "/listviec.txt";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(sdcard);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(dsViecCanLam);
            objectOutputStream.close();
            fileOutputStream.close();
        }catch (Exception e){
            Log.e("Error: ", e.toString());
        }
    }

    private void docFile() {
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath() + "/listviec.txt";
        try {
            File file = new File(sdcard);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            dsViecCanLam = (ArrayList<GhiChu>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            Log.e("Error: ", e.toString());
        }
    }
}
