package com.example.myapplication.DAO;

import com.example.myapplication.Models.TodoItem;

import java.util.List;

public interface InterfaceItem {
    public List<TodoItem> getByTaskId(int id);
    public TodoItem get(int id);
    public boolean insert(TodoItem item);
    public boolean update(TodoItem item);
    public boolean delete(int id);
}
