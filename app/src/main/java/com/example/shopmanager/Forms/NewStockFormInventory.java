package com.example.shopmanager.Forms;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopmanager.R;

import java.util.ArrayList;

public class NewStockFormInventory extends Fragment implements NewStock.FormFragment {

    FormInventoryRecyclerViewAdapter inventoryRecyclerViewAdapter;
    ArrayList<FormInventoryModel> inventoryList;

    RecyclerView inventoryRecyclerView;

    FormInventoryRecyclerViewAdapter.OnClickInterface onClickInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newstock_form_inventory, container, false);

        inventoryRecyclerView = view.findViewById(R.id.fragment_newstock_form_inventory_reyclerview_inventory);

        setupInventoryList();

        onClickInterface = new FormInventoryRecyclerViewAdapter.OnClickInterface() {
            @Override
            public void onClick(int position) {
                inventoryList.get(position).setSelected(!inventoryList.get(position).isSelected());
                inventoryRecyclerViewAdapter.notifyItemChanged(position);
            }
        };

        inventoryRecyclerViewAdapter = new FormInventoryRecyclerViewAdapter(getContext(), inventoryList, onClickInterface);
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
        return true;
    }
}