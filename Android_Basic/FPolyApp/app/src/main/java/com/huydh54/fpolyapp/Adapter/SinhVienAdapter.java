package com.huydh54.fpolyapp.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.huydh54.fpolyapp.Activity.QuanLySinhVien;
import com.huydh54.fpolyapp.Activity.ThuVien;
import com.huydh54.fpolyapp.DAO.LopHocDAO;
import com.huydh54.fpolyapp.DAO.SinhVienDAO;
import com.huydh54.fpolyapp.DAO.ThongBaoDAO;
import com.huydh54.fpolyapp.Model.Category;
import com.huydh54.fpolyapp.Model.SinhVien;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;
import java.util.List;

public class SinhVienAdapter extends BaseAdapter {
    Context context;
    ArrayList<SinhVien> dsSinhVien = new ArrayList<SinhVien>();

    public SinhVienAdapter(Context context, ArrayList<SinhVien> dsSinhVien) {
        this.context = context;
        this.dsSinhVien = dsSinhVien;
    }

    @Override
    public int getCount() {
        return dsSinhVien.size();
    }

    @Override
    public Object getItem(int i) {
        return dsSinhVien.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SinhVienAdapter.ViewHolder viewHolder;
        if(view == null){
            viewHolder = new SinhVienAdapter.ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.item_sinhvien, null);
            viewHolder.ma = view.findViewById(R.id.txtMaSV);
            viewHolder.anh = view.findViewById(R.id.imgAvatarSV);
            viewHolder.ten = view.findViewById(R.id.txtTenSV);
            viewHolder.lop = view.findViewById(R.id.txtLopSV);
            viewHolder.edit = view.findViewById(R.id.imgCapNhatSV);
            viewHolder.delete = view.findViewById(R.id.imgXoaSV);
            view.setTag(viewHolder);
        }else{
            viewHolder = (SinhVienAdapter.ViewHolder)view.getTag();
        }
        viewHolder.ma.setText(dsSinhVien.get(i).getMa());
        String imgName = dsSinhVien.get(i).getAnh();
        int imgID = ((Activity) context).getResources().getIdentifier(
                imgName, "drawable", ((Activity) context).getPackageName()
        );
        try {
            viewHolder.anh.setImageResource(imgID);
        }catch (Exception e){
            viewHolder.anh.setImageResource(R.drawable.avatar_loi);
        }
        if(viewHolder.anh.getDrawable() == null){
            viewHolder.anh.setImageResource(R.drawable.avatar_loi);
        }
        viewHolder.ten.setText(dsSinhVien.get(i).getTen());
        viewHolder.lop.setText(dsSinhVien.get(i).getLop());

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
                ThongBaoDAO thongBaoDAO = new ThongBaoDAO(context);

                noiDungXoa.setText("Bạn chắc chắn muốn xóa sinh viên này?");

                xacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SinhVienDAO sinhVienDAO = new SinhVienDAO(view.getContext());
                        sinhVienDAO.xoaTheoViTri(i);
                        dsSinhVien.remove(i);
                        SinhVienAdapter.this.notifyDataSetChanged();
                        Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        thongBaoDAO.them(R.drawable.ic_thongbao_xoa, "Xóa sinh viên thành công!");
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
                dialog.setContentView(R.layout.dialog_themsinhvien2);
                dialog.show();
                TextView tieuDe = dialog.findViewById(R.id.txtTieuDeThemSV);
                ImageView thoat = dialog.findViewById(R.id.imgThoatThemSV);
                Button capNhat = dialog.findViewById(R.id.btnThemMoiSV);
                EditText ma = dialog.findViewById(R.id.edtMaSV);
                ImageView khungAnh = dialog.findViewById(R.id.imgAvatarSV);
                EditText ten = dialog.findViewById(R.id.edtTenSV);
                Spinner lop = dialog.findViewById(R.id.spnMaLop);
                SinhVienDAO sinhVienDAO = new SinhVienDAO(view.getContext());
                ArrayList<String> dsLopHoc;
                CategoryAdapter categoryAdapter;
                ThongBaoDAO thongBaoDAO = new ThongBaoDAO(context);
                LopHocDAO lopHocDAO = new LopHocDAO(context);
                dsLopHoc = lopHocDAO.docMaLop();
                List<Category> listLop = new ArrayList<>();
                for (int i=0; i<dsLopHoc.size(); i++){
                    Category category = new Category(dsLopHoc.get(i));
                    listLop.add(category);
                }
                categoryAdapter = new CategoryAdapter(context, R.layout.item_giaodien_spinner, listLop);
                lop.setAdapter(categoryAdapter);

                tieuDe.setText("Cập nhật sinh viên");
                ma.setText(dsSinhVien.get(i).getMa());
                ma.setEnabled(false);
                String tenAnh = dsSinhVien.get(i).getAnh();
                int imgID = view.getContext().getResources().getIdentifier(
                        tenAnh, "drawable", view.getContext().getPackageName()
                );
                try {
                    khungAnh.setImageResource(imgID);
                }catch (Exception e){
                    khungAnh.setImageResource(R.drawable.avatar_loi);
                }
//                ten.setText("");
                ten.setText(dsSinhVien.get(i).getTen());
                for (int j=0; j<dsLopHoc.size(); j++){
                    if (dsSinhVien.get(i).getLop().equalsIgnoreCase(dsLopHoc.get(j))){
                        lop.setSelection(j);
                        break;
                    }
                }
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
                            String maLop = dsLopHoc.get(lop.getSelectedItemPosition());
                            sinhVienDAO.capNhat(
                                    i,
                                    ma.getText().toString(),
                                    tenAnh,
                                    ten.getText().toString(),
                                    maLop
                            );
                            dsSinhVien.clear();
                            dsSinhVien = sinhVienDAO.doc();
                            SinhVienAdapter.this.notifyDataSetChanged();
                            Toast.makeText(view.getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            thongBaoDAO.them(R.drawable.ic_thongbao_tinnhan, "Cập nhật sinh viên thành công!");
                            Toast.makeText(view.getContext(), "Bạn có thông báo mới!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                khungAnh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ThuVien.class);
                        Bundle bundleSV = new Bundle();
                        bundleSV.putInt("vitrisv", i);
                        intent.putExtra("boxsv", bundleSV);
                        ((Activity) context).startActivityForResult(intent, 888);
                        dialog.dismiss();
                    }
                });
            }
        });

        return view;
    }

    public class ViewHolder{
        ImageView anh;
        TextView ma, ten, lop;
        ImageButton edit, delete;
    }
}
