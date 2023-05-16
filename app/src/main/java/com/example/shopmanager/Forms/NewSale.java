package com.example.shopmanager.Forms;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shopmanager.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class NewSale extends AppCompatActivity {

    AddStockRecyclerViewAdapter addStockRecyclerViewAdapter;
    ArrayList<AddStockModel> addStockModelList;

    RecyclerView addStockRecyclerView;

    ConstraintLayout containerInputNewSale;
    EditText inputNewSale;
    TextInputEditText inputSoldBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sale);

        addStockRecyclerView = findViewById(R.id.activity_newsale_container_form_recyclerview_products);

        containerInputNewSale = findViewById(R.id.activity_newsale_container_form_container_searchiteminputwrapper);
        inputNewSale = findViewById(R.id.activity_newsale_container_form_edittext_searchproduct);
        inputSoldBy = findViewById(R.id.activity_newsale_container_form_edittext_soldby);

        AddStockModel stockModel = new AddStockModel("Name", "Brand", "Category", "69", 1, 3, false, 1);

        addStockModelList = new ArrayList<>();

        addStockModelList.add(stockModel);
        addStockModelList.add(stockModel);
        addStockModelList.add(stockModel);
        addStockModelList.add(stockModel);

        addStockRecyclerViewAdapter = new AddStockRecyclerViewAdapter(this, addStockModelList);
        addStockRecyclerView.setAdapter(addStockRecyclerViewAdapter);
        addStockRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}