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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.huydh54.fpolyapp.DAO.SinhVienDAO;
import com.huydh54.fpolyapp.DAO.TaiKhoanDAO;
import com.huydh54.fpolyapp.DAO.ThongBaoDAO;
import com.huydh54.fpolyapp.Model.Category;
import com.huydh54.fpolyapp.Model.TaiKhoan;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;
import java.util.List;

public class TaiKhoanAdapter extends BaseAdapter {
    Context context;
    ArrayList<TaiKhoan> dsTaiKhoan = new ArrayList<TaiKhoan>();

    public TaiKhoanAdapter(Context context, ArrayList<TaiKhoan> dsTaiKhoan) {
        this.context = context;
        this.dsTaiKhoan = dsTaiKhoan;
    }

    @Override
    public int getCount() {
        return dsTaiKhoan.size();
    }

    @Override
    public Object getItem(int i) {
        return dsTaiKhoan.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TaiKhoanAdapter.ViewHolder viewHolder;
        if(view == null){
            viewHolder = new TaiKhoanAdapter.ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.item_taikhoan, null);
            viewHolder.taiKhoan = view.findViewById(R.id.txtTenTK);
            viewHolder.matKhau = view.findViewById(R.id.txtNDMatKhau);
            viewHolder.mSSV = view.findViewById(R.id.txtMSSVMK);
            viewHolder.edit = view.findViewById(R.id.imgCapNhatTK);
            viewHolder.delete = view.findViewById(R.id.imgXoaTK);
            view.setTag(viewHolder);
        }else{
            viewHolder = (TaiKhoanAdapter.ViewHolder)view.getTag();
        }
        viewHolder.taiKhoan.setText(dsTaiKhoan.get(i).getTenTK());
        viewHolder.matKhau.setText(dsTaiKhoan.get(i).getMatKhau());
        viewHolder.mSSV.setText(dsTaiKhoan.get(i).getMaSV());

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

                noiDungXoa.setText("Bạn chắc chắn muốn xóa tài khoản này?");

                xacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(view.getContext());
                        taiKhoanDAO.xoaTheoViTri(i);
                        dsTaiKhoan.remove(i);
                        TaiKhoanAdapter.this.notifyDataSetChanged();
                        Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        thongBaoDAO.them(R.drawable.ic_thongbao_xoa, "Xóa tài khoản thành công!");
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
                dialog.setContentView(R.layout.dialog_themtaikhoan);
                dialog.show();
                TextView tieuDe = dialog.findViewById(R.id.txtTieuDeThemTK);
                ImageView thoat = dialog.findViewById(R.id.imgThoatThemTK);
                Button capNhat = dialog.findViewById(R.id.btnThemMoiTK);
                EditText capNhatTenTK = dialog.findViewById(R.id.edtTaiKhoanTTK);
                EditText capNhatMK = dialog.findViewById(R.id.edtMatKhauTTK);
                Spinner capNhatMSV = dialog.findViewById(R.id.spnMaSVTTK);
                TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(view.getContext());
                ThongBaoDAO thongBaoDAO = new ThongBaoDAO(context);
                ArrayList<String> dsMaSinhVien;
                CategoryAdapter categoryAdapter;
                SinhVienDAO sinhVienDAO = new SinhVienDAO(context);
                dsMaSinhVien = sinhVienDAO.docMaSinhVien();
                for (int j=0; j<dsMaSinhVien.size(); j++) {
                    if (dsMaSinhVien.get(j).equalsIgnoreCase(dsTaiKhoan.get(i).getMaSV())){
                        continue;
                    } else {
                        for (int k=0; k < dsTaiKhoan.size(); k++) {
                            if (dsMaSinhVien.get(j).equalsIgnoreCase(dsTaiKhoan.get(k).getMaSV())) {
                                dsMaSinhVien.remove(j);
                                j--;
                                break;
                            }
                        }
                    }
                }
                List<Category> listSinhVien = new ArrayList<>();
                for (int j=0; j<dsMaSinhVien.size(); j++){
                    Category category = new Category(dsMaSinhVien.get(j));
                    listSinhVien.add(category);
                }
                categoryAdapter = new CategoryAdapter(context, R.layout.item_giaodien_spinner, listSinhVien);
                capNhatMSV.setAdapter(categoryAdapter);

                tieuDe.setText("Cập nhật sinh viên");
                capNhatTenTK.setText(dsTaiKhoan.get(i).getTenTK());
                capNhatTenTK.setEnabled(false);
                capNhatMK.setText(dsTaiKhoan.get(i).getMatKhau());
                for (int j=0; j<dsMaSinhVien.size(); j++){
                    if (dsTaiKhoan.get(i).getMaSV().equalsIgnoreCase(dsMaSinhVien.get(j))){
                        capNhatMSV.setSelection(j);
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
                        if(capNhatMK.getText().toString().isEmpty()){
                            Toast.makeText(view.getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                        }else {
                            String maSinhVien = dsMaSinhVien.get(capNhatMSV.getSelectedItemPosition());
                            try {
                                taiKhoanDAO.capNhat(
                                        i,
                                        capNhatTenTK.getText().toString(),
                                        capNhatMK.getText().toString(),
                                        maSinhVien
                                );
                                dsTaiKhoan.clear();
                                dsTaiKhoan = taiKhoanDAO.doc();
                                TaiKhoanAdapter.this.notifyDataSetChanged();
                                Toast.makeText(view.getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                thongBaoDAO.them(R.drawable.ic_thongbao_tinnhan, "Cập nhật tài khoản thành công!");
                                Toast.makeText(view.getContext(), "Bạn có thông báo mới!", Toast.LENGTH_SHORT).show();
                            } catch (android.database.sqlite.SQLiteConstraintException e) {
                                dialogThongBao("Trùng lặp dữ liệu!");
                                capNhatTenTK.setText("");
                                capNhatTenTK.requestFocus();
                            }
                        }
                    }
                });
            }
        });

        return view;
    }

    public class ViewHolder{
        TextView taiKhoan, matKhau, mSSV;
        ImageButton edit, delete;
    }

    private void dialogThongBao(String noiDung) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_thongbao);
        dialog.show();

        Button xacNhan = dialog.findViewById(R.id.btnXacNhanTB);
        TextView thongBao = dialog.findViewById(R.id.txtNoiDungTB);
        ImageView thoat = dialog.findViewById(R.id.imgThoatTB);

        thongBao.setText(noiDung);

        xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
}
