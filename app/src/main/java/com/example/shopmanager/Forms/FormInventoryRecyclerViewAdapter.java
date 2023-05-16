package com.example.shopmanager.Forms;

import android.content.Context;
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

    OnClickInterface onClickInterface;
    public interface OnClickInterface {
        public void onClick(int position);
    }

    public FormInventoryRecyclerViewAdapter(Context context, ArrayList<FormInventoryModel> inventoryList, OnClickInterface onClickInterface) {
        this.context = context;
        this.inventoryList = inventoryList;
        this.onClickInterface = onClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_sizeinventory_row, parent, false);
        return new ViewHolder(view, onClickInterface);
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

        public ViewHolder(@NonNull View itemView, OnClickInterface onClickInterface) {
            super(itemView);

            root = itemView.findViewById(R.id.recyclerview_sizeintentory_row_container_element);
            buttonSize = itemView.findViewById(R.id.recyclerview_sizeintentory_row_button_size);
            labelItems = itemView.findViewById(R.id.recyclerview_sizeintentory_row_text_items);
            imageItems = itemView.findViewById(R.id.recyclerview_sizeintentory_row_image_items);
            editTextCount = itemView.findViewById(R.id.recyclerview_sizeintentory_row_edittext_count);

            buttonSize.setOnClickListener(v -> {
                if(onClickInterface == null) return;
                int pos = getAdapterPosition();
                if(pos == RecyclerView.NO_POSITION) return;
                onClickInterface.onClick(pos);
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
