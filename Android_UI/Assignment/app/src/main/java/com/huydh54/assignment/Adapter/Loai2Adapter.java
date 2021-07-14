package com.huydh54.assignment.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.huydh54.assignment.Activity.MainActivity;
import com.huydh54.assignment.Activity.ThuVien;
import com.huydh54.assignment.DAO.KhoanDAO;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Model.Khoan;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;
import com.huydh54.assignment.ViewHolder.ViewHolderLoai;
import com.huydh54.assignment.ViewPager.CustomViewPager;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class Loai2Adapter extends RecyclerView.Adapter<ViewHolderLoai> {
    ArrayList<Loai> dsLoai;
    Context context;
    ArrayList<Integer> dsViTri = new ArrayList<Integer>();
    ViewPager viewPager;

    public Loai2Adapter(ArrayList<Loai> dsLoai, Context context, ArrayList<Integer> dsViTri, ViewPager viewPager) {
        this.dsLoai = dsLoai;
        this.context = context;
        this.dsViTri = dsViTri;
        this.viewPager = viewPager;
    }

    @NonNull
    @Override
    public ViewHolderLoai onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_loai2,parent,false);
        return new ViewHolderLoai(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLoai holder, int position) {
        Loai loai = dsLoai.get(position);
        String tenAnh = loai.getAnh();
        int imgID = context.getResources().getIdentifier(
                tenAnh, "drawable", context.getPackageName()
        );
        try {
            holder.anh.setImageResource(imgID);
        }catch (Exception e){
            holder.anh.setImageResource(R.drawable.load_loai_loi);
        }
        holder.ten.setText(loai.getTen());
        holder.phanLoai.setText(loai.getPhanLoai());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_xoa);
                dialog.show();

                TextView noiDungXoa = dialog.findViewById(R.id.txtNoiDungTBXoa);
                Button xacNhan = dialog.findViewById(R.id.btnXacNhanXoa);
                Button huy = dialog.findViewById(R.id.btnHuyXoa);
                ImageView thoat = dialog.findViewById(R.id.imgThoatXoaQLKH);

                noiDungXoa.setText("Bạn chắc chắn muốn xóa danh mục này?");

                xacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     //   Toast.makeText(context, position + " - " + dsViTri.get(position) + "", Toast.LENGTH_SHORT).show();
                        Loai loaiXoa = dsLoai.get(position);
                        LoaiDAO loaiDAO = new LoaiDAO(view.getContext());
                        Dialog dialogXoaLoai = new Dialog(view.getContext());
                        dialogXoaLoai.setContentView(R.layout.dialog_xoa);
                        KhoanDAO khoanDAO = new KhoanDAO(view.getContext());
                        dialogXoaLoai.show();

                        TextView noiDungXoa = dialogXoaLoai.findViewById(R.id.txtNoiDungTBXoa);
                        Button xacNhanXoa = dialogXoaLoai.findViewById(R.id.btnXacNhanXoa);
                        Button huyXoa = dialogXoaLoai.findViewById(R.id.btnHuyXoa);
                        ImageView thoatXoa = dialogXoaLoai.findViewById(R.id.imgThoatXoaQLKH);
                        noiDungXoa.setText("Thao tác sẽ xóa toàn bộ dữ liệu chi tiêu thuộc loại " + loaiXoa.getTen()+ "! Xác nhận xóa?");

                        xacNhanXoa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ArrayList<Khoan> dsKhoan = new ArrayList<>();
                                dsKhoan = khoanDAO.doc();
                                for(int j=0; j<dsKhoan.size(); j++) {
                                    if(dsKhoan.get(j).getMaLop() == loaiDAO.getMa(dsViTri.get(position))){
                                        khoanDAO.xoaTheoViTri(j);
                                        dsKhoan.remove(j);
                                        j--;
                                    }
                                }
                                loaiDAO.xoaTheoViTri(dsViTri.get(position));
                                RecyclerView rvLoaiThu = viewPager.getChildAt(0).findViewById(R.id.rvLoaiThu);
                                RecyclerView rvLoaiChi = viewPager.getChildAt(1).findViewById(R.id.rvLoaiThu);
                                Loai2Adapter loai2AdapterThu = new Loai2Adapter(
                                        loaiDAO.docThuChi(true),
                                        context,
                                        loaiDAO.docViTri(true),
                                        viewPager
                                );
                                Loai2Adapter loai2AdapterChi = new Loai2Adapter(
                                        loaiDAO.docThuChi(false),
                                        context,
                                        loaiDAO.docViTri(false),
                                        viewPager
                                );
                                rvLoaiThu.setAdapter(loai2AdapterThu);
                                rvLoaiChi.setAdapter(loai2AdapterChi);

                                dialogXoaLoai.dismiss();
                                dialog.dismiss();
                                capNhatKhoan();
                            }
                        });

                        huyXoa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context, "Thao tác bị hủy!", Toast.LENGTH_SHORT).show();
                                dialogXoaLoai.dismiss();
                                dialog.dismiss();
                            }
                        });

                        thoatXoa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogXoaLoai.dismiss();
                                dialog.dismiss();
                            }
                        });

