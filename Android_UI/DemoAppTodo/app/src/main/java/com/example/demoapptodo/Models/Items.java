package com.example.demoapptodo.Models;

public class Items {
    private String id, name, taskId;
    private Boolean status;

    public Items() {
    }

    public Items(String id, String name, String taskId, Boolean status) {
        this.id = id;
        this.name = name;
        this.taskId = taskId;
        this.status = status;
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
