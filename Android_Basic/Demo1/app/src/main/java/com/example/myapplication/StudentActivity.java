package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import controller.ClazzController;
import controller.StudentController;
import helper.SpinnerAdapterHelper;
import model.Clazz;
import model.Student;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StudentActivity extends AppCompatActivity {

    private Spinner spinnerClassCode;
    private EditText txtStudentName;
    private EditText txtStudentDob;
    private Button btnSaveStudent;
    private ListView listViewStudents;

    private StudentController studentController;
    private ClazzController clazzController;
    private List<Student> arrayList = new ArrayList<Student>();
    private ArrayAdapter<Student> listViewAdapter;
    private SpinnerAdapterHelper adapterHelper;

    private Clazz selectedClazz;

    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            myCalendar.set(Calendar.YEAR, i);
            myCalendar.set(Calendar.MONTH, i1);
            myCalendar.set(Calendar.DAY_OF_MONTH, i2);
            updateLabel();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        this.spinnerClassCode = (Spinner) findViewById(R.id.spinnerClassCode);
        this.txtStudentName = (EditText) findViewById(R.id.txtStudentName);
        this.txtStudentDob = (EditText) findViewById(R.id.txtStudentDob);
        this.btnSaveStudent = (Button) findViewById(R.id.btnSaveStudent);
        this.listViewStudents = (ListView) findViewById(R.id.listViewStudents);

        this.studentController = new StudentController(this);
        this.clazzController = new ClazzController(this);

        List<Clazz> clazzList = this.clazzController.get();
        this.adapterHelper = new SpinnerAdapterHelper(StudentActivity.this,
                android.R.layout.simple_spinner_item, clazzList.toArray(new Clazz[0]));
        this.spinnerClassCode.setAdapter(adapterHelper);
        this.spinnerClassCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedClazz = adapterHelper.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.loadStudents();

        this.btnSaveStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveStudent();
            }
        });
        this.txtStudentDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(StudentActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void saveStudent() {
        String classCode = this.selectedClazz.getClassCode();
        String name = this.txtStudentName.getText().toString();
        String dob = this.txtStudentDob.getText().toString();
        Student student = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = formatter.parse(dob);
            Log.i("Studnent: ", ">>>>>" + date);
            student = new Student(classCode, name, date, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.studentController.add(student);
        this.loadStudents();
    }

    private void loadStudents() {
        List<Student> studentList = this.studentController.get();
        this.listViewAdapter = new ArrayAdapter<Student>(this,
                android.R.layout.simple_list_item_1, studentList);
        this.listViewStudents.setAdapter(this.listViewAdapter);
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        txtStudentDob.setText(sdf.format(myCalendar.getTime()));
    }
}