package com.example.shopmanager.Sales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.shopmanager.Forms.NewSaleActivity;
import com.example.shopmanager.Home.HomeActivity;
import com.example.shopmanager.MainActivity;
import com.example.shopmanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SalesActivity extends AppCompatActivity implements MainActivity.SearchFilter {

    SaleRecyclerViewAdapter saleRecyclerViewAdapter;

    RecyclerView salesRecyclerView;
    TextView analyticsSalesTotal, analyticsSalesMonthly, analyticsSalesWeekly, analyticsSalesToday, noItems;

    FloatingActionButton buttonNewSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        salesRecyclerView = findViewById(R.id.activity_sales_recyclerview_sales);
        analyticsSalesTotal = findViewById(R.id.activity_sales_constraint_sales_text_salestotal);
        analyticsSalesMonthly = findViewById(R.id.activity_sales_constraint_sales_text_salesmonthly);
        analyticsSalesWeekly = findViewById(R.id.activity_sales_constraint_sales_text_salesweekly);
        analyticsSalesToday = findViewById(R.id.activity_sales_constraint_sales_text_salestoday);
        buttonNewSale = findViewById(R.id.activity_sales_button_createnew);

        noItems = findViewById(R.id.activity_sales_no_items);

        analyticsSalesTotal.setText(new StringBuilder().append(HomeActivity.roundPrice(MainActivity.salesAnalytics.getTotal())).append("€"));
        analyticsSalesMonthly.setText(new StringBuilder().append(HomeActivity.roundPrice(MainActivity.salesAnalytics.getMonthly())).append("€"));
        analyticsSalesWeekly.setText(new StringBuilder().append(HomeActivity.roundPrice(MainActivity.salesAnalytics.getWeekly())).append("€"));
        analyticsSalesToday.setText(new StringBuilder().append(HomeActivity.roundPrice(MainActivity.salesAnalytics.getToday())).append("€"));

        if(MainActivity.sales.size()==0){
            noItems.setVisibility(View.VISIBLE);
        }else{
            noItems.setVisibility(View.INVISIBLE);
        }

        saleRecyclerViewAdapter = new SaleRecyclerViewAdapter(this, MainActivity.sales);
        salesRecyclerView.setAdapter(saleRecyclerViewAdapter);
        salesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonNewSale.setOnClickListener(v -> {
            Intent newSaleActivity = new Intent(this, NewSaleActivity.class);
            startActivity(newSaleActivity);
        });

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