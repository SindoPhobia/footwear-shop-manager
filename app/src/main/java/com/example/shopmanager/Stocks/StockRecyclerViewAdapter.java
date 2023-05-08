package com.example.shopmanager.Stocks;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmanager.Home.HomeActivity;
import com.example.shopmanager.MainActivity;
import com.example.shopmanager.R;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class StockRecyclerViewAdapter extends RecyclerView.Adapter<StockRecyclerViewAdapter.ViewHolder> {

    private static final int SIZES_DISPLAY_COUNT = 3;
    boolean isDisplayedInHome;

    Context context;
    ArrayList<StockDisplayModel> stockModels;

    StockOnClickInterface onClickInterface;
    public interface StockOnClickInterface {
        void onClick(int position);
    }

    public StockRecyclerViewAdapter(
            Context context,
            ArrayList<StockDisplayModel> stock,
            StockOnClickInterface onClickInterface
    ) {
        this.context = context;
        this.stockModels = stock;
        this.onClickInterface = onClickInterface;

        isDisplayedInHome = (Activity) context instanceof HomeActivity;
    }

    public void filterStock(String newText){
        this.stockModels = (ArrayList<StockDisplayModel>)
                MainActivity.stock.stream().filter(item -> (item.getBrand()+" - "+item.getName()).toLowerCase().contains(newText.toLowerCase())).
                        collect(Collectors.toList());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StockRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_stock, parent, false);
        return new StockRecyclerViewAdapter.ViewHolder(view, onClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull StockRecyclerViewAdapter.ViewHolder holder, int position) {
        StockDisplayModel element = stockModels.get(position);
        if(isDisplayedInHome) {
            holder.name.setTextColor(ContextCompat.getColorStateList(context, R.color.neutral_100));

            holder.category.setTextColor(ContextCompat.getColorStateList(context, R.color.blue_400));
            holder.category.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.neutral_100));

            holder.currentPrice.setTextColor(ContextCompat.getColorStateList(context, R.color.neutral_100));
            holder.originalPrice.setTextColor(ContextCompat.getColorStateList(context, R.color.blue_300));

            holder.discountPercent.setTextColor(ContextCompat.getColorStateList(context, R.color.blue_400));
            holder.discountPercent.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.neutral_100));

            holder.totalStock.setTextColor(ContextCompat.getColorStateList(context, R.color.neutral_100));
            holder.totalStockIcon.setImageTintList(ContextCompat.getColorStateList(context, R.color.neutral_100));
            holder.totalStockLabel.setTextColor(ContextCompat.getColorStateList(context, R.color.blue_300));
        }

        holder.name.setText(new StringBuilder().append(element.getBrand()).append(" - ").append(element.getName()));
        holder.category.setText(element.getCategory());
        holder.gender.setVisibility(View.INVISIBLE);

        if(element.isSaleEnabled()) {
            holder.currentPrice.setText(new StringBuilder().append(element.getSalePrice()).append("€"));
            holder.originalPrice.setText(new StringBuilder().append(element.getPrice()).append("€"));
            int discount = (int) ((element.getPrice() - element.getSalePrice()) / element.getPrice() * 100);
            holder.discountPercent.setText(String.valueOf(discount) + "%");
        } else {
            holder.currentPrice.setText(new StringBuilder().append(element.getPrice()).append("€"));
            holder.originalPrice.setVisibility(View.INVISIBLE);
            holder.discountPercent.setVisibility(View.INVISIBLE);
        }

        holder.totalStock.setText(String.valueOf(element.getTotalStock()));

        holder.sizesList.removeAllViews();

        int sizesCount = Math.min(element.getSizes().size(), SIZES_DISPLAY_COUNT);
        Object[] keys = element.getSizes().keySet().toArray();
        for(int i = 0; i < sizesCount; i++) {
            holder.sizesList.addView(createSizeElement((String)keys[i], element.getSizes().get(keys[i]), isDisplayedInHome));
        }

        if(element.getSizes().size() > SIZES_DISPLAY_COUNT) {
            LinearLayout moreView = new LinearLayout(context);
            ImageView moreDotsView = new ImageView(context);

            int primaryColor = isDisplayedInHome ? R.color.blue_300 : R.color.neutral_300;
            int backgroundElement = isDisplayedInHome ? R.drawable.background_rounded_full_white : R.drawable.background_rounded_full_blue;

            moreDotsView.setImageResource(R.drawable.icon_dots);
            moreDotsView.setImageTintList(ContextCompat.getColorStateList(context, primaryColor));

            moreView.setBackground(ContextCompat.getDrawable(context, backgroundElement));
            moreView.setPadding(8, 7, 8, 7);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 8;
            moreView.setLayoutParams(params);
            moreView.addView(moreDotsView);

            holder.sizesList.addView(moreView);
        }
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
        ImageView totalStockIcon;
        TextView totalStockLabel;

        public ViewHolder(@NonNull View view, StockOnClickInterface onClickInterface) {
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
            totalStockIcon = view.findViewById(R.id.recyclerview_stock_image_totalstock);
            totalStockLabel = view.findViewById(R.id.recyclerview_stock_text_label_totalstock);

            view.setOnClickListener(v -> {
                if(onClickInterface == null) return;
                int pos = getAdapterPosition();
                if(pos == RecyclerView.NO_POSITION) return;
                onClickInterface.onClick(pos);
            });
        }
    }

    public View createSizeElement(String size, int amount, boolean isDisplayedInHome) {
        LinearLayout stockItemView = new LinearLayout(context);
        TextView sizeView = new TextView(context);
        TextView amountView = new TextView(context);

        int primaryColor = isDisplayedInHome ? R.color.blue_400 : R.color.neutral_100;
        int secondaryColor = isDisplayedInHome ? R.color.neutral_300 : R.color.blue_300;
        int backgroundElement = isDisplayedInHome ? R.drawable.background_rounded_full_white : R.drawable.background_rounded_full_blue;

        stockItemView.setBackground(ContextCompat.getDrawable(context, backgroundElement));
        stockItemView.setPadding(32, 6, 32, 6);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = 8;
        stockItemView.setLayoutParams(params);
        stockItemView.addView(sizeView);
        stockItemView.addView(amountView);

        sizeView.setText(size);
        sizeView.setTypeface(ResourcesCompat.getFont(context, R.font.poppins_semibold));
        sizeView.setTextColor(ContextCompat.getColor(context, primaryColor));
        sizeView.setTextSize(8);
        sizeView.setIncludeFontPadding(false);

        amountView.setText(new StringBuilder().append(" - ").append(amount));
        amountView.setTypeface(ResourcesCompat.getFont(context, R.font.poppins_semibold));
        amountView.setTextColor(ContextCompat.getColor(context, secondaryColor));
        amountView.setTextSize(8);
        amountView.setIncludeFontPadding(false);

        return stockItemView;
    }
}
