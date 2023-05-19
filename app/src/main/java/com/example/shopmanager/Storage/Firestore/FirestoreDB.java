package com.example.shopmanager.Storage.Firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.shopmanager.MainActivity;
import com.example.shopmanager.Sales.SaleDisplayModel;
import com.example.shopmanager.Storage.Analytics.SalesAnalytics;
import com.example.shopmanager.Storage.Firestore.Collections.Sale;
import com.example.shopmanager.Storage.Firestore.Collections.SoldStock;
import com.example.shopmanager.Storage.RoomApi.Shoe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.AggregateField;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FirestoreDB {
    public static FirebaseFirestore db;
    public void init(FirebaseFirestore db){
        this.db = db;
    }

    public static void getLatestSales(int number, Callback callback){
        db.collection("Sales").orderBy("date", Query.Direction.DESCENDING).limit(number).get()
                .addOnCompleteListener(task -> convertSnapshot(task, callback));
    }

    public static void getSalesDateRange(long starting, long ending, Callback callback){
        db.collection("Sales").orderBy("date", Query.Direction.DESCENDING)
                .whereGreaterThanOrEqualTo("date", starting).whereLessThanOrEqualTo("date", ending).get()
                .addOnCompleteListener(task -> convertSnapshot(task, callback));
    }

    public static void getSalesSeller(String seller, Callback callback) {
        db.collection("Sales").whereEqualTo("seller", seller).get()
                .addOnCompleteListener(task -> convertSnapshot(task, callback));
    }

    public static void getTotalSales(CallbackAggregate cl){
        db.collection("Sales").count().get(AggregateSource.SERVER).addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                cl.onError();
                return;
            }
            cl.onComplete((int) task.getResult().getCount());
        });
    }

    public static void addSale(Sale sale, Callback cl){
        getLatestSales(1, new Callback() {
            @Override
            public void onComplete(Sale[] salez) {
                if(salez.length==0){
                    cl.onError();
                    return;
                }
                sale.setId(String.format("%04d", Integer.parseInt(salez[0].getId())+1));
                db.collection("Sales").add(sale.parseSale()).addOnCompleteListener(task -> {
                    if(!task.isSuccessful()){
                        cl.onError();
                        return;
                    }
                    Sale[] sales = new Sale[1];
                    sales[0] = sale;
                    if(sales.length != 1) {
                        cl.onError();
                        return;
                    }
                    SaleDisplayModel.StockDisplayModel[] saleDisplayModel = Arrays.stream(sales[0].getSoldStock()).map(item -> {
                        Shoe s = ((ArrayList<Shoe>)MainActivity.stockDatabase.stockDao().getStock(item.getStock_id())).get(0);
                        HashMap<String, Integer> sizes =  s.getSizesFormatted();
                        try{
                            int newTotal = sizes.get(item.getSize()) - 1;
                            if(newTotal <= 0){
                                sizes.remove(item.getSize());
                            }else{
                                sizes.put(item.getSize(), newTotal);
                            }
                            s.setSizes(s.parseSizes(sizes));
                            MainActivity.stockDatabase.createShoe(s);
                            //TODO: LIVE UPDATE STOCK LIST
                            MainActivity.updateStock();
                        }catch(NullPointerException e){
                            Log.w("sizes", e);
                        }
                        return new SaleDisplayModel.StockDisplayModel(item.getStock_id(), s.getName(), s.getBrand(), s.getColor(), item.getSize(),
                                s.isSale_enabled() ? s.getSale_price() : s.getPrice());
                    }).toArray(SaleDisplayModel.StockDisplayModel[]::new);

                    SaleDisplayModel m = new SaleDisplayModel(sales[0].getId(), sales[0].getSeller(), sales[0].getDate(),
                            saleDisplayModel);

                    SaleDisplayModel lastItem = MainActivity.sales.get(MainActivity.sales.size()-1);

                    try{
                        for (int i = MainActivity.sales.size()-1; i > 0; i--) {
                            MainActivity.sales.set(i, MainActivity.sales.get(i-1));
                        }
                        MainActivity.sales.set(0, m);
                        MainActivity.sales.add(lastItem);
                    }catch(Exception e ){
                        Log.w("sales", e);
                        cl.onError();
                        return;
                    }

                    MainActivity.salesAnalytics.onNewSale(sale);
                    cl.onComplete(sales);
                });
            }

            @Override
            public void onError() {

            }
        });
    }

    public static void deleteSale(Sale sale, Callback cl){
        db.collection("Sales").whereEqualTo("id", sale.getId()).get().addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                cl.onError();
                return;
            }
            if(task.getResult().getDocuments().isEmpty()){
                cl.onError();
                return;
            }
            db.collection("Sales").document(task.getResult().getDocuments().get(0).getId()).delete().addOnCompleteListener(task2 -> {
                    if(!task2.isSuccessful()){
                        cl.onError();
                        return;
                    }
                Sale[] sales = new Sale[1];
                cl.onComplete(sales);
                MainActivity.salesAnalytics.onDeleteSale(sale);
            });
        });

    }

    private static void convertSnapshot(Task<QuerySnapshot> task, Callback cl){
        if(!task.isSuccessful()) {
            cl.onError();
            return;
        }
        cl.onComplete(task.getResult().getDocuments().stream().map(Sale::convertSale).toArray(Sale[]::new));
    }

    public interface Callback{
        public void onComplete(Sale[] sales);
        public void onError();
    }

    public interface CallbackAggregate{
        public void onComplete(int count);

        public void onError();

    }

}
