package com.example.shopmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;

import com.example.shopmanager.Home.HomeActivity;
import com.example.shopmanager.Storage.RoomApi.StockDB;


public class MainActivity extends AppCompatActivity {

    public static StockDB stockDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent startActivityIntent = new Intent(this, HomeActivity.class);
        startActivity(startActivityIntent);

        stockDatabase = Room.databaseBuilder(
                getApplicationContext(), StockDB.class, "StockDB"
                ).allowMainThreadQueries().build();

        finish();
    }
}