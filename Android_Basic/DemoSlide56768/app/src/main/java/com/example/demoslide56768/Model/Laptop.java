package com.example.demoslide56768.Model;

public class Laptop {
    private String laptopId;
    private String laptopName;
    private Double laptopPrice;
    private String brandId;
    private byte[] laptopImage;

    public byte[] getLaptopImage() {
        return laptopImage;
    }

    public void setLaptopImage(byte[] laptopImage) {
        this.laptopImage = laptopImage;
    }

    public Laptop() {
    }

    public Laptop(String laptopId, String laptopName, Double laptopPrice, byte[] image, String brandId) {
        this.laptopId = laptopId;
        this.laptopName = laptopName;
        this.laptopPrice = laptopPrice;
        this.laptopImage = image;
        this.brandId = brandId;
    }

    public String getLaptopId() {
        return laptopId;
    }

    public void setLaptopId(String laptopId) {
        this.laptopId = laptopId;
    }

    public String getLaptopName() {
        return laptopName;
    }

    public void setLaptopName(String laptopName) {
        this.laptopName = laptopName;
    }

    public Double getLaptopPrice() {
        return laptopPrice;
    }

    public void setLaptopPrice(Double laptopPrice) {
        this.laptopPrice = laptopPrice;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }
}
