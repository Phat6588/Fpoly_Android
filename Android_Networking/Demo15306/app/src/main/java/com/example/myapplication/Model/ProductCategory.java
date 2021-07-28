package com.example.myapplication.Model;

public class ProductCategory {
    private Integer id;
    private String category_name;

    public ProductCategory() {
    }

    public ProductCategory(Integer id, String category_name) {
        this.id = id;
        this.category_name = category_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
