package com.example.shopmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.shopmanager.Home.HomeActivity;
import com.example.shopmanager.Sales.SalesActivity;
import com.example.shopmanager.Stock.StockActivity;
import com.example.shopmanager.Stock.StocksActivity;

public class AppNavigationFragment extends Fragment {

    ImageButton buttonHome, buttonSales, buttonStock;

    BUTTON highlightedButton;

    public static enum BUTTON { HOME, SALES, STOCK };

    public AppNavigationFragment() {}

    public void setHighlightButton(BUTTON button) {
        if(getContext() == null) return;

        switch (button) {
            case HOME:
                buttonHome.setImageResource(R.drawable.icon_home_selected);
                break;
            case SALES:
                buttonSales.setImageResource(R.drawable.icon_sales_selected);
                break;
            case STOCK:
                buttonStock.setImageResource(R.drawable.icon_stock_selected);
                break;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity parentActivity = (Activity) context;
        if(parentActivity instanceof HomeActivity) {
            highlightedButton = BUTTON.HOME;
        } else if(parentActivity instanceof SalesActivity) {
            highlightedButton = BUTTON.SALES;
        }else if(parentActivity instanceof StocksActivity || parentActivity instanceof StockActivity) {
                highlightedButton = BUTTON.STOCK;
            }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_navigation, container, false);

        buttonHome = view.findViewById(R.id.fragment_appnavigation_button_home);
        buttonSales = view.findViewById(R.id.fragment_appnavigation_button_sales);
        buttonStock = view.findViewById(R.id.fragment_appnavigation_button_stock);

        buttonHome.setOnClickListener(viewButtonHome -> changeActivity(viewButtonHome, HomeActivity.class));
        buttonSales.setOnClickListener(viewButtonSales -> changeActivity(viewButtonSales, SalesActivity.class));
        buttonStock.setOnClickListener(viewButtonStock -> changeActivity(viewButtonStock, StocksActivity.class));

        setHighlightButton(highlightedButton);
        return view;
    }

    private void changeActivity(View view, Class<?> activityClass) {
        Intent intentStartActivity = new Intent(getContext(), activityClass);
        startActivity(intentStartActivity);
    }

}