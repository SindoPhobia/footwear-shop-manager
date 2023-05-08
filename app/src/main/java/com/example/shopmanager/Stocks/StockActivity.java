package com.example.shopmanager.Stocks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.shopmanager.MainActivity;
import com.example.shopmanager.R;
import com.example.shopmanager.Storage.RoomApi.Shoe;

import java.util.ArrayList;

public class StockActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String code = getIntent().getExtras().getString("code");
        ArrayList<Shoe> shoes = (ArrayList<Shoe>) MainActivity.stockDatabase.stockDao().getShoesSameColor(code);
        Log.d("stock", String.valueOf(shoes.size()));
        setContentView(R.layout.activity_stock);

    }

}

