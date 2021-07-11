package com.example.demoapptodo.DAO;

import com.example.demoapptodo.Models.Tasks;

import java.util.List;

public interface ITasks {
    List<Tasks> get(); // select * from tasks
    Tasks get(String id); // select * from tasks where id =
    boolean insert(Tasks tasks); // insert into tasks values()
    boolean delete(String id);
}