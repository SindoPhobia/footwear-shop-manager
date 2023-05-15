package com.example.shopmanager.Sales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.example.shopmanager.MainActivity;
import com.example.shopmanager.R;

public class SalesActivity extends AppCompatActivity implements MainActivity.SearchFilter {

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

    public void setUpSearchFilter(){
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
                saleRecyclerViewAdapter.filterSales(newText);
            }
        });
    }

}