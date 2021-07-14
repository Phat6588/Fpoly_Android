package com.example.myapplication.DAO;

import com.example.myapplication.Models.Category;

import java.util.List;

public interface ICategoryDAO {
    // abstract method
    List<Category> get(); // select * from category
}
