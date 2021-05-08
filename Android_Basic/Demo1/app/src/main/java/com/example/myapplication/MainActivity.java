package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    private Button btnAddClass;
    private Button btnViewClass;
    private Button btnStudentManagement;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnAddClass = (Button) findViewById(R.id.btnAddClass);
        this.btnViewClass = (Button) findViewById(R.id.btnViewClass);
        this.btnStudentManagement = (Button) findViewById(R.id.btnStudentManagement);

        this.btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAddClassClick();
            }
        });

        this.btnViewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnViewClassClick();
            }
        });

        this.btnStudentManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStudentManagementClick();
            }
        });
    }

    private void btnStudentManagementClick() {
        Intent intent = new Intent(this, StudentActivity.class);
        this.startActivity(intent);
    }

    private void btnViewClassClick() {
        Intent intent = new Intent(this, ClazzListActivity.class);
        this.startActivity(intent);
    }

    private void btnAddClassClick() {
        final AddClassDialog dialog = new AddClassDialog(this);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


}