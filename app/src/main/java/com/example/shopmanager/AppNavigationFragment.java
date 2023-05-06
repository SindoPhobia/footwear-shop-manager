package com.example.shopmanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shopmanager.Home.HomeActivity;
import com.example.shopmanager.Sales.SalesActivity;
import com.example.shopmanager.Stock.StockActivity;

public class AppNavigationFragment extends Fragment {

    Button buttonHome, buttonSales, buttonStock;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_navigation, container, false);

        buttonHome = view.findViewById(R.id.fragment_appnavigation_button_home);
        buttonSales = view.findViewById(R.id.fragment_appnavigation_button_sales);
        buttonStock = view.findViewById(R.id.fragment_appnavigation_button_stock);

        buttonHome.setOnClickListener(viewButtonHome -> {
            Intent intentStartActivity = new Intent(getContext(), HomeActivity.class);
            startActivity(intentStartActivity);
        });

        buttonSales.setOnClickListener(viewButtonSales -> {
            Intent intentStartActivity = new Intent(getContext(), SalesActivity.class);
            startActivity(intentStartActivity);
        });

        buttonStock.setOnClickListener(viewButtonStock -> {
            Intent intentStartActivity = new Intent(getContext(), StockActivity.class);
            startActivity(intentStartActivity);
        });

        return view;
    }

    private void changeActivity(View view, Class<?> activityClass) {
        Intent intentStartActivity = new Intent(getContext(), activityClass);
        startActivity(intentStartActivity);
    }

}