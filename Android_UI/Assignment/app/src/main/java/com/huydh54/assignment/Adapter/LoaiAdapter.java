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
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.huydh54.assignment.Activity.ThuVien;
import com.huydh54.assignment.DAO.LoaiDAO;
import com.huydh54.assignment.Fragment.MainFragment.LoaiFragment;
import com.huydh54.assignment.Model.Loai;
import com.huydh54.assignment.R;
import com.huydh54.assignment.ViewPager.ViewPagerLoai;

import java.util.ArrayList;

public class LoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> dsViTri = new ArrayList<Integer>();
    ArrayList<Loai> dsLoai = new ArrayList<Loai>();
    ViewPager viewPager;

    public LoaiAdapter(Context context, ArrayList<Loai> dsLoai, ArrayList<Integer> dsViTri, ViewPager viewPager) {
        this.context = context;
        this.dsLoai = dsLoai;
        this.dsViTri = dsViTri;
        this.viewPager = viewPager;
    }

    @Override
    public int getCount() {
        return dsLoai.size();
    }

    @Override
    public Object getItem(int i) {
        return dsLoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LoaiAdapter.ViewHolder viewHolder;
        if(view == null){
            viewHolder = new LoaiAdapter.ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.item_loai, null);
            viewHolder.anh = view.findViewById(R.id.imgAnhLoai);
            viewHolder.ten = view.findViewById(R.id.txtTenLoai);
            viewHolder.phanLoai = view.findViewById(R.id.txtPhanLoai);
            viewHolder.edit = view.findViewById(R.id.imgCapNhatLoai);
            viewHolder.delete = view.findViewById(R.id.imgXoaLoai);
            view.setTag(viewHolder);
        }else{
            viewHolder = (LoaiAdapter.ViewHolder)view.getTag();
        }
        String tenAnh = dsLoai.get(i).getAnh();
        int imgID = view.getContext().getResources().getIdentifier(
                tenAnh, "drawable", view.getContext().getPackageName()
        );
        try {
            viewHolder.anh.setImageResource(imgID);
        }catch (Exception e){
            viewHolder.anh.setImageResource(R.drawable.load_loai_loi);
        }
        viewHolder.ten.setText(dsLoai.get(i).getTen());
        viewHolder.phanLoai.setText(dsLoai.get(i).getPhanLoai());

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

                noiDungXoa.setText("Bạn chắc chắn muốn xóa danh mục này?");

                xacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LoaiDAO loaiDAO = new LoaiDAO(view.getContext());
                        loaiDAO.xoaTheoViTri(dsViTri.get(i));
                        dsLoai.remove(i);
                        LoaiAdapter.this.notifyDataSetChanged();
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
                String tenAnh = dsLoai.get(i).getAnh();
                int imgID = view.getContext().getResources().getIdentifier(
                        tenAnh, "drawable", view.getContext().getPackageName()
                );
                try {
                    khungAnh.setImageResource(imgID);
                }catch (Exception e){
                    khungAnh.setImageResource(R.drawable.load_loai_chamhoi);
                }
                ten.setText(dsLoai.get(i).getTen());
                if(dsLoai.get(i).getPhanLoai().equals("Loại thu")){
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
                            String phanLoaiGoc = dsLoai.get(i).getPhanLoai();
                            if(rdoThu.isChecked()){
                                stringPhanLoai = "Loại thu";
                            }else{
                                stringPhanLoai = "Loại chi";
                            }
                            loaiDAO.capNhat(
                                    dsViTri.get(i),
                                    tenAnh,
                                    ten.getText().toString(),
                                    stringPhanLoai
                            );
                            dsLoai.clear();
                            ArrayList<Loai> dsTong = loaiDAO.doc();
                            ArrayList<Integer> dsViTri = new ArrayList<>();
                            ArrayList<Loai> dsLoai2 = new ArrayList<>();
                            ArrayList<Integer> dsViTri2 = new ArrayList<>();
                            for(int i=0; i<dsTong.size(); i++){
                                if(dsTong.get(i).getPhanLoai().equals(phanLoaiGoc)) {
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

                            ListView lvLoai = viewPager.getChildAt(viTri).findViewById(R.id.lvLoaiThu);
                            LoaiAdapter loaiAdapter = new LoaiAdapter(context, dsLoai, dsViTri, viewPager);
                            ListView lvLoai2 = viewPager.getChildAt(viTri2).findViewById(R.id.lvLoaiThu);
                            LoaiAdapter loaiAdapter2 = new LoaiAdapter(context, dsLoai2, dsViTri2, viewPager);
                            lvLoai.setAdapter(loaiAdapter);
                            lvLoai2.setAdapter(loaiAdapter2);
                            Toast.makeText(view.getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });

                khungAnh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ThuVien.class);
                        Bundle bundleSV = new Bundle();
                        bundleSV.putInt("vitriloai", dsViTri.get(i));
                        intent.putExtra("boxloai", bundleSV);
                        ((Activity)context).startActivityForResult(intent, 888);
                        dialog.dismiss();
                    }
                });
            }
        });

        return view;
    }

    public class ViewHolder{
        ImageView anh;
        TextView ten, phanLoai;
        ImageButton edit, delete;
    }
}
