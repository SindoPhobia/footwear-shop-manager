package com.example.shopmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;

import com.example.shopmanager.Home.HomeActivity;
import com.example.shopmanager.Storage.Analytics.SalesAnalytics;
import com.example.shopmanager.Storage.Firestore.FirestoreDB;
import com.example.shopmanager.Storage.RoomApi.StockDB;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    public static StockDB stockDatabase;
    public static FirestoreDB firestoreDB;
    public static SalesAnalytics salesAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent startActivityIntent = new Intent(this, HomeActivity.class);
        startActivity(startActivityIntent);

        stockDatabase = Room.databaseBuilder(
                getApplicationContext(), StockDB.class, "StockDB"
                ).allowMainThreadQueries().build();

        firestoreDB = new FirestoreDB();
        firestoreDB.init(FirebaseFirestore.getInstance());

        salesAnalytics = new SalesAnalytics();
        salesAnalytics.init();

        // TODO: Grab data from both databases for the main recycler views
        finish();
    }

}