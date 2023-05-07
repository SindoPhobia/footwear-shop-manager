package com.example.shopmanager.Storage.RoomApi.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.stream.Collectors;


@Entity (tableName = "Shoes")
public class Shoes {
    @PrimaryKey (autoGenerate = true) @ColumnInfo (name = "id")
    private int id;

    @ColumnInfo (name = "name")
    private String name;

    @ColumnInfo (name = "price")
    private float price;

    @ColumnInfo (name = "sale_enabled")
    private boolean saleEnabled;

    @ColumnInfo (name = "sale_price")
    private float salePrice;

    @ColumnInfo (name="code")
    private String code;

    @ColumnInfo (name = "date")
    private long date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isSaleEnabled() {
        return saleEnabled;
    }

    public void setSaleEnabled(boolean saleEnabled) {
        this.saleEnabled = saleEnabled;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
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

}
