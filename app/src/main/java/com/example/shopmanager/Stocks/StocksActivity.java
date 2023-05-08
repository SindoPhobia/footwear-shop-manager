package com.example.shopmanager.Stocks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.shopmanager.MainActivity;
import com.example.shopmanager.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StocksActivity extends AppCompatActivity implements StockRecyclerViewAdapter.StockOnClickInterface, MainActivity.SearchFilter {

    RecyclerView stockRecyclerView;

    StockRecyclerViewAdapter stockRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);


        stockRecyclerView = findViewById(R.id.activity_stocks_recyclerview_stock);

        stockRecyclerViewAdapter = new StockRecyclerViewAdapter(this, MainActivity.stock, this);
        stockRecyclerView.setAdapter(stockRecyclerViewAdapter);
        stockRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setUpSearchFilter();
    }


    @Override
    public void onClick(int position) {
        MainActivity.stock.get(position);

        Intent intentStartStockActivity = new Intent(this, StockActivity.class);
        intentStartStockActivity.putExtra("code", MainActivity.stock.get(position).getCode());
        startActivity(intentStartStockActivity);
    }

    @Override
    public void setUpSearchFilter() {
        EditText search = findViewById(R.id.activity_stocks_constraint_filters_edittext_search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newText = s.toString();
                stockRecyclerViewAdapter.filterStock(newText);
            }
        });
    }
}