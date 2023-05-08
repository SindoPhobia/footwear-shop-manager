package com.example.shopmanager.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.shopmanager.MainActivity;
import com.example.shopmanager.R;
import com.example.shopmanager.Sales.SaleDisplayModel;
import com.example.shopmanager.Sales.SaleRecyclerViewAdapter;
import com.example.shopmanager.Stocks.StockActivity;
import com.example.shopmanager.Stocks.StockDisplayModel;
import com.example.shopmanager.Stocks.StockRecyclerViewAdapter;
import com.example.shopmanager.Storage.Analytics.StockAnalytics;
import com.example.shopmanager.Storage.RoomApi.Shoe;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeActivity extends AppCompatActivity implements StockRecyclerViewAdapter.StockOnClickInterface{
    private static final int SALES_DISPLAY_COUNT = 2;

    SaleRecyclerViewAdapter saleRecyclerViewAdapter;
    StockRecyclerViewAdapter stockRecyclerViewAdapter;

    RecyclerView salesRecyclerView;
    RecyclerView stockRecyclerView;
    TextView analyticsSalesTotal;
    TextView analyticsSalesToday;
    TextView analyticsStockTotal;
    TextView analyticsCategoriesTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setAnalytics();

        setStockRecyclerView();
        setSalesRecyclerView();
    }

    private void setStockRecyclerView(){
        stockRecyclerView = findViewById(R.id.activity_home_constraint_stock_recyclerview_stock);
        ArrayList<StockDisplayModel> stockList = new ArrayList<>(Arrays.asList(StockDisplayModel.parseStockToDisplayModel(
                (ArrayList<Shoe>) MainActivity.stockDatabase.stockDao().getStockDesc(3))));

        stockRecyclerViewAdapter = new StockRecyclerViewAdapter(this, stockList, this);
        stockRecyclerView.setAdapter(stockRecyclerViewAdapter);
        stockRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setSalesRecyclerView(){
        salesRecyclerView = findViewById(R.id.activity_home_constraint_latestsales_recyclerview_sales);

        ArrayList<SaleDisplayModel> homeSalesList = new ArrayList<>();

        int startCount = Math.min(MainActivity.sales.size(), SALES_DISPLAY_COUNT);
        for(int i = startCount - 1; i >= 0; i--) {
            homeSalesList.add(MainActivity.sales.get(i));
        }

        saleRecyclerViewAdapter = new SaleRecyclerViewAdapter(this, homeSalesList);
        salesRecyclerView.setAdapter(saleRecyclerViewAdapter);
        salesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setAnalytics(){
        analyticsSalesTotal = findViewById(R.id.activity_home_constraint_sales_text_salestotal);
        analyticsSalesToday = findViewById(R.id.activity_home_constraint_sales_text_salestoday);
        analyticsStockTotal = findViewById(R.id.activity_home_constraint_analyticstock_text_totalstock);
        analyticsCategoriesTotal = findViewById(R.id.activity_home_constraint_analyticstock_text_stockcategories);

        analyticsSalesTotal.setText(new StringBuilder().append(MainActivity.salesAnalytics.getTotal()).append("€"));
        analyticsSalesToday.setText(new StringBuilder().append(MainActivity.salesAnalytics.getToday()).append("€"));

        StockAnalytics stockAnalytics = MainActivity.stockDatabase.stockDao().getStockAnalytics();
        analyticsStockTotal.setText(String.valueOf(stockAnalytics.getStockTotal()));
        analyticsCategoriesTotal.setText(String.valueOf(stockAnalytics.getCategoriesTotal()));
    }

    @Override
    public void onClick(int position) {
        MainActivity.stock.get(position);

        Intent intentStartStockActivity = new Intent(this, StockActivity.class);
        intentStartStockActivity.putExtra("code", MainActivity.stock.get(position).getCode());
        startActivity(intentStartStockActivity);
    }
}