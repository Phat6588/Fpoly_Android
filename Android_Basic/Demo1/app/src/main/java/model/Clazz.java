package model;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Clazz implements Serializable {
    private String classCode;
    private String className;

    public Clazz() {
    }

    public Clazz(String classCode, String className) {
        this.classCode = classCode;
        this.className = className;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getClassName() + " - " + this.getClassCode();
    }
}
