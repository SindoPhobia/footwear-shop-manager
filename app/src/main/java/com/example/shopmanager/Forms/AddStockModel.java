package com.example.shopmanager.Forms;

public class AddStockModel {
    private String name;
    private String brand;
    private String category;
    private String size;

    private int id;

    private float price;
    private boolean saleEnabled;
    private float salePrice;

    public AddStockModel(String name, String brand, String category, String size, int id, float price, boolean saleEnabled, float salePrice) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.size = size;
        this.id = id;
        this.price = price;
        this.saleEnabled = saleEnabled;
        this.salePrice = salePrice;
    }

    public String getName() { return name; }
    public String getBrand() { return brand; }
    public String getCategory() { return category; }
    public String getSize() { return size; }
    public int getId() { return id; }
    public float getPrice() { return price; }
    public boolean isSaleEnabled() { return saleEnabled; }
    public float getSalePrice() { return salePrice; }
}
