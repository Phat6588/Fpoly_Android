package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.Adapter.LopAdapter;
import com.example.assignment.Adapter.SVAdapter;
import com.example.assignment.Model.Lop;
import com.example.assignment.Model.SinhVien;
import com.example.assignment.SQLite.LopDAO;
import com.example.assignment.SQLite.SinhVienDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.DataFormatException;

public class SinhVienActivity extends AppCompatActivity {
    ListView lvSV;
    SinhVienDAO sinhVienDAO;
    LopDAO lopDAO;
    ArrayList<SinhVien> dssv= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinh_vien);
        lvSV=findViewById(R.id.lvSV);
        sinhVienDAO=new SinhVienDAO(SinhVienActivity.this);
        dssv=sinhVienDAO.getAllSV();
        if(dssv.size()==0){
            Toast.makeText(SinhVienActivity.this, "Danh sách trống",Toast.LENGTH_SHORT).show();
        }
        else {
            SVAdapter svAdapter=new SVAdapter(SinhVienActivity.this,dssv,sinhVienDAO);
            lvSV.setAdapter(svAdapter);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_sv,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_them_sv){
            ThemSV();
        }
        else if(item.getItemId()==R.id.menu_tim_sv){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            View view= LayoutInflater.from(this).inflate(R.layout.dialog_tim_sv,null);
            builder.setView(view);
            EditText TimSV=view.findViewById(R.id.etTimSV);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try{
                        int id=Integer.parseInt(TimSV.getText().toString());
                        TimSV(id);
                    }catch (Exception ex){
                        Toast.makeText(SinhVienActivity.this, "Nhập sai định dạng",Toast.LENGTH_SHORT).show();
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
        return super.onOptionsItemSelected(item);
    }
    public void CapNhatSV(){
        dssv=sinhVienDAO.getAllSV();
        SVAdapter svAdapter=new SVAdapter(SinhVienActivity.this,dssv,sinhVienDAO);
        lvSV.setAdapter(svAdapter);
    }
    public void ThemSV(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.dialog_them_sv,null);
        builder.setView(view);
        TextView ThemTenSV=view.findViewById(R.id.etThemTenSV);
        EditText ThemNgaySinhSV=view.findViewById(R.id.etThemNgaySinhSV);
        ThemNgaySinhSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int ngay=calendar.get(Calendar.DATE);
                int thang=calendar.get(Calendar.MONTH);
                int nam=calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(SinhVienActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                        ThemNgaySinhSV.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },nam,thang,ngay);
                datePickerDialog.show();
            }
        });
        lopDAO=new LopDAO(SinhVienActivity.this);
        ArrayList<Lop> dslop=lopDAO.getAllLop();
        ArrayList<String> arrayList=new ArrayList<String>();
        for(int i=0;i<dslop.size();i++){
            arrayList.add(dslop.get(i).getTen_lop());
        }
        Spinner spinnerthemsv=view.findViewById(R.id.spinnerThemSV);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(SinhVienActivity.this, android.R.layout.simple_spinner_dropdown_item,arrayList);
        spinnerthemsv.setAdapter(adapter);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int vitri = 0;
                for(int i=0;i<dslop.size();i++){
                    if(spinnerthemsv.getSelectedItemPosition()==i){
                        vitri=dslop.get(i).getId_lop();
                        break;
                    }
                }

                try{
                    if (ThemTenSV.getText().toString().equals("")||ThemNgaySinhSV.getText().toString().equals("")||vitri==0){
                        Toast.makeText(SinhVienActivity.this,"Không được để trống, thêm thất bại",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        try {
                            SimpleDateFormat a=new SimpleDateFormat();
                            a.applyPattern("dd/MM/yyyy");
                            a.parse(ThemNgaySinhSV.getText().toString());
                            sinhVienDAO=new SinhVienDAO(SinhVienActivity.this);
                            sinhVienDAO.ThemSV(ThemTenSV.getText().toString(),ThemNgaySinhSV.getText().toString(),vitri);
                            dssv=sinhVienDAO.getAllSV();
                            SVAdapter svAdapter=new SVAdapter(SinhVienActivity.this,dssv,sinhVienDAO);
                            lvSV.setAdapter(svAdapter);
                            Toast.makeText(SinhVienActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                        }catch (ParseException ex) {
                            Toast.makeText(SinhVienActivity.this,"Ngày không đúng định dạng",Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception ex){
                    Toast.makeText(SinhVienActivity.this,"Thêm thất bại",Toast.LENGTH_SHORT).show();
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
    public void TimSV(int id){
        int dem=0;
        try{
            for(int i=0;i<dssv.size();i++){
                if(id==dssv.get(i).getId_sv()){
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    View view= LayoutInflater.from(this).inflate(R.layout.dialog_thong_tin_sv,null);
                    builder.setView(view);
                    TextView ThongTinMaSV=view.findViewById(R.id.tvThongTinMaSV);
                    ThongTinMaSV.setText("Mã sinh viên: "+dssv.get(i).getId_sv());
                    TextView ThongTinTenSV=view.findViewById(R.id.tvThongTinTenSV);
                    ThongTinTenSV.setText("Tên sinh viên: "+dssv.get(i).getTen_sv());
                    TextView ThongTinNgaySinhSV=view.findViewById(R.id.tvThongTinNgaySinhSV);
                    ThongTinNgaySinhSV.setText("Ngày sinh: "+dssv.get(i).getNgay_sinh_sv());
                    TextView ThongTinTenLopSV=view.findViewById(R.id.tvThongTinTenLopSV);
                    lopDAO=new LopDAO(SinhVienActivity.this);
                    ArrayList<Lop> dslop =lopDAO.getAllLop();
                    for(int j=0;j<dslop.size();j++){
                        if(dssv.get(i).getId_lopsv()==dslop.get(j).getId_lop()){
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
                    Toast.makeText(SinhVienActivity.this, "Tìm kiếm thành công",Toast.LENGTH_SHORT).show();
                    dem++;
                    break;
                }
            }
            if(dem==0){
                Toast.makeText(SinhVienActivity.this, "Không tìm thấy",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            Toast.makeText(SinhVienActivity.this, "Tìm kiếm thất bại",Toast.LENGTH_SHORT).show();
        }
    }
}