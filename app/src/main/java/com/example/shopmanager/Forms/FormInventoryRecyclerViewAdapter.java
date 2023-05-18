package com.example.shopmanager.Forms;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmanager.R;

import java.util.ArrayList;

public class FormInventoryRecyclerViewAdapter extends RecyclerView.Adapter<FormInventoryRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<FormInventoryModel> inventoryList;

    InventoryRowInterface inventoryRowInterface;
    public interface InventoryRowInterface {
        public void onClick(int position);
        public void onKeyPress(int position, int number);
    }

    public FormInventoryRecyclerViewAdapter(Context context, ArrayList<FormInventoryModel> inventoryList, InventoryRowInterface inventoryRowInterface) {
        this.context = context;
        this.inventoryList = inventoryList;
        this.inventoryRowInterface = inventoryRowInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_sizeinventory_row, parent, false);
        return new ViewHolder(view, inventoryRowInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FormInventoryModel item = inventoryList.get(position);

        holder.buttonSize.setText(new StringBuilder().append("Size ").append(item.getSize()));
        holder.toggleSelected(item.isSelected());
    }

    @Override
    public int getItemCount() {
        return inventoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout root;
        Button buttonSize;
        TextView labelItems;
        ImageView imageItems;
        EditText editTextCount;

        public ViewHolder(@NonNull View itemView, InventoryRowInterface inventoryRowInterface) {
            super(itemView);

            root = itemView.findViewById(R.id.recyclerview_sizeintentory_row_container_element);
            buttonSize = itemView.findViewById(R.id.recyclerview_sizeintentory_row_button_size);
            labelItems = itemView.findViewById(R.id.recyclerview_sizeintentory_row_text_items);
            imageItems = itemView.findViewById(R.id.recyclerview_sizeintentory_row_image_items);
            editTextCount = itemView.findViewById(R.id.recyclerview_sizeintentory_row_edittext_count);

            buttonSize.setOnClickListener(v -> {
                if(inventoryRowInterface == null) return;
                int pos = getAdapterPosition();
                if(pos == RecyclerView.NO_POSITION) return;
                inventoryRowInterface.onClick(pos);
            });

            editTextCount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(inventoryRowInterface == null) return;
                    int pos = getAdapterPosition();
                    if(pos == RecyclerView.NO_POSITION) return;

                    int number = (s.length() > 0) ? Integer.parseInt(s.toString()) : 0;
                    inventoryRowInterface.onKeyPress(pos, number);
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }

        private void toggleSelected(boolean selected) {
            if(!selected) {
                root.setBackground(null);
                buttonSize.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.neutral_100));
                buttonSize.setTextColor(ContextCompat.getColorStateList(context, R.color.neutral_700));
                buttonSize.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_checkbox_off, 0, 0, 0);

                labelItems.setVisibility(View.GONE);
                imageItems.setVisibility(View.GONE);
                editTextCount.setVisibility(View.GONE);
                return;
            }

            root.setBackground(ContextCompat.getDrawable(context, R.drawable.background_rounded_blue));
            buttonSize.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.blue_400));
            buttonSize.setTextColor(ContextCompat.getColorStateList(context, R.color.neutral_100));
            buttonSize.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_checkbox_on, 0, 0, 0);
            labelItems.setVisibility(View.VISIBLE);
            imageItems.setVisibility(View.VISIBLE);
            editTextCount.setVisibility(View.VISIBLE);
        }
    }
}
