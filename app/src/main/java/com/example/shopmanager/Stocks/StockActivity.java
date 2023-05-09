package com.example.shopmanager.Stocks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shopmanager.MainActivity;
import com.example.shopmanager.R;
import com.example.shopmanager.Storage.RoomApi.Shoe;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class StockActivity extends AppCompatActivity{

    TextView name;
    TextView productCode;

    TextView category;
    TextView gender;

    TextView currentPrice;
    TextView originalPrice;
    TextView discountPercent;

    LinearLayout stockList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        name = findViewById(R.id.activity_stock_container_details_text_name);
        productCode = findViewById(R.id.activity_stock_container_details_text_productcode);

        category = findViewById(R.id.activity_stock_container_categories_text_category);
        gender = findViewById(R.id.activity_stock_container_categories_text_gender);

        currentPrice = findViewById(R.id.activity_stock_container_details_text_currentprice);
        originalPrice = findViewById(R.id.activity_stock_container_details_text_originalprice);
        discountPercent = findViewById(R.id.activity_stock_container_details_text_salepercent);

        stockList = findViewById(R.id.activity_stock_list);

        String code = getIntent().getExtras().getString("code");
        ArrayList<Shoe> shoes = (ArrayList<Shoe>) MainActivity.stockDatabase.stockDao().getShoesSameColor(code);

        Shoe initial = shoes.get(0);

        name.setText(new StringBuilder().append(initial.getBrand()).append(" - ").append(initial.getName()));
        productCode.setText(new StringBuilder().append("#").append(initial.getCode()));

        category.setText(initial.getCategory());
        gender.setText(Shoe.getGender(initial.getGender()));

        if(initial.isSale_enabled()) {
            currentPrice.setText(new StringBuilder(String.valueOf(initial.getSale_price())).append("€"));
            originalPrice.setText(new StringBuilder(String.valueOf(initial.getPrice())).append("€"));
            int discount = (int) ((initial.getPrice() - initial.getSale_price()) / initial.getPrice() * 100);
            discountPercent.setText(new StringBuilder().append(discount).append("%"));
        } else {
            currentPrice.setText(new StringBuilder(String.valueOf(initial.getPrice())).append("€"));
            originalPrice.setVisibility(View.INVISIBLE);
            discountPercent.setVisibility(View.INVISIBLE);
        }

        shoes.forEach(shoe -> {
            stockList.addView(createInventoryRow(shoe.getColor(), shoe.getSizesFormatted()));
        });
    }

    private View createInventoryRow(String color, HashMap<String, Integer> sizes) {
        ConstraintLayout view = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.activity_stock_inventory_row, null, false);

        TextView colorView = view.findViewById(R.id.activity_stock_list_item_text_color);
        TextView totalSizesView = view.findViewById(R.id.activity_stock_list_item_text_totalsizes);
        LinearLayout colorList = view.findViewById(R.id.activity_stock_list_item_list_sizes);

        colorList.removeAllViews();

        Integer totalCount = 0;
        Object[] sizeKeys = sizes.keySet().toArray();
        for(int i = 0; i < sizes.size(); i++) {
            String size = (String) sizeKeys[i];
            Integer count = sizes.get(sizeKeys[i]);
            totalCount += count;

            colorList.addView(createInventoryRowsSizeTag(size, count));
        }

        colorView.setText(color);
        totalSizesView.setText(String.valueOf(10));

        return view;
    }

    private View createInventoryRowsSizeTag(String size, Integer count) {
        LinearLayout sizeRoot = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_stock_inventory_row_size_tag, null, false);

        LinearLayout.LayoutParams sizeRootLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sizeRootLayoutParams.setMargins(0, 0, 16, 0);
        sizeRoot.setLayoutParams(sizeRootLayoutParams);

        TextView sizeView = sizeRoot.findViewById(R.id.activity_stock_list_item_list_sizes_item_text_size);
        TextView countView = sizeRoot.findViewById(R.id.activity_stock_list_item_list_sizes_item_sizeamount);

        sizeView.setText(size);
        countView.setText(String.valueOf(count));

        return sizeRoot;
    }
}

