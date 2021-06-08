package com.example.demoslide56768.DAO;

import com.example.demoslide56768.Model.Laptop;

import java.util.List;

public interface ILaptopDAO {
    // 4 ham
    List<Laptop> get();
    Laptop get(String laptopId);
    void insert(Laptop laptop);
    void update(Laptop laptop);
    void delete(String laptopId);

    // dependency injection
}
