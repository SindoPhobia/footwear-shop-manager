package com.example.shopmanager.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.shopmanager.MainActivity;
import com.example.shopmanager.R;
import com.example.shopmanager.Storage.Analytics.StockAnalytics;

public class HomeActivity extends AppCompatActivity {

    TextView analyticsSalesTotal;

    TextView analyticsSalesToday;
    TextView analyticsStockTotal;
    TextView analyticsCategoriesTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
}