package com.example.demoapptodo.DAO;

import com.example.demoapptodo.Models.Items;
import com.example.demoapptodo.Models.Tasks;

import java.util.List;

public interface IItems {
    List<Items> getByTaskId(String taskId); // select * from items where taskId =
    Items get(String id); // select * from items where id =
    boolean insert(Items items); // insert into items values()
    boolean update(Items items); // update items set .... where id =
    boolean delete(String id);
}
