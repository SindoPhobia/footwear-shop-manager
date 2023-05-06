package com.example.shopmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shopmanager.Home.HomeActivity;
import com.example.shopmanager.Sales.SalesActivity;
import com.example.shopmanager.Stock.StockActivity;

public class AppNavigationFragment extends Fragment {

    Button buttonHome, buttonSales, buttonStock;

    BUTTON highlightedButton;

    public static enum BUTTON { HOME, SALES, STOCK };

    public AppNavigationFragment() {}

    public void setHighlightButton(BUTTON button) {
        if(getContext() == null) return;

        switch (button) {
            case HOME:
                buttonHome.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.teal_200));
                break;
            case SALES:
                buttonSales.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.teal_200));
                break;
            case STOCK:
                buttonStock.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.teal_200));
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
        }else if(parentActivity instanceof StockActivity) {
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
        buttonStock.setOnClickListener(viewButtonStock -> changeActivity(viewButtonStock, StockActivity.class));

        setHighlightButton(highlightedButton);
        return view;
    }

    private void changeActivity(View view, Class<?> activityClass) {
        Intent intentStartActivity = new Intent(getContext(), activityClass);
        startActivity(intentStartActivity);
    }

}