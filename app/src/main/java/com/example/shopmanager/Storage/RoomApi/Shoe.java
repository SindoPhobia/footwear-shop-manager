package com.example.shopmanager.Storage.RoomApi;

import java.util.Arrays;
import java.util.List;

public class Shoe {
    private int id;
    private String name;
    private String code;
    private long date;
    private float price;
    private boolean sale_enabled;
    private float sale_price;
    private String category;
    private String brand;
    private String color;

    private String sizes;

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    private int gender;

    private static final String SEPARATOR = ",";

    public List<Integer> getSizesFormatted(){
        return Arrays.asList(Arrays.stream(this.sizes.split(SEPARATOR)).map(size -> Integer.parseInt(size)).toArray(Integer[]::new));
    }

    public String parseSizes(List<Integer> sizes){
        return sizes.stream().map(item -> String.valueOf(item)).reduce((a,b) -> a+","+b).get();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isSale_enabled() {
        return sale_enabled;
    }

    public void setSale_enabled(boolean sale_enabled) {
        this.sale_enabled = sale_enabled;
    }

    public float getSale_price() {
        return sale_price;
    }

    public void setSale_price(float sale_price) {
        this.sale_price = sale_price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Shoe{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", date=" + date +
                ", price=" + price +
                ", sale_enabled=" + sale_enabled +
                ", sale_price=" + sale_price +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", gender=" + gender +
                '}';
    }
}
