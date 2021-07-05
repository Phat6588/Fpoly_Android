package com.example.myapplication.DAO;

import com.example.myapplication.Models.Tasks;

import java.util.List;

public interface ITasks {
    List<Tasks> get();
    Tasks get(int id);
    boolean insert(Tasks item);
    boolean update(Tasks item);
    boolean delete(int id);
}
