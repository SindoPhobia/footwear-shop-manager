package com.example.shopmanager.Stocks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.shopmanager.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StocksActivity extends AppCompatActivity {

    RecyclerView stockRecyclerView;

    StockRecyclerViewAdapter stockRecyclerViewAdapter;
    ArrayList<StockDisplayModel> stockModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);

        stockModels = new ArrayList<>();

        stockRecyclerView = findViewById(R.id.activity_stocks_recyclerview_stock);

        setupStock();
        stockRecyclerViewAdapter = new StockRecyclerViewAdapter(this, stockModels);
        stockRecyclerView.setAdapter(stockRecyclerViewAdapter);
        stockRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupStock() {
        // TODO: Get from database

        StockDisplayModel stock1 = new StockDisplayModel(
                "tryferos edition", "abibbas", "Sneakers",
                69.0F, true, 40.0F,
                new HashMap<String, Integer>() {{
                    put("40", 3);
                    put("41", 2);
                    put("43", 5);
                }}, 8, new Date().getTime()
        );

        StockDisplayModel stock2 = new StockDisplayModel(
                "dale edition", "abibbas", "Sneakers",
                69.0F, false, 0F,
                new HashMap<String, Integer>() {{
                    put("40", 3);
                    put("41", 2);
                    put("43", 5);
                }}, 6, new Date().getTime()
        );

        stockModels.add(stock1);
        stockModels.add(stock2);
    }
}