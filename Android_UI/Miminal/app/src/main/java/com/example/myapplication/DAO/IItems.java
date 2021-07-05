package com.example.myapplication.DAO;

import com.example.myapplication.Models.Items;

import java.util.List;

public interface IItems {
    List<Items> getByTaskId(String id);
    Items get(String id);
    boolean insert(Items item);
    boolean update(Items item);
    boolean delete(int id);
}
