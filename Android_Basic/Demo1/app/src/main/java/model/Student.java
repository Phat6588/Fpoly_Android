package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;

public class Student {

    private String clazzCode;
    private String name;
    private Date dob;
    private int studentId;

    public Student() {
    }

    public Student(String clazzCode, String name, Date dob, int studentId) {
        this.clazzCode = clazzCode;
        this.name = name;
        this.dob = dob;
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getClazzCode() {
        return clazzCode;
    }

    public void setClazzCode(String clazzCode) {
        this.clazzCode = clazzCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @NonNull
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return this.getName() + " - " + this.getClazzCode() + " - " + sdf.format(this.getDob());
    }
}
