package com.example.shopmanager.Storage.RoomApi;

import java.util.Arrays;
import java.util.HashMap;
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

    private byte[] img;

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    private String sizes;
    private int gender;

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public Shoe(){}


    public Shoe(int id, String name, String code, long date, float price, boolean sale_enabled, float sale_price, String category, String brand, String color, String sizes, int gender) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.date = date;
        this.price = price;
        this.sale_enabled = sale_enabled;
        this.sale_price = sale_price;
        this.category = category;
        this.brand = brand;
        this.color = color;
        this.sizes = sizes;
        this.gender = gender;
    }

    private static final String SIZE_SEPARATOR = ",";
    private static final String AMOUNT_SEPARATOR = "-";

/*
"42-3,44-4,45-6"
"41-5,42-5,45-2"
"39-2,44-6"
"42-6,43-10"
"38-6,39-5,40-4,41-3,42-3,44-10"
"42-3,44-4,45-6,41-8"
 */


    public HashMap<String, Integer> getSizesFormatted(){
        HashMap<String, Integer> data = new HashMap<>();
        String[] sizes = this.sizes.split(SIZE_SEPARATOR);
        for(String size : sizes){
            String[] sizeData = size.split(AMOUNT_SEPARATOR);
            data.put(sizeData[0], Integer.parseInt(sizeData[1]));
        }
        return data;
    }

    public String parseSizes(HashMap<String, Integer> sizes){
        return sizes.entrySet().stream().map((item) -> item.getKey()+AMOUNT_SEPARATOR+item.getValue()).reduce((a,b) -> a+SIZE_SEPARATOR+b).get();
    }

    public void addSize(String size, int amount){
        editSizeCount(size, amount);
    }

    public void removeSize(String size){
        HashMap<String, Integer> data = getSizesFormatted();
        data.remove(size);
        this.sizes = parseSizes(data);
    }

    public void editSizeCount(String size, int newCount){
        HashMap<String, Integer> data = getSizesFormatted();
        data.put(size, newCount);
        this.sizes = parseSizes(data);
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

    public static int getGenderCode(String text){
        switch(text){
            case "Male":
                return 0;
            case "Female":
                return 1;
            case "Unisex":
                return 2;
            default:
                return -1;
        }
    }

    public static String getGender(int gender) {
        switch (gender) {
            case 0:
                return "Male";
            case 1:
                return "Female";
            case 2:
                return "Unisex";
            default:
                return "Null";
        }
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
        return sale_enabled || this.getSale_price() > 0.f;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", date=" + date +
                ", price=" + price +
                ", sale_enabled=" + sale_enabled +
                ", sale_price=" + sale_price +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", sizes='" + sizes + '\'' +
                ", gender=" + gender +
                '}';
    }
}
