package com.example.shopmanager.Stocks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmanager.R;

import java.util.ArrayList;

public class StockRecyclerViewAdapter extends RecyclerView.Adapter<StockRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<StockDisplayModel> stockModels;

    public StockRecyclerViewAdapter(Context context, ArrayList<StockDisplayModel> stock) {
        this.context = context;
        this.stockModels = stock;
    }

    @NonNull
    @Override
    public StockRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_stock, parent, false);
        return new StockRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockRecyclerViewAdapter.ViewHolder holder, int position) {
        StockDisplayModel element = stockModels.get(position);

        holder.name.setText(new StringBuilder().append(element.getBrand()).append(" - ").append(element.getName()));
        holder.category.setText(element.getCategory());
        holder.gender.setVisibility(View.INVISIBLE);

        if(element.isSaleEnabled()) {
            holder.currentPrice.setText(String.valueOf(element.getSalePrice()));
            holder.originalPrice.setText(String.valueOf(element.getPrice()));
            int discount = (int) ((element.getPrice() - element.getSalePrice()) / element.getPrice() * 100);
            holder.discountPercent.setText(String.valueOf(discount) + "%");
        } else {
            holder.currentPrice.setText(String.valueOf(element.getPrice()));
            holder.originalPrice.setVisibility(View.INVISIBLE);
            holder.discountPercent.setVisibility(View.INVISIBLE);
        }

        holder.totalStock.setText(String.valueOf(element.getTotalStock()));

        element.getSizes().forEach((size, amount) -> {
            holder.sizesList.addView(createSizeElement(size, amount));
        });
    }

    @Override
    public int getItemCount() {
        return stockModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView category;
        TextView gender;

        TextView currentPrice;
        TextView originalPrice;
        TextView discountPercent;

        LinearLayout sizesList;
        TextView totalStock;

        public ViewHolder(@NonNull View view) {
            super(view);
            // TODO: SETUP HANDLE FOR SIZES

            name = view.findViewById(R.id.recyclerview_stock_text_name);
            category = view.findViewById(R.id.recyclerview_stock_text_category);
            gender = view.findViewById(R.id.recyclerview_stock_text_gender);

            currentPrice = view.findViewById(R.id.recyclerview_stock_text_currentprice);
            originalPrice = view.findViewById(R.id.recyclerview_stock_text_originalprice);
            discountPercent = view.findViewById(R.id.recyclerview_stock_text_discountpercent);

            sizesList = view.findViewById(R.id.recyclerview_stock_list_sizes);
            totalStock = view.findViewById(R.id.recyclerview_stock_text_totalstock);
        }
    }

    public View createSizeElement(String size, int amount) {
        LinearLayout stockItemView = new LinearLayout(context);
        TextView sizeView = new TextView(context);
        TextView amountView = new TextView(context);

        stockItemView.setBackground(ContextCompat.getDrawable(context, R.drawable.background_rounded_full_blue));
        stockItemView.setPadding(8, 2, 8, 2);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = 8;
        stockItemView.setLayoutParams(params);
        stockItemView.addView(sizeView);
        stockItemView.addView(amountView);

        sizeView.setText(size);
        sizeView.setTypeface(ResourcesCompat.getFont(context, R.font.poppins_semibold));
        sizeView.setTextColor(ContextCompat.getColor(context, R.color.neutral_100));
        sizeView.setTextSize(10);

        amountView.setText(new StringBuilder().append(" - ").append(amount));
        amountView.setTypeface(ResourcesCompat.getFont(context, R.font.poppins_semibold));
        amountView.setTextColor(ContextCompat.getColor(context, R.color.blue_300));
        amountView.setTextSize(10);

        return stockItemView;
    }
}
