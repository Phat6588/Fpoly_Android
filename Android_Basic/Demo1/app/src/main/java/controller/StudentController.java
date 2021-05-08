package controller;

import android.content.Context;

import java.util.List;

import androidx.annotation.Nullable;
import helper.MyDbHelper;
import model.Clazz;
import model.Student;

public class StudentController {
    MyDbHelper myDbHelper;
    public StudentController(@Nullable Context context){
        myDbHelper = new MyDbHelper(context, 6);
    }

    public void add(Student st){
        myDbHelper.addStudent(st);
    }

    public List<Student> get(){
        return myDbHelper.getStudent();
    }
}
