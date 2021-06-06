package com.example.assignment.Adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.assignment.LopActivity;
import com.example.assignment.Model.Lop;
import com.example.assignment.Model.SinhVien;
import com.example.assignment.R;
import com.example.assignment.SQLite.LopDAO;
import com.example.assignment.SQLite.SinhVienDAO;
import com.example.assignment.SinhVienActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SVAdapter extends BaseAdapter {
    public Context c;
    public ArrayList<SinhVien> dssv=new ArrayList<SinhVien>();
    SinhVienDAO sinhVienDAO;
    public SVAdapter(Context c,ArrayList<SinhVien> dssv,SinhVienDAO sinhVienDAO){
        this.c=c;
        this.dssv=dssv;
        this.sinhVienDAO=sinhVienDAO;
    }
    @Override
    public int getCount() {
        return dssv.size();
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
            mot_o= new View_mot_o();
            convertView=inf.inflate(R.layout.sinhvien,null);
            mot_o.masv=convertView.findViewById(R.id.tv_layout_masv);
            mot_o.tensv= convertView.findViewById(R.id.tv_layout_tensv);
            mot_o.malopsv=convertView.findViewById(R.id.tv_layout_malopsv);
            mot_o.suasv=convertView.findViewById(R.id.btnSuaSV);
            mot_o.xoasv=convertView.findViewById(R.id.btnXoaSV);
            mot_o.xemsv=convertView.findViewById(R.id.btnXemSV);
            convertView.setTag(mot_o);
        }
        else{
            mot_o=(SVAdapter.View_mot_o)convertView.getTag();
        }
        mot_o.masv.setText("Mã sinh viên: "+dssv.get(position).getId_sv());
        mot_o.tensv.setText("Tên sinh viên: "+dssv.get(position).getTen_sv());
        mot_o.malopsv.setText("Mã lớp: "+dssv.get(position).getId_lopsv());
        mot_o.xoasv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XoaSV(position);
            }
        });
        mot_o.suasv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuaSV(position);
            }
        });
        mot_o.xemsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongTinSV(position);
            }
        });
        return convertView;
    }
    public class View_mot_o{
        public TextView masv;
        public TextView tensv;
        public TextView malopsv;
        public ImageView suasv;
        public ImageView xoasv;
        public ImageView xemsv;
    }
    public void XoaSV(int position){
        for(int i=0;i<dssv.size();i++){
            try {
                if(position==i){
                    AlertDialog.Builder builder=new AlertDialog.Builder(c);
                    View view= LayoutInflater.from(c).inflate(R.layout.dialog_xac_nhan,null);
                    TextView xacnhan=view.findViewById(R.id.tvXacNhan);
                    xacnhan.setText("XÁC NHẬN XÓA SINH VIÊN");
                    builder.setView(view);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sinhVienDAO.XoaSV(dssv.get(position).getId_sv());
                            ((SinhVienActivity)c).CapNhatSV();
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
            }catch (Exception ex){
                Toast.makeText(c,"Xóa thất bại",Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }
    public void SuaSV(int position){
        for(int i=0;i<dssv.size();i++){
            try {
                if(position==i){
                    AlertDialog.Builder builder=new AlertDialog.Builder(c);
                    View view= LayoutInflater.from(c).inflate(R.layout.dialog_sua_sv,null);
                    builder.setView(view);
                    TextView tvSuaMaSV=view.findViewById(R.id.tvSuaMaSV);
                    EditText etSuaTenSV=view.findViewById(R.id.etSuaTenSV);
                    EditText SuaNgaySinhSV=view.findViewById(R.id.etSuaNgaySinhSV);
                    SuaNgaySinhSV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar calendar=Calendar.getInstance();
                            int ngay=calendar.get(Calendar.DATE);
                            int thang=calendar.get(Calendar.MONTH);
                            int nam=calendar.get(Calendar.YEAR);
                            DatePickerDialog datePickerDialog=new DatePickerDialog(c, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    calendar.set(year,month,dayOfMonth);
                                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                                    SuaNgaySinhSV.setText(simpleDateFormat.format(calendar.getTime()));
                                }
                            },nam,thang,ngay);
                            datePickerDialog.show();
                        }
                    });
                    LopDAO lopDAO = new LopDAO(c);
                    ArrayList<Lop> dslop=lopDAO.getAllLop();
                    ArrayList<String> arrayList=new ArrayList<String>();
                    for(int k=0;k<dslop.size();k++){
                        arrayList.add(dslop.get(k).getTen_lop());
                    }
                    Spinner spinnersuasv=view.findViewById(R.id.spinnerSuaSV);
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(c, android.R.layout.simple_spinner_dropdown_item,arrayList);
                    spinnersuasv.setAdapter(adapter);
                    tvSuaMaSV.setText("Mã sinh viên:"+dssv.get(position).getId_sv());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int vitri = 0;
                            for(int i=0;i<dslop.size();i++){
                                if(spinnersuasv.getSelectedItemPosition()==i){
                                    vitri=dslop.get(i).getId_lop();
                                    break;
                                }
                            }
                            if(etSuaTenSV.getText().toString().equals("")||SuaNgaySinhSV.getText().toString().equals("")||vitri==0)
                                Toast.makeText(c,"Không được để trống, sửa thất bại",Toast.LENGTH_SHORT).show();
                            else{
                                try{
                                    SimpleDateFormat a=new SimpleDateFormat();
                                    a.applyPattern("dd/MM/yyyy");
                                    a.parse(SuaNgaySinhSV.getText().toString());
                                    sinhVienDAO.SuaSV(dssv.get(position).getId_sv(),etSuaTenSV.getText().toString(),SuaNgaySinhSV.getText().toString(),vitri);
                                    ((SinhVienActivity)c).CapNhatSV();
                                    Toast.makeText(c,"Sửa thành công!",Toast.LENGTH_SHORT).show();
                                }catch (ParseException ex) {
                                    Toast.makeText(c,"Ngày không đúng định dạng!",Toast.LENGTH_SHORT).show();
                                }

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
                break;
            }

        }
    }
    public void ThongTinSV(int position){
        for(int i=0;i<dssv.size();i++){
            try {
                if(position==i){
                    AlertDialog.Builder builder=new AlertDialog.Builder(c);
                    View view= LayoutInflater.from(c).inflate(R.layout.dialog_thong_tin_sv,null);
                    builder.setView(view);
                    TextView ThongTinMaSV=view.findViewById(R.id.tvThongTinMaSV);
                    ThongTinMaSV.setText("Mã sinh viên: "+dssv.get(position).getId_sv());
                    TextView ThongTinTenSV=view.findViewById(R.id.tvThongTinTenSV);
                    ThongTinTenSV.setText("Tên sinh viên: "+dssv.get(position).getTen_sv());
                    TextView ThongTinNgaySinhSV=view.findViewById(R.id.tvThongTinNgaySinhSV);
                    ThongTinNgaySinhSV.setText("Ngày sinh: "+dssv.get(position).getNgay_sinh_sv());
                    TextView ThongTinTenLopSV=view.findViewById(R.id.tvThongTinTenLopSV);
                    LopDAO lopDAO=new LopDAO(c);
                    ArrayList<Lop> dslop=lopDAO.getAllLop();
                    for(int j=0;j<dslop.size();j++){
                        if(dssv.get(position).getId_lopsv()==dslop.get(j).getId_lop()){
                            ThongTinTenLopSV.setText("Tên lớp: "+dslop.get(j).getTen_lop());
                            break;
                        }
                    }
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
            }catch (Exception ex){
                Toast.makeText(c,"Xem thất bại",Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }

}
