package com.example.shopmanager.Sales;

import java.util.Arrays;

public class SaleDisplayModel {

    private int id;
    private String seller;
    private long date;
    private StockDisplayModel[] stock;

    public SaleDisplayModel(int id, String seller, long date, StockDisplayModel[] stock) {
        this.id = id;
        this.seller = seller;
        this.date = date;
        this.stock = stock;
    }

    public int getId() { return id; }
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

        private int id;
        private String name;
        private String brand;
        private String color;
        private String size;
        private float price;

        public StockDisplayModel(int id, String name, String brand, String color, String size, float price) {
            this.id = id;
            this.name = name;
            this.brand = brand;
            this.color = color;
            this.size = size;
            this.price = price;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getBrand() { return brand; }
        public String getColor() { return color; }
        public String getSize() { return size; }
        public float getPrice() { return price; }

        @Override
        public String toString() {
            return "StockDisplayModel{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", brand='" + brand + '\'' +
                    ", color='" + color + '\'' +
                    ", size='" + size + '\'' +
                    ", price=" + price +
                    '}';
        }
    }
}
