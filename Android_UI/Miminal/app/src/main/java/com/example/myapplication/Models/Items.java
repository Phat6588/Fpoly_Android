package com.example.myapplication.Models;

import java.util.Date;

public class Items {

    private String id;
    private String name;
    private boolean status;
    private String taskId;

    public Items() {
    }

    public Items(String id, String name, boolean status, String taskId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.taskId = taskId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
