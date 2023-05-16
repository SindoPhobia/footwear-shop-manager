package com.example.shopmanager.Stocks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.example.shopmanager.Forms.NewStock;
import com.example.shopmanager.MainActivity;
import com.example.shopmanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StocksActivity extends AppCompatActivity implements StockRecyclerViewAdapter.StockOnClickInterface, MainActivity.SearchFilter {

    RecyclerView stockRecyclerView;
    FloatingActionButton buttonNewStock;

    StockRecyclerViewAdapter stockRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);


        stockRecyclerView = findViewById(R.id.activity_stocks_recyclerview_stock);
        buttonNewStock = findViewById(R.id.activity_stocks_button_createnew);

        stockRecyclerViewAdapter = new StockRecyclerViewAdapter(this, MainActivity.stock, this);
        stockRecyclerView.setAdapter(stockRecyclerViewAdapter);
        stockRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setUpSearchFilter();

        buttonNewStock.setOnClickListener(v -> {
            Intent newStockIntent = new Intent(this, NewStock.class);
            startActivity(newStockIntent);
        });
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
        search.setImeOptions(EditorInfo.IME_ACTION_DONE);
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