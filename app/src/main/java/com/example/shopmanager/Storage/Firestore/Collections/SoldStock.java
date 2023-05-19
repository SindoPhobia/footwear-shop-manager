package com.example.shopmanager.Storage.Firestore.Collections;

public class SoldStock {
    private int stock_id;
    private String size;
    private float price;


    public SoldStock(){}

    public SoldStock(int stock_id, String size, float price) {
        this.stock_id = stock_id;
        this.size = size;
        this.price = price;
    }

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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
                ", price=" + price +
                '}';
    }
}