//                        LoaiDAO loaiDAO = new LoaiDAO(view.getContext());
//                        loaiDAO.xoaTheoViTri(dsViTri.get(position));
//                        dsLoai.remove(position);
//                        Loai2Adapter.this.notifyDataSetChanged();
//                        Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
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

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_themloai);
                dialog.show();
                TextView tieuDe = dialog.findViewById(R.id.txtTieuDeThemSV);
                ImageView thoat = dialog.findViewById(R.id.imgThoatThemSV);
                Button capNhat = dialog.findViewById(R.id.btnThemLoai);
                ImageView khungAnh = dialog.findViewById(R.id.imgThemLoai);
                RadioButton rdoThu = dialog.findViewById(R.id.rdoThu);
                RadioGroup radioGroup = dialog.findViewById(R.id.rdoGroup);
                RadioButton rdoChi = dialog.findViewById(R.id.rdoChi);
                EditText ten = dialog.findViewById(R.id.edtTenLoai);
                LoaiDAO loaiDAO = new LoaiDAO(view.getContext());

                tieuDe.setText("Cập nhật loại chi tiêu");
                String tenAnh = dsLoai.get(position).getAnh();
                int imgID = view.getContext().getResources().getIdentifier(
                        tenAnh, "drawable", view.getContext().getPackageName()
                );
                try {
                    khungAnh.setImageResource(imgID);
                }catch (Exception e){
                    khungAnh.setImageResource(R.drawable.load_loai_chamhoi);
                }
                ten.setText(dsLoai.get(position).getTen());
                if(dsLoai.get(position).getPhanLoai().equals("Loại thu")){
                    rdoThu.setChecked(true);
                }else{
                    rdoChi.setChecked(true);
                }
                if(rdoChi.isChecked()){
                    rdoChi.setTextColor(Color.WHITE);
                    rdoThu.setTextColor(Color.GRAY);
                }else{
                    rdoChi.setTextColor(Color.GRAY);
                    rdoThu.setTextColor(Color.WHITE);
                }
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if(rdoChi.isChecked()){
                            rdoChi.setTextColor(Color.WHITE);
                            rdoThu.setTextColor(Color.GRAY);
                        }else{
                            rdoChi.setTextColor(Color.GRAY);
                            rdoThu.setTextColor(Color.WHITE);
                        }
                    }
                });
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
                            String stringPhanLoai = "";
                            String phanLoaiGoc = dsLoai.get(position).getPhanLoai();
                            if(rdoThu.isChecked()){
                                stringPhanLoai = "Loại thu";
                            }else{
                                stringPhanLoai = "Loại chi";
                            }
                            loaiDAO.capNhat(
                                    dsViTri.get(position),
                                    tenAnh,
                                    ten.getText().toString(),
                                    stringPhanLoai
                            );
                            dsLoai.clear();
                            ArrayList<Loai> dsTong = loaiDAO.doc();
                            ArrayList<Integer> dsViTri = new ArrayList<>();
                            ArrayList<Loai> dsLoai2 = new ArrayList<>();
                            ArrayList<Integer> dsViTri2 = new ArrayList<>();



//                            Toast.makeText(context, phanLoaiGoc, Toast.LENGTH_SHORT).show();
                            for(int i=0; i<dsTong.size(); i++){
                                if(dsTong.get(i).getPhanLoai().equals("Vay nợ")){
                                    continue;
                                }else if(dsTong.get(i).getPhanLoai().equals(phanLoaiGoc)) {
                                    dsLoai.add(dsTong.get(i));
                                    dsViTri.add(i);
                                }else{
                                    dsLoai2.add(dsTong.get(i));
                                    dsViTri2.add(i);
                                }
                            }
//                            LoaiAdapter.this.notifyDataSetChanged();
                            int viTri = viewPager.getCurrentItem();
                            int viTri2 = -1;
                            if(viTri == 0) viTri2 = 1;
                            else viTri2 = 0;

                            RecyclerView rvLoai = viewPager.getChildAt(viTri).findViewById(R.id.rvLoaiThu);
                            Loai2Adapter loai2Adapter = new Loai2Adapter(dsLoai, context, dsViTri, viewPager);
                            RecyclerView rvLoai2 = viewPager.getChildAt(viTri2).findViewById(R.id.rvLoaiThu);
                            Loai2Adapter loai2Adapter2 = new Loai2Adapter(dsLoai2, context, dsViTri2, viewPager);
                            rvLoai.setAdapter(loai2Adapter);
                            rvLoai2.setAdapter(loai2Adapter2);

                            Toast.makeText(view.getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            capNhatKhoan();
                        }
                    }
                });

                khungAnh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ThuVien.class);
                        Bundle bundleSV = new Bundle();
                        bundleSV.putInt("vitriloai", dsViTri.get(position));
                        intent.putExtra("boxloai", bundleSV);

                        boolean checkRadio;
                        if(rdoThu.isChecked()==true) checkRadio = true;
                        else checkRadio = false;
                        bundleSV.putBoolean("checkradio", checkRadio);
                        String tenLoaiGui, tenAnhGui;
                        tenLoaiGui = ten.getText().toString();
