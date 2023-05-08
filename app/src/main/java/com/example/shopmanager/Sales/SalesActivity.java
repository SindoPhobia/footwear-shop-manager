package com.example.shopmanager.Sales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.shopmanager.R;

import java.util.ArrayList;
import java.util.Date;

public class SalesActivity extends AppCompatActivity {

    ArrayList<SaleDisplayModel> sales;
    SaleRecyclerViewAdapter saleRecyclerViewAdapter;
    RecyclerView salesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        salesRecyclerView = findViewById(R.id.activity_sales_recyclerview_sales);

        setupSales();
        saleRecyclerViewAdapter = new SaleRecyclerViewAdapter(this, sales);
        salesRecyclerView.setAdapter(saleRecyclerViewAdapter);
        salesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void setupSales() {
        sales = new ArrayList<>();

        // TODO: Combine sale and stock data and create the necessary objects
        SaleDisplayModel.StockDisplayModel stock1 = new SaleDisplayModel.StockDisplayModel(
                1,
                "stan smeeth", "abibbas",
                "yellow", "43", 69.0F
        );

        SaleDisplayModel.StockDisplayModel stock2 = new SaleDisplayModel.StockDisplayModel(
                1,
                "tryfera edition", "nike",
                "pink", "46", 420.0F
        );


        SaleDisplayModel.StockDisplayModel[] stocks = new SaleDisplayModel.StockDisplayModel[2];
        stocks[0] = stock1;
        stocks[1] = stock2;

        SaleDisplayModel sale1 = new SaleDisplayModel(1, "Tryff", new Date().getTime(), stocks);
        sales.add(sale1);
    }
}