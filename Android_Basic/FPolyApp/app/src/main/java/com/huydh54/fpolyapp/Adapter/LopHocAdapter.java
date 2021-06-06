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

import com.huydh54.fpolyapp.Activity.QuanLyLop;
import com.huydh54.fpolyapp.DAO.LopHocDAO;
import com.huydh54.fpolyapp.DAO.SinhVienDAO;
import com.huydh54.fpolyapp.DAO.TaiKhoanDAO;
import com.huydh54.fpolyapp.DAO.ThongBaoDAO;
import com.huydh54.fpolyapp.Model.LopHoc;
import com.huydh54.fpolyapp.Model.SinhVien;
import com.huydh54.fpolyapp.Model.TaiKhoan;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;

public class LopHocAdapter  extends BaseAdapter {
    Context context;
    ArrayList<LopHoc> dsLopHoc = new ArrayList<LopHoc>();

    public LopHocAdapter(Context context, ArrayList<LopHoc> dsLopHoc) {
        this.context = context;
        this.dsLopHoc = dsLopHoc;
    }

    @Override
    public int getCount() {
        return dsLopHoc.size();
    }

    @Override
    public Object getItem(int i) {
        return dsLopHoc.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LopHocAdapter.ViewHolder viewHolder;
        if(view == null){
            viewHolder = new LopHocAdapter.ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.item_lop, null);
            viewHolder.anh = view.findViewById(R.id.imgAnhQLL);
            viewHolder.ma= view.findViewById(R.id.txtMaQLL);
            viewHolder.khoa= view.findViewById(R.id.txtKhoa);
            viewHolder.nganh= view.findViewById(R.id.txtNganh);
            viewHolder.edit = view.findViewById(R.id.imgCapNhatQLL);
            viewHolder.delete = view.findViewById(R.id.imgXoaQLL);
            view.setTag(viewHolder);
        }else{
            viewHolder = (LopHocAdapter.ViewHolder)view.getTag();
        }
        if(dsLopHoc.get(i).getNganh().equals("Lập trình di động")) {
            viewHolder.anh.setImageResource(R.drawable.ic_lop_didong);
            viewHolder.anh.setBackgroundResource(R.color.xanhduong);
        }else if(dsLopHoc.get(i).getNganh().equals("Thiết kế Web")){
            viewHolder.anh.setImageResource(R.drawable.ic_lophoc_thietkeweb);
            viewHolder.anh.setBackgroundResource(R.color.xanhla);
        }else if(dsLopHoc.get(i).getNganh().equals("Ứng dụng phần mềm")){
            viewHolder.anh.setImageResource(R.drawable.ic_lophoc_ungdung);
            viewHolder.anh.setBackgroundResource(R.color.vang);
        }else if(dsLopHoc.get(i).getNganh().equals("Tự động hóa")){
            viewHolder.anh.setImageResource(R.drawable.ic_lophoc_tudong);
            viewHolder.anh.setBackgroundResource(R.color.maudo);
        }else if(dsLopHoc.get(i).getNganh().equals("Marketing - Truyền thông")){
            viewHolder.anh.setImageResource(R.drawable.ic_lophoc_mar);
            viewHolder.anh.setBackgroundResource(R.color.tim);
        }else if(dsLopHoc.get(i).getNganh().equals("Digital - Marketing")){
            viewHolder.anh.setImageResource(R.drawable.ic_lophoc_digi);
            viewHolder.anh.setBackgroundResource(R.color.xanhlama);
        }else if(dsLopHoc.get(i).getNganh().equals("Quản trị nhà hàng khách sạn")){
            viewHolder.anh.setImageResource(R.drawable.ic_lophoc_dulich);
            viewHolder.anh.setBackgroundResource(R.color.xanhngoc);
        }else if(dsLopHoc.get(i).getNganh().equals("Thiết kế đồ họa")){
            viewHolder.anh.setImageResource(R.drawable.ic_lophoc_thietkedohoa);
            viewHolder.anh.setBackgroundResource(R.color.cam);
        }else{
            viewHolder.anh.setImageResource(R.drawable.ic_lophoc_khongxacdinh);
            viewHolder.anh.setBackgroundResource(R.color.xam);
        }
        viewHolder.ma.setText(dsLopHoc.get(i).getMa());
        viewHolder.khoa.setText("Khóa " + dsLopHoc.get(i).getKhoa());
        viewHolder.nganh.setText(dsLopHoc.get(i).getNganh());

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

                noiDungXoa.setText("Bạn chắc chắn muốn xóa lớp học này?");

                xacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LopHocDAO lopHocDAO = new LopHocDAO(view.getContext());
                        Dialog dialogXoaSV = new Dialog(view.getContext());
                        dialogXoaSV.setContentView(R.layout.dialog_xoa);
                        dialogXoaSV.show();

                        TextView noiDungXoaSV = dialogXoaSV.findViewById(R.id.txtNoiDungTBXoa);
                        Button xacNhanSV = dialogXoaSV.findViewById(R.id.btnXacNhanXoa);
                        Button huySV = dialogXoaSV.findViewById(R.id.btnHuyXoa);
                        ImageView thoatSV = dialogXoaSV.findViewById(R.id.imgThoatXoaQLKH);
                        ThongBaoDAO thongBaoDAO = new ThongBaoDAO(view.getContext());

                        noiDungXoaSV.setText("Lớp học đang hoạt động. Bạn phải xóa dữ liệu sinh viên trong lớp học này! Xác nhận xóa?");

                        xacNhanSV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SinhVienDAO sinhVienDAO = new SinhVienDAO(view.getContext());
                                TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(view.getContext());
                                ArrayList<SinhVien> dsSinhVien = new ArrayList<>();
                                dsSinhVien = sinhVienDAO.doc();
                                ArrayList<TaiKhoan> dsTaiKhoan = new ArrayList<>();
                                dsTaiKhoan = taiKhoanDAO.doc();
                                for(int j=0; j<dsSinhVien.size(); j++) {
                                    if(dsLopHoc.get(i).getMa().equalsIgnoreCase(dsSinhVien.get(j).getLop())){
                                        for (int k=0; k<dsTaiKhoan.size(); k++){
                                            if (dsSinhVien.get(j).getMa().equalsIgnoreCase(dsTaiKhoan.get(k).getMaSV())){
                                                taiKhoanDAO.xoaTheoViTri(k);
                                                dsTaiKhoan.remove(k);
                                                k--;
                                            }
                                        }
                                        sinhVienDAO.xoaTheoViTri(j);
                                        dsSinhVien.remove(j);
                                        j--;
                                    }
                                }
                                lopHocDAO.xoaTheoViTri(i);
                                dsLopHoc = lopHocDAO.doc();
//                                dsLopHoc.remove(i);
                                LopHocAdapter.this.notifyDataSetChanged();
                                Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                dialogXoaSV.dismiss();
                                dialog.dismiss();
                                thongBaoDAO.them(R.drawable.ic_thongbao_xoa, "Xóa lớp học thành công!");
                                Toast.makeText(view.getContext(), "Bạn có thông báo mới!", Toast.LENGTH_SHORT).show();
                            }
                        });

                        huySV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context, "Thao tác bị hủy!", Toast.LENGTH_SHORT).show();
                                dialogXoaSV.dismiss();
                                dialog.dismiss();
                            }
                        });

                        thoatSV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogXoaSV.dismiss();
                                dialog.dismiss();
                            }
                        });
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
                dialog.setContentView(R.layout.dialog_themlop);
                dialog.show();
                TextView tieuDe = dialog.findViewById(R.id.txtTieuDeThemLop);
                ImageView thoat = dialog.findViewById(R.id.imgThoatThemQLL);
                Button capNhat = dialog.findViewById(R.id.btnThemMoiQLL);
                EditText ma = dialog.findViewById(R.id.edtMaQLL);
                EditText khoa = dialog.findViewById(R.id.edtKhoaDaoTao);
                EditText nganh = dialog.findViewById(R.id.edtNganhDaoTao);
                LopHocDAO lopHocDAO = new LopHocDAO(view.getContext());
                ThongBaoDAO thongBaoDAO = new ThongBaoDAO(view.getContext());

                tieuDe.setText("Cập nhật lớp học");
                ma.setText(dsLopHoc.get(i).getMa());
                ma.setEnabled(false);
                khoa.setText(dsLopHoc.get(i).getKhoa());
                nganh.setText(dsLopHoc.get(i).getNganh());
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
                        if(khoa.getText().toString().isEmpty() ||
                                nganh.getText().toString().isEmpty()){
                            Toast.makeText(view.getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                        }else {
                            lopHocDAO.capNhat(
                                    i,
                                    ma.getText().toString(),
                                    khoa.getText().toString(),
                                    nganh.getText().toString()
                            );
                            dsLopHoc.clear();
                            dsLopHoc = lopHocDAO.doc();
                            LopHocAdapter.this.notifyDataSetChanged();
                            Toast.makeText(view.getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            thongBaoDAO.them(R.drawable.ic_thongbao_tinnhan, "Cập nhật lớp học thành công!");
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
        TextView ma, khoa, nganh;
        ImageButton edit, delete;
    }
}
