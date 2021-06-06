package com.huydh54.fpolyapp.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huydh54.fpolyapp.Activity.QuanLyKhoaHoc;
import com.huydh54.fpolyapp.DAO.KhoaHocDAO;
import com.huydh54.fpolyapp.DAO.ThongBaoDAO;
import com.huydh54.fpolyapp.Model.KhoaHoc;
import com.huydh54.fpolyapp.R;

import java.io.File;
import java.util.ArrayList;

public class QLKhoaHocAdapter extends BaseAdapter {
    Context context;
    ArrayList<KhoaHoc> dsKhoaHoc = new ArrayList<KhoaHoc>();

    public QLKhoaHocAdapter(Context context, ArrayList<KhoaHoc> dsKhoaHoc) {
        this.context = context;
        this.dsKhoaHoc = dsKhoaHoc;
    }

    @Override
    public int getCount() {
        return dsKhoaHoc.size();
    }

    @Override
    public Object getItem(int i) {
        return dsKhoaHoc.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        QLKhoaHocAdapter.ViewHolder viewHolder;
        if(view == null){
            viewHolder = new QLKhoaHocAdapter.ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.item_qlkhoahoc, null);
            viewHolder.ma= view.findViewById(R.id.txtMaQLKH);
            viewHolder.anh = view.findViewById(R.id.imgAnhQLKH);
            viewHolder.ten= view.findViewById(R.id.txtTenQLKH);
            viewHolder.thoiGian= view.findViewById(R.id.txtNgayBatDauQLKH);
            viewHolder.edit = view.findViewById(R.id.imgCapNhatQLKH);
            viewHolder.delete = view.findViewById(R.id.imgXoaQLKH);
            view.setTag(viewHolder);
        }else{
            viewHolder = (QLKhoaHocAdapter.ViewHolder)view.getTag();
        }
        viewHolder.ma.setText(dsKhoaHoc.get(i).getMa());
        String imgName = dsKhoaHoc.get(i).getAnh();
        int imgID = ((Activity) context).getResources().getIdentifier(
                imgName, "drawable", ((Activity) context).getPackageName()
        );
        try {
            viewHolder.anh.setImageResource(imgID);
        }catch (Exception e){
            viewHolder.anh.setImageResource(R.drawable.load_no_image);
        }
        if(viewHolder.anh.getDrawable() == null){
            viewHolder.anh.setImageResource(R.drawable.load_no_image);
        }
        viewHolder.ten.setText(dsKhoaHoc.get(i).getTen());
        viewHolder.thoiGian.setText("Bắt đầu: " + dsKhoaHoc.get(i).getThoiGian());

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_xoa);
                dialog.show();

                Button xacNhan = dialog.findViewById(R.id.btnXacNhanXoa);
                Button huy = dialog.findViewById(R.id.btnHuyXoa);
                ImageView thoat = dialog.findViewById(R.id.imgThoatXoaQLKH);
                ThongBaoDAO thongBaoDAO = new ThongBaoDAO(context);

                xacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        KhoaHocDAO khoaHocDAO = new KhoaHocDAO(view.getContext());
                        khoaHocDAO.xoaTheoViTri(i);
                        dsKhoaHoc.remove(i);
                        QLKhoaHocAdapter.this.notifyDataSetChanged();
                        Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        thongBaoDAO.them(R.drawable.ic_thongbao_xoa, "Xóa khóa học thành công!");
                        Toast.makeText(view.getContext(), "Bạn có thông báo mới!", Toast.LENGTH_SHORT).show();
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
                dialog.setContentView(R.layout.dialog_themkhoahoc);
                dialog.show();
                TextView tieuDe = dialog.findViewById(R.id.txtTieuDeThem);
                ImageView thoat = dialog.findViewById(R.id.imgThoatThemQLKH);
                Button capNhat = dialog.findViewById(R.id.btnThemMoiQLKH);
                EditText ma = dialog.findViewById(R.id.edtMaQLKH);
                EditText anh = dialog.findViewById(R.id.edtTenAnh);
                EditText ten = dialog.findViewById(R.id.edtTenKH);
                EditText ngay = dialog.findViewById(R.id.edtNgayBatDauQLKH);
                KhoaHocDAO khoaHocDAO = new KhoaHocDAO(view.getContext());
                ThongBaoDAO thongBaoDAO = new ThongBaoDAO(context);

                tieuDe.setText("Cập nhật khóa học");
                ma.setText(dsKhoaHoc.get(i).getMa());
                ma.setEnabled(false);
                anh.setText(dsKhoaHoc.get(i).getAnh());
                ngay.setText(dsKhoaHoc.get(i).getThoiGian());
                ten.setText(dsKhoaHoc.get(i).getTen());
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
                        if(anh.getText().toString().isEmpty() ||
                                ngay.getText().toString().isEmpty() ||
                                ten.getText().toString().isEmpty()){
                            Toast.makeText(view.getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                        }else{
                            khoaHocDAO.capNhat(
                                    i,
                                    ma.getText().toString(),
                                    anh.getText().toString(),
                                    ngay.getText().toString(),
                                    ten.getText().toString()
                            );
                            dsKhoaHoc.clear();
                            dsKhoaHoc = khoaHocDAO.doc();
                            QLKhoaHocAdapter.this.notifyDataSetChanged();
                            Toast.makeText(view.getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            thongBaoDAO.them(R.drawable.ic_thongbao_tinnhan, "Cập nhật khóa học thành công!");
                            Toast.makeText(view.getContext(), "Bạn có thông báo mới!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }

    public class ViewHolder{
        ImageView anh;
        TextView ma, ten, thoiGian;
        ImageButton edit, delete;
    }
}
