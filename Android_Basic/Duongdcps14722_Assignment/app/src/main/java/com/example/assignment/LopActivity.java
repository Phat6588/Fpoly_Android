package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.Adapter.LopAdapter;
import com.example.assignment.Model.Lop;
import com.example.assignment.SQLite.LopDAO;
import com.example.assignment.SQLite.MyDatabase;

import java.util.ArrayList;

public class LopActivity extends AppCompatActivity {
    ListView lvLop;
    LopDAO lopDAO;
    ArrayList<Lop> dsLop= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lop);
        lvLop=findViewById(R.id.lvLop);
        lopDAO=new LopDAO(LopActivity.this);
        dsLop=lopDAO.getAllLop();
        if(dsLop.size()==0){
            Toast.makeText(LopActivity.this, "Danh sách trống",Toast.LENGTH_SHORT).show();
        }
        else {
            LopAdapter lopAdapter=new LopAdapter(LopActivity.this,dsLop,lopDAO);
            lvLop.setAdapter(lopAdapter);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_lop,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_them_lop){
            ThemLop();
        }
        return super.onOptionsItemSelected(item);
    }
    public void CapNhatLop(){
        dsLop=lopDAO.getAllLop();
        LopAdapter lopAdapter=new LopAdapter(LopActivity.this,dsLop,lopDAO);
        lvLop.setAdapter(lopAdapter);
    }

    public boolean CheckTenLop(String tenlop){
        boolean check=false;
        for(int i=0;i<dsLop.size();i++){
            if(tenlop.equalsIgnoreCase(dsLop.get(i).getTen_lop())){
                check=true;
                break;
            }
        }
        return  check;
    }

    public void ThemLop(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.dialog_them_lop,null);
        builder.setView(view);
        TextView ThemTenLop=view.findViewById(R.id.etThemTenLop);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    if (ThemTenLop.getText().toString().equals("")){
                        Toast.makeText(LopActivity.this,"Không được để trống, thêm thất bại",Toast.LENGTH_SHORT).show();
                    }
                    else if(CheckTenLop(ThemTenLop.getText().toString())){
                        Toast.makeText(LopActivity.this,"Trùng tên lớp, thêm thất bại",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        lopDAO=new LopDAO(LopActivity.this);
                        lopDAO.ThemLop(ThemTenLop.getText().toString());
                        CapNhatLop();
                        Toast.makeText(LopActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception ex){
                    Toast.makeText(LopActivity.this,"Thêm thất bại",Toast.LENGTH_SHORT).show();
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
}