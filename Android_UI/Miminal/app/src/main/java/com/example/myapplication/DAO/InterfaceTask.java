package com.example.myapplication.DAO;

import com.example.myapplication.Models.TodoTask;

import java.util.List;

public interface InterfaceTask {
    public List<TodoTask> get();
    public TodoTask get(int id);
    public boolean insert(TodoTask item);
    public boolean update(TodoTask item);
    public boolean delete(int id);
}
