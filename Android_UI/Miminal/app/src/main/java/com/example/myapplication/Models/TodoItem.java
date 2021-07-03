package com.example.myapplication.Models;

import java.util.Date;

public class TodoItem {

    private int id;
    private String name;
    private boolean status;
    private String color;
    private int taskId;

    public TodoItem() {
    }

    public TodoItem(int id, String name, boolean status, String color, int taskId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.color = color;
        this.taskId = taskId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
