package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import controller.ClazzController;
import helper.MyDbHelper;
import model.Clazz;

public class AddClassDialog extends Dialog {
    public Context context;

    private EditText txtClassCode;
    private EditText txtClassName;
    private Button btnClear;
    private Button btnSave;

    private ClazzController controller;

    public AddClassDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        this.controller = new ClazzController(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_class_dialog);

        this.txtClassCode = (EditText) findViewById(R.id.txtClassCode);
        this.txtClassName = (EditText) findViewById(R.id.txtClassName);
        this.btnClear = (Button) findViewById(R.id.btnClear);
        this.btnSave = (Button) findViewById(R.id.btnSave);

        this.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClearClick();
            }
        });

        this.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSaveClick();
            }
        });
    }

    private void btnClearClick() {
        this.txtClassCode.setText("");
        this.txtClassName.setText("");
    }

    private void btnSaveClick() {
        String code = this.txtClassCode.getText().toString();
        String name = this.txtClassName.getText().toString();
        if (code.equals("") || name.equals("")){
            Toast.makeText(null, "Sai roi", Toast.LENGTH_LONG).show();
            return;
        }
        Clazz clazz = new Clazz(code, name);
        this.controller.add(clazz);
        this.dismiss();
    }
}
