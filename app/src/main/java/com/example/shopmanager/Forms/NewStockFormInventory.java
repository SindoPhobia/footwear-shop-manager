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
import com.example.shopmanager.Storage.RoomApi.Shoe;

import java.util.ArrayList;
import java.util.HashMap;

public class NewStockFormInventory extends Fragment implements NewStock.FormFragment {

    private static final int MIN_SIZE = 34;
    private static final int MAX_SIZE = 49;

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

        Shoe state = NewStock.shoe;
        if(state.getSizes()!=null){
            HashMap<String, Integer> map = state.getSizesFormatted();
            if(map.size()==0) return view;
            state.getSizesFormatted().forEach(
                    (size, count) -> {
                        inventoryList.add(new FormInventoryModel(size, false, count));
                    });
        }
        return view;
    }

    private void setupInventoryList() {
        inventoryList = new ArrayList<>();
        for (int i = MIN_SIZE;i<=MAX_SIZE;i++){
            inventoryList.add(new FormInventoryModel(String.valueOf(i)));
        }
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

    @Override
    public void fillData(Shoe data) {
        HashMap<String, Integer> sizesMap = new HashMap<>();
        for (int i = 0; i < inventoryList.size(); i++) {
            FormInventoryModel inventoryItem = inventoryList.get(i);
            if(!inventoryItem.isSelected() || inventoryItem.getCount()<=0) continue;
            sizesMap.put(inventoryItem.getSize(), inventoryItem.getCount());
        }
        data.setSizes(data.parseSizes(sizesMap));
    }

}