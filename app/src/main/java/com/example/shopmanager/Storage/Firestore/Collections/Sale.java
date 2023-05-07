package com.example.shopmanager.Storage.Firestore.Collections;

import java.util.Arrays;

public class Sale {
    private String seller;
    private String id;
    private long date;

    private SoldStock[] soldStock;

    public Sale(String seller, String id, long date, SoldStock[] stock_ids) {
        this.seller = seller;
        this.id = id;
        this.date = date;
        this.soldStock = stock_ids;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "seller='" + seller + '\'' +
                ", id='" + id + '\'' +
                ", date=" + date +
                ", stock_ids=" + Arrays.toString(soldStock) +
                '}';
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public SoldStock[] getStock_ids() {
        return soldStock;
    }

    public void setStock_ids(SoldStock[] stock_ids) {
        this.soldStock = stock_ids;
    }
}
