package com.example.shopmanager.Forms;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmanager.R;

import java.util.ArrayList;

public class AddStockRecyclerViewAdapter extends RecyclerView.Adapter<AddStockRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<AddStockModel> addStockModels;

    public AddStockRecyclerViewAdapter(Context context, ArrayList<AddStockModel> addStockModels) {
        this.context = context;
        this.addStockModels = addStockModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_addstock_stock, parent, false);
        return new AddStockRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddStockModel entry = addStockModels.get(position);

        holder.name.setText(new StringBuilder().append(entry.getBrand()).append(" - ").append(entry.getName()));
        holder.category.setText(entry.getCategory());
        holder.size.setText(entry.getSize());
        if(entry.isSaleEnabled()) {
            holder.price.setText(new StringBuilder().append(entry.getSalePrice()).append("€"));
            int discountPercent = (int)((entry.getPrice() - entry.getSalePrice()) / entry.getPrice() * 100);
            holder.discountPercent.setText(new StringBuilder().append(discountPercent).append("%"));
        } else {
            holder.price.setText(new StringBuilder().append(entry.getPrice()).append("€"));
        }

    }

    @Override
    public int getItemCount() {
        return addStockModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView category;
        TextView size;
        TextView price;
        TextView discountPercent;

        public ViewHolder(@NonNull View view) {
            super(view);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    addStockModels.remove(pos);
                    notifyItemRemoved(pos);
                    return true;
                }
            });

             name = view.findViewById(R.id.recyclerview_addstock_text_name);
             category = view.findViewById(R.id.recyclerview_addstock_text_category);
             size = view.findViewById(R.id.recyclerview_addstock_text_size);
             price = view.findViewById(R.id.recyclerview_addstock_text_currentprice);
             discountPercent = view.findViewById(R.id.recyclerview_addstock_text_discountprice);
        }
    }
}
