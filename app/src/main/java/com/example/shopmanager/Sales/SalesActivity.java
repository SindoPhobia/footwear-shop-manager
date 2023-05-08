package com.example.shopmanager.Sales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.shopmanager.MainActivity;
import com.example.shopmanager.R;

public class SalesActivity extends AppCompatActivity {

    SaleRecyclerViewAdapter saleRecyclerViewAdapter;

    RecyclerView salesRecyclerView;
    TextView analyticsSalesTotal;
    TextView analyticsSalesMonthly;
    TextView analyticsSalesWeekly;
    TextView analyticsSalesToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        salesRecyclerView = findViewById(R.id.activity_sales_recyclerview_sales);
        analyticsSalesTotal = findViewById(R.id.activity_sales_constraint_sales_text_salestotal);
        analyticsSalesMonthly = findViewById(R.id.activity_sales_constraint_sales_text_salesmonthly);
        analyticsSalesWeekly = findViewById(R.id.activity_sales_constraint_sales_text_salesweekly);
        analyticsSalesToday = findViewById(R.id.activity_sales_constraint_sales_text_salestoday);

        analyticsSalesTotal.setText(new StringBuilder().append(MainActivity.salesAnalytics.getTotal()).append("€"));
        analyticsSalesMonthly.setText(new StringBuilder().append(MainActivity.salesAnalytics.getMonthly()).append("€"));
        analyticsSalesWeekly.setText(new StringBuilder().append(MainActivity.salesAnalytics.getWeekly()).append("€"));
        analyticsSalesToday.setText(new StringBuilder().append(MainActivity.salesAnalytics.getToday()).append("€"));

        saleRecyclerViewAdapter = new SaleRecyclerViewAdapter(this, MainActivity.sales);
        salesRecyclerView.setAdapter(saleRecyclerViewAdapter);
        salesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setUpSearchFilter();
    }

    private void setUpSearchFilter(){
//        SearchView search = findViewById(R.id.activity_stocks_constraint_filters_edittext_search);
    }

}