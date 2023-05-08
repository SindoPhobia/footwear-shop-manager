package com.example.shopmanager.Sales;

import java.util.Arrays;

public class SaleDisplayModel {

    private String id;
    private String seller;
    private long date;
    private StockDisplayModel[] stock;

    public SaleDisplayModel(String id, String seller, long date) {
        this.id = id;
        this.seller = seller;
        this.date = date;
    }

    public SaleDisplayModel(String id, String seller, long date, StockDisplayModel[] stock) {
        this.id = id;
        this.seller = seller;
        this.date = date;
        this.stock = stock;
    }

    public void setStockDisplayModel(StockDisplayModel[] stock){
        this.stock = stock;
    }

    public String getId() { return id; }
    public String getSeller() { return seller; }
    public long getDate() { return date; }
    public StockDisplayModel[] getStock() { return stock; }

    @Override
    public String toString() {
        return "SaleDisplayModel{" +
                "id=" + id +
                ", seller='" + seller + '\'' +
                ", date=" + date +
                ", stock=" + Arrays.toString(stock) +
                '}';
    }


    public static class StockDisplayModel {

        private int stock_id;
        private String name;
        private String brand;
        private String color;
        private String size;
        private float price;

        public StockDisplayModel(int stock_id, String name, String brand, String color, String size, float price) {
            this.stock_id = stock_id;
            this.name = name;
            this.brand = brand;
            this.color = color;
            this.size = size;
            this.price = price;
        }

        public int getStockId() { return stock_id; }
        public String getName() { return name; }
        public String getBrand() { return brand; }
        public String getColor() { return color; }
        public String getSize() { return size; }
        public float getPrice() { return price; }

        @Override
        public String toString() {
            return "StockDisplayModel{" +
                    "id=" + stock_id +
                    ", name='" + name + '\'' +
                    ", brand='" + brand + '\'' +
                    ", color='" + color + '\'' +
                    ", size='" + size + '\'' +
                    ", price=" + price +
                    '}';
        }
    }
}
