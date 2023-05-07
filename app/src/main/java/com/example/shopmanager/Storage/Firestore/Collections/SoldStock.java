package com.example.shopmanager.Storage.Firestore.Collections;

public class SoldStock {
    private String stock_id;
    private int size;
    private String color;
    private float price;

    public SoldStock(String stock_id, int size, String color, float price) {
        this.stock_id = stock_id;
        this.size = size;
        this.color = color;
        this.price = price;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SoldStock{" +
                "stock_id='" + stock_id + '\'' +
                ", size=" + size +
                ", color='" + color + '\'' +
                ", price=" + price +
                '}';
    }
}
