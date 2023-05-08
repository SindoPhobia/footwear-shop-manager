package com.example.shopmanager.Stocks;

import java.util.Map;

public class StockDisplayModel {
    private String name;
    private String brand;
    private String category;

    private float price;
    private boolean saleEnabled;
    private float salePrice;

    private Map<String, Integer> sizes;
    private int totalStock;

    private long date;

    public StockDisplayModel(
            String name, String brand, String category,
            float price, boolean saleEnabled, float salePrice,
            Map<String, Integer> sizes, int totalStock, long date
    ) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.saleEnabled = saleEnabled;
        this.salePrice = salePrice;
        this.sizes = sizes;
        this.date = date;
        this.totalStock = totalStock;
    }

    public String getName() { return name; }
    public String getBrand() { return brand; }
    public String getCategory() { return category; }

    public float getPrice() { return price; }
    public boolean isSaleEnabled() { return saleEnabled; }
    public float getSalePrice() { return salePrice; }

    public Map<String, Integer> getSizes() { return sizes; }
    public int getTotalStock() { return totalStock; }
    public long getDate() { return date; }

    @Override
    public String toString() {
        return "StockDisplayModel{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", saleEnabled=" + saleEnabled +
                ", salePrice=" + salePrice +
                ", sizes='" + sizes + '\'' +
                ", totalStock=" + totalStock +
                ", date=" + date +
                '}';
    }
}
