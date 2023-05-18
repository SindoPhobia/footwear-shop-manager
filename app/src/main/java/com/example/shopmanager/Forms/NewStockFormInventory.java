package com.example.shopmanager.Forms;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopmanager.R;

import java.util.ArrayList;

public class NewStockFormInventory extends Fragment implements NewStock.FormFragment {

    ConstraintLayout errorInventory;
    FormInventoryRecyclerViewAdapter inventoryRecyclerViewAdapter;
    ArrayList<FormInventoryModel> inventoryList;

    RecyclerView inventoryRecyclerView;

    FormInventoryRecyclerViewAdapter.InventoryRowInterface inventoryRowInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newstock_form_inventory, container, false);

        errorInventory = view.findViewById(R.id.fragment_newstock_form_inventory_contstraint_inventoryerror);
        inventoryRecyclerView = view.findViewById(R.id.fragment_newstock_form_inventory_reyclerview_inventory);

        setupInventoryList();

        inventoryRowInterface = new FormInventoryRecyclerViewAdapter.InventoryRowInterface() {
            @Override
            public void onClick(int position) {
                inventoryList.get(position).setSelected(!inventoryList.get(position).isSelected());
                inventoryRecyclerViewAdapter.notifyItemChanged(position);
            }

            public void onKeyPress(int position, int number) {
                inventoryList.get(position).setCount(number);
            }
        };

        inventoryRecyclerViewAdapter = new FormInventoryRecyclerViewAdapter(getContext(), inventoryList, inventoryRowInterface);
        inventoryRecyclerView.setAdapter(inventoryRecyclerViewAdapter);
        inventoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private void setupInventoryList() {
        inventoryList = new ArrayList<>();

        inventoryList.add(new FormInventoryModel("43", true, 0));
        inventoryList.add(new FormInventoryModel("44"));
        inventoryList.add(new FormInventoryModel("45"));
    }

    @Override
    public boolean validateForm() {
        boolean isValid = true;

        boolean isSelected = false;
        boolean hasCount = true;
        for(int i = 0; i < inventoryList.size(); i++) {
            if(inventoryList.get(i).isSelected()) {
                isSelected = true;
                if(inventoryList.get(i).getCount() == 0) hasCount = false;
            }
        }

        if(!isSelected) {
            errorInventory.setVisibility(View.VISIBLE);
            isValid = false;
            hasCount = false;
        } else {
            errorInventory.setVisibility(View.GONE);
        }

        if(!hasCount) {
            errorInventory.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorInventory.setVisibility(View.GONE);
        }

        return isValid;
    }
}