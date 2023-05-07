package com.example.shopmanager.Storage.Firestore;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirestoreDB {
    public static FirebaseFirestore db;
    public void init(FirebaseFirestore db){
        this.db = db;
    }


//    Map<String, Object> data = new HashMap<>();
//        data.put("name", "malakas");
//    db = FirebaseFirestore.getInstance();
//        db.collection("Sales").add(data).addOnSuccessListener(documentReference -> {
//        Log.d("firestore","success");
//    }).addOnFailureListener(error -> {
//        Log.w("firestore", "error", error);
//    });
}
