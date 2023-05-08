package com.example.shopmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.shopmanager.Home.HomeActivity;
import com.example.shopmanager.Storage.Analytics.SalesAnalytics;
import com.example.shopmanager.Storage.Firestore.FirestoreDB;
import com.example.shopmanager.Storage.RoomApi.Shoe;
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

        Shoe shoe = new Shoe(1, "ntelos", "code", 12, 50f, false, 50f, "athletic",
                "adidas", "white", "40-2,41-2,43-4,45-5,46-6", 0);

        Log.d("shoes", shoe.getSizesFormatted().toString());
        Log.d("shoes", shoe.parseSizes(shoe.getSizesFormatted()));

        // TODO: Grab data from both databases for the main recycler views
        finish();
    }

}