//                        Toast.makeText(context, tenLoaiGui, Toast.LENGTH_SHORT).show();
                        bundleSV.putString("tencapnhat", tenLoaiGui);
                        tenAnhGui = dsLoai.get(position).getAnh();
                        bundleSV.putString("tenanhgui", tenAnhGui);

                        ((Activity)context).startActivityForResult(intent, 888);
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void capNhatKhoan() {
        KhoanDAO khoanDAO = new KhoanDAO(context);
        LoaiDAO loaiDAO = new LoaiDAO(context);
        ArrayList<Khoan> dsKhoan = khoanDAO.doc();
        ArrayList<Khoan> dsKhoanThu = new ArrayList<>();
        ArrayList<Integer> dsViTriThu = new ArrayList<>();
        ArrayList<Khoan> dsKhoanChi = new ArrayList<>();
        ArrayList<Integer> dsViTriChi = new ArrayList<>();
        double tongThu = 0, tongChi = 0;
        for (int i = 0; i < dsKhoan.size(); i++) {
            Loai loai = loaiDAO.timTheoMa(dsKhoan.get(i).getMaLop());
            if (loai.getPhanLoai().equals("Loại thu") ||
                    (loai.getPhanLoai().equals("Vay nợ") &&
                            (loai.getTen().equals("Đi vay") || loai.getTen().equals("Thu nợ")))) {
                dsKhoanThu.add(dsKhoan.get(i));
                dsViTriThu.add(i);
                tongThu += dsKhoan.get(i).getSoTien();
            } else {
                dsKhoanChi.add(dsKhoan.get(i));
                dsViTriChi.add(i);
                tongChi += dsKhoan.get(i).getSoTien();
            }
        }
        CustomViewPager vpMain = ((Activity) context).findViewById(R.id.vpKhungMain);
        ViewPager vpKhoan = vpMain.getChildAt(1).findViewById(R.id.vpKhung);
        RecyclerView rvKhoanThu = vpKhoan.getChildAt(0).findViewById(R.id.rvKhoanThu);
        RecyclerView rvKhoanChi = vpKhoan.getChildAt(1).findViewById(R.id.rvKhoanThu);


        KhoanAdapter khoanAdapterThu = new KhoanAdapter(dsKhoanThu, context, loaiDAO, dsViTriThu, khoanDAO);
        rvKhoanThu.setAdapter(khoanAdapterThu);

        KhoanAdapter khoanAdapterChi = new KhoanAdapter(dsKhoanChi, context, loaiDAO, dsViTriChi, khoanDAO);
        rvKhoanChi.setAdapter(khoanAdapterChi);

        Button buttonThu = vpKhoan.getChildAt(0).findViewById(R.id.btnThemKhoan);
        if (dsKhoanThu.size() == 0) {
            buttonThu.setVisibility(View.VISIBLE);
        } else {
            buttonThu.setVisibility(View.GONE);
        }
        Button buttonChi = vpKhoan.getChildAt(1).findViewById(R.id.btnThemKhoan);
        if (dsKhoanChi.size() == 0) {
            buttonChi.setVisibility(View.VISIBLE);
        } else {
            buttonChi.setVisibility(View.GONE);
        }

        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        String patternTienTe = "###,###,### ₫";
        DecimalFormat formatTienTe = new DecimalFormat(patternTienTe, decimalFormatSymbols);
        TextView txtTongThu = vpKhoan.getChildAt(0).findViewById(R.id.txtTong);
        txtTongThu.setText(formatTienTe.format(tongThu));
        TextView txtTongChi = vpKhoan.getChildAt(1).findViewById(R.id.txtTong);
        txtTongChi.setText(formatTienTe.format(tongChi));

        TextView soTienVao = vpMain.getChildAt(1).findViewById(R.id.txtSoTienVao);
        TextView soTienRa = vpMain.getChildAt(1).findViewById(R.id.txtSoTienRa);
        TextView soTienTong = vpMain.getChildAt(1).findViewById(R.id.txtSoTienTong);
        soTienVao.setText(formatTienTe.format(tongThu));
        soTienRa.setText(formatTienTe.format(tongChi));

        double tienVao = chuyenChuThanhSo(soTienVao.getText().toString());
        double tienRa = chuyenChuThanhSo(soTienRa.getText().toString());
        double tienTong = tienVao - tienRa;
        soTienTong.setText(formatTienTe.format(tienTong));
    }

    private double chuyenChuThanhSo(String stringTienTe) {
        String[] daySo = stringTienTe.substring(0, stringTienTe.length()-2).split("");
        String soNoi = "";
        for(int i=0; i<daySo.length; i++){
            if(daySo[i].equals(",")){
                continue;
            }else{
                soNoi+=daySo[i];
            }
        }
        return Double.parseDouble(soNoi);
    }

    @Override
    public int getItemCount() {
        return dsLoai.size();
    }
}
