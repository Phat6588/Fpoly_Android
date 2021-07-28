package com.example.myapplication.DAO;

import com.example.myapplication.Models.IncomeExpense;

import java.util.List;

public interface IIncomeExpense {
    List<IncomeExpense> get();
    List<IncomeExpense> get(int flag);
    void insert(IncomeExpense incomeExpense);
    void update(IncomeExpense incomeExpense);
    void delete(Integer id);
}
