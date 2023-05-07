package com.example.shopmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.shopmanager.Home.HomeActivity;
import com.example.shopmanager.Storage.Analytics.SalesAnalytics;
import com.example.shopmanager.Storage.Firestore.Collections.Sale;
import com.example.shopmanager.Storage.Firestore.Collections.SoldStock;
import com.example.shopmanager.Storage.Firestore.FirestoreDB;
import com.example.shopmanager.Storage.RoomApi.Entities.Brands;
import com.example.shopmanager.Storage.RoomApi.Entities.Categories;
import com.example.shopmanager.Storage.RoomApi.Entities.Colors;
import com.example.shopmanager.Storage.RoomApi.Entities.Shoes;
import com.example.shopmanager.Storage.RoomApi.Entities.Stock;
import com.example.shopmanager.Storage.RoomApi.Shoe;
import com.example.shopmanager.Storage.RoomApi.StockDB;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        Log.d("items", "start");

        firestoreDB = new FirestoreDB();
        firestoreDB.init(FirebaseFirestore.getInstance());

        salesAnalytics = new SalesAnalytics();
        salesAnalytics.init();

        pushData();
        finish();
    }

    private void pushData(){
        Brands brand1 = new Brands();
//        brand1.setId(1);
        brand1.setName("Nike");
        Brands brand2 = new Brands();
//        brand2.setId(2);
        brand2.setName("Adidas");
        Brands brand3 = new Brands();
//        brand3.setId(3);
        brand3.setName("Puma");

        Categories cat1 = new Categories();
//        cat1.setId(1);
        cat1.setName("Athletic");
        Categories cat2 = new Categories();
//        cat2.setId(2);
        cat2.setName("Hiking");
        Categories cat3 = new Categories();
//        cat3.setId(3);
        cat3.setName("Walking");

        Colors color1 = new Colors();
//        color1.setId(1);
        color1.setName("Black");
        Colors color2 = new Colors();
//        color2.setId(2);
        color2.setName("White");
        Colors color3 = new Colors();
//        color3.setId(3);
        color3.setName("Red");

        Shoes shoe1 = new Shoes( "adibas", 42.5f,
                false, 42.5f, "ntelos123", new Date().getTime(), 0);
        Shoes shoe2 = new Shoes( "Adibas", 62.5f,
                false, 22.5f, "ntelos123", new Date().getTime(), 0);
        Shoes shoe3 = new Shoes( "Adibas", 12.5f,
                false, 12.5f, "ntelos123", new Date().getTime(), 0);

        Stock stock1 = new Stock( 3, 3, 3, 1);
        Stock stock2 = new Stock( 2, 2, 3, 2);
        Stock stock3 = new Stock( 1, 5, 1, 1);
        shoe1.setSizes("1,2,3,4,5,6,7,8,9");
        shoe2.setSizes("1,2,3,4,5,6,7,8,9");
        shoe3.setSizes("1,2,3,4,5,6,7,8,9");

//        stockDatabase.stockDao().insertBrands(brand1);
//        stockDatabase.stockDao().insertBrands(brand2);
//        stockDatabase.stockDao().insertBrands(brand3);
////
//        stockDatabase.stockDao().insertCategories(cat1);
//        stockDatabase.stockDao().insertCategories(cat2);
//        stockDatabase.stockDao().insertCategories(cat3);
//
//        stockDatabase.stockDao().insertColors(color1);
//        stockDatabase.stockDao().insertColors(color2);
//        stockDatabase.stockDao().insertColors(color3);
//
//        stockDatabase.stockDao().insertShoe(shoe1);
//        stockDatabase.stockDao().insertShoe(shoe2);
//        stockDatabase.stockDao().insertShoe(shoe3);
////
//        stockDatabase.stockDao().insertStock(stock1);
//        stockDatabase.stockDao().insertStock(stock2);
//        stockDatabase.stockDao().insertStock(stock3);

        List<Shoe> shoes = stockDatabase.stockDao().getStockPrice(10, 20);
        Shoe shoe = shoes.get(0);
        Log.d("items", shoe.getSizesFormatted().toString());
        Log.d("items", shoe.parseSizes(shoe.getSizesFormatted()));
        Log.d("items", String.valueOf(shoes.size()));
        Log.d("items", shoe.toString());

        Log.d("total", String.valueOf(stockDatabase.stockDao().getStockAnalytics()));


    }
}