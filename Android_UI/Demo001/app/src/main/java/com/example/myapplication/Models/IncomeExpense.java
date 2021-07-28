package com.example.myapplication.Models;

import java.util.Date;

public class IncomeExpense {
    private Integer id, flag, categoryId;
    private String name, description;
    private Date createdDate;
    private Double amount;

    public IncomeExpense() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public IncomeExpense(Integer id, Integer flag, Integer categoryId, String name, String description, Date createdDate, Double amount) {
        this.id = id;
        this.flag = flag;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.amount = amount;
    }
}
