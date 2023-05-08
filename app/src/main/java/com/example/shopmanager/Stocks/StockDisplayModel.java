package com.example.shopmanager.Stocks;

import com.example.shopmanager.MainActivity;
import com.example.shopmanager.Sales.SaleDisplayModel;
import com.example.shopmanager.Storage.RoomApi.Shoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StockDisplayModel {
    private String name;
    private String brand;
    private String category;

    private String code;

    private int id;

    private float price;
    private boolean saleEnabled;
    private float salePrice;

    private Map<String, Integer> sizes;
    private int totalStock;

    private long date;

    public static StockDisplayModel[] parseStockToDisplayModel(ArrayList<Shoe> shoes){
        return shoes.stream().map(item ->
                        new StockDisplayModel(
                                item.getId(),
                                item.getCode(),
                                item.getName(),
                                item.getBrand(),
                                item.getCategory(),
                                item.getPrice(),
                                item.isSale_enabled(),
                                item.getSale_price(),
                                item.getSizesFormatted(),
                                item.getDate()
                        )
                ).toArray(StockDisplayModel[]::new);
    }

    public StockDisplayModel(int id, String code,
            String name, String brand, String category,
            float price, boolean saleEnabled, float salePrice,
            Map<String, Integer> sizes, long date
    ) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.saleEnabled = saleEnabled;
        this.salePrice = salePrice;
        this.sizes = sizes;
        this.date = date;
        this.totalStock = sizes.values().stream().reduce(0, (a,b) -> a+b);
    }

    public String getCode(){return this.code;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
