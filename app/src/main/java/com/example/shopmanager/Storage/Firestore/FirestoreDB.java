package com.example.shopmanager.Storage.Firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.shopmanager.MainActivity;
import com.example.shopmanager.Storage.Analytics.SalesAnalytics;
import com.example.shopmanager.Storage.Firestore.Collections.Sale;
import com.example.shopmanager.Storage.Firestore.Collections.SoldStock;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

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

    public static void addSale(Sale sale, Callback cl){
        db.collection("Sales").add(sale.parseSale()).addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                cl.onError();
                return;
            }
            Sale[] saleAr = new Sale[1];
            saleAr[0] = sale;
            cl.onComplete(saleAr);
            MainActivity.salesAnalytics.onNewSale(sale);
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

}
