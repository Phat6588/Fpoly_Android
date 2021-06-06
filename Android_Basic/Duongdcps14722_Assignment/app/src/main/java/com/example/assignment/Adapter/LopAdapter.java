package com.example.assignment.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.assignment.LopActivity;
import com.example.assignment.Model.Lop;
import com.example.assignment.Model.SinhVien;
import com.example.assignment.R;
import com.example.assignment.SQLite.LopDAO;
import com.example.assignment.SQLite.SinhVienDAO;
import com.example.assignment.SVTrongLopActivity;

import java.util.ArrayList;

public class LopAdapter extends BaseAdapter {
    public Context c;
    public ArrayList<Lop> dslop=new ArrayList<Lop>();
    LopDAO lopDAO;
    public LopAdapter(Context c, ArrayList<Lop> dslop, LopDAO lopDAO){
        this.c=c;
        this.dslop=dslop;
        this.lopDAO=lopDAO;
    }
    @Override
    public int getCount() {
        return dslop.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View_mot_o mot_o;
        LayoutInflater inf = ((Activity)c).getLayoutInflater();
        if(convertView==null){
            mot_o=new View_mot_o();
            convertView=inf.inflate(R.layout.lop,null);
            mot_o.malop=convertView.findViewById(R.id.tv_layout_malop);
            mot_o.tenlop= convertView.findViewById(R.id.tv_layout_tenlop);
            mot_o.sualop=convertView.findViewById(R.id.btnSuaLop);
            mot_o.xoalop=convertView.findViewById(R.id.btnXoaLop);
            mot_o.xemlop=convertView.findViewById(R.id.btnXemLop);
            convertView.setTag(mot_o);
        }
        else{
            mot_o=(View_mot_o)convertView.getTag();
        }
        mot_o.malop.setText("Mã lớp: "+dslop.get(position).getId_lop());
        mot_o.tenlop.setText("Tên lớp: "+dslop.get(position).getTen_lop());
        mot_o.xoalop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XoaLop(position);
            }
        });
        mot_o.sualop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuaLop(position);
            }
        });
        mot_o.xemlop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XemLop(position);
            }
        });
        return convertView;
    }
    public class View_mot_o{
        public TextView malop;
        public TextView tenlop;
        public ImageView sualop;
        public ImageView xoalop;
        public ImageView xemlop;
    }
    public void XoaLop(int position){
        for(int i=0;i<dslop.size();i++){
            try {
                if(position==i){
                    SinhVienDAO sinhVienDAO=new SinhVienDAO(c);
                    ArrayList<SinhVien> dssv=sinhVienDAO.getAllSV();
                    boolean tontai=false;
                    for(int j=0;j<dssv.size();j++){
                        if(dssv.get(j).getId_lopsv()==dslop.get(position).getId_lop())
                            tontai=true;
                    }
                    if(tontai==false){
                        AlertDialog.Builder builder=new AlertDialog.Builder(c);
                        View view= LayoutInflater.from(c).inflate(R.layout.dialog_xac_nhan,null);
                        TextView xacnhan=view.findViewById(R.id.tvXacNhan);
                        xacnhan.setText("XÁC NHẬN XÓA LỚP");
                        builder.setView(view);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                lopDAO.XoaLop(dslop.get(position).getId_lop());
                                ((LopActivity)c).CapNhatLop();
                                Toast.makeText(c,"Xóa thành công!",Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.create().show();
                    }
                    else
                        Toast.makeText(c,"Có sinh viên trong lớp xóa thất bại!",Toast.LENGTH_SHORT).show();

                }
            }catch (Exception ex){
                Toast.makeText(c,"Xóa lớp thất bại",Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void SuaLop(int position){
        for(int i=0;i<dslop.size();i++){
            try {
                if(position==i){
                    AlertDialog.Builder builder=new AlertDialog.Builder(c);
                    View view= LayoutInflater.from(c).inflate(R.layout.dialog_sua_lop,null);
                    builder.setView(view);
                    TextView tvSuaMaLop=view.findViewById(R.id.tvSuaMaLop);
                    EditText etSuaTenLop=view.findViewById(R.id.etSuaTenLop);
                    tvSuaMaLop.setText("Mã lớp:"+dslop.get(position).getId_lop());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(etSuaTenLop.getText().toString().equals(""))
                                Toast.makeText(c,"Không được để trống,sửa thất bại",Toast.LENGTH_SHORT).show();
                            else if(CheckTenLop(etSuaTenLop.getText().toString())){
                                Toast.makeText(c,"Trùng tên lớp, sửa thất bại",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                lopDAO.SuaLop(dslop.get(position).getId_lop(),etSuaTenLop.getText().toString());
                                ((LopActivity)c).CapNhatLop();
                                Toast.makeText(c,"Sửa thành công!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
            }catch (Exception ex){
                Toast.makeText(c,"Sửa thất bại",Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void XemLop(int position){
        for(int i=0;i<dslop.size();i++){
            try {
                if(position==i){
                    Intent intent=new Intent(c, SVTrongLopActivity.class);
                    intent.putExtra("tenlop",dslop.get(position).getTen_lop());
                    SinhVienDAO sinhVienDAO=new SinhVienDAO(c);
                    ArrayList<SinhVien> dssv=sinhVienDAO.getAllSV();
                    ArrayList<String> dstensv = new ArrayList<String>();
                    for(SinhVien x:dssv){
                        if(x.getId_lopsv()==dslop.get(position).getId_lop())
                            dstensv.add("Mã sinh viên: "+x.getId_sv()+"\n"+"Tên sinh viên: "+x.getTen_sv());
                    }
                    intent.putExtra("dstensv",dstensv);
                    ((Activity)c).startActivity(intent);
                }
            }catch (Exception ex){
                Toast.makeText(c,"Xem lớp thất bại",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean CheckTenLop(String tenlop){
        boolean check=false;
        for(int i=0;i<dslop.size();i++){
            if(tenlop.equalsIgnoreCase(dslop.get(i).getTen_lop())){
                check=true;
                break;
            }
        }
        return  check;
    }
}
