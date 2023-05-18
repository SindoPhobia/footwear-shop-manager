package com.example.shopmanager.Storage.Firestore.Collections;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Sale {
    private String seller;
    private String id;
    private long date;

    private SoldStock[] soldStock;

    public Sale(){}

    public Sale(String seller, long date, SoldStock[] stock_ids) {
        this.seller = seller;
        this.date = date;
        this.soldStock = stock_ids;
    }

    public Sale(String seller, String id,long date, SoldStock[] stock_ids) {
        this.seller = seller;
        this.date = date;
        this.id = id;
        this.soldStock = stock_ids;
    }

    public HashMap<String, Object> parseSale(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("seller", seller);
        map.put("id", id);
        map.put("date", date);

        ArrayList<HashMap<String, Object>> listSold = new ArrayList<>();
        for(SoldStock item : soldStock){
            HashMap<String, Object> tmp = new HashMap<>();
//            tmp.put("color", item.getColor());
            tmp.put("price", item.getPrice());
            tmp.put("size", item.getSize());
            tmp.put("stock_id", item.getStock_id());
            listSold.add(tmp);
        }

        map.put("soldStock", listSold);
        return map;
    }

    public static Sale convertSale(DocumentSnapshot snap){
        HashMap<String, Object> map = (HashMap<String, Object>) snap.getData();
        Object obj = map.get("soldStock");
        ArrayList<SoldStock> soldStock = new ArrayList<>();
        if(obj instanceof ArrayList<?>){
            ArrayList items = (ArrayList) obj;
            for (Object item : items){
                if(item instanceof HashMap<?, ?>){
                    HashMap<String, Object> data = (HashMap<String, Object>) item;
                    soldStock.add(
                            new SoldStock(
                                    Integer.parseInt(data.get("stock_id").toString()),
                                    data.get("size").toString(),
//                                    data.get("color").toString(),
                                    Float.parseFloat(data.get("price").toString())
                            )
                    );
                }
            }
        }
        return new Sale(
                map.get("seller").toString(),
                map.get("id").toString(),
                Long.parseLong(map.get("date").toString()),
                soldStock.stream().toArray(SoldStock[]::new)
        );
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

    public SoldStock[] getSoldStock() {
        return soldStock;
    }

    public void setSoldStock(SoldStock[] stock_ids) {
        this.soldStock = stock_ids;
    }
}
