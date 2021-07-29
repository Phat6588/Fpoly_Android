package com.example.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product {
    private Integer id, category_id;
    private Double price;
    private String product_name, image_url;

    public Product() {
    }

    public Product(Integer id, Integer category_id, Double price, String product_name, String image_url) {
        this.id = id;
        this.category_id = category_id;
        this.price = price;
        this.product_name = product_name;
        this.image_url = image_url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

}
