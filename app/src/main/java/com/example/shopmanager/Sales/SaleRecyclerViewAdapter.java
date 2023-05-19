package com.example.shopmanager.Sales;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmanager.MainActivity;
import com.example.shopmanager.R;
import com.example.shopmanager.Storage.Firestore.Collections.Sale;
import com.example.shopmanager.Storage.Firestore.Collections.SoldStock;
import com.example.shopmanager.Storage.Firestore.FirestoreDB;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class SaleRecyclerViewAdapter extends RecyclerView.Adapter<SaleRecyclerViewAdapter.ViewHolder>{

    Context context;
    ArrayList<SaleDisplayModel> sales;

    public SaleRecyclerViewAdapter(Context context, ArrayList<SaleDisplayModel> sales) {
        this.context = context;
        this.sales = sales;
    }

    public void filterSales(String newText){
        ArrayList<SaleDisplayModel> list = new ArrayList<>(
                Arrays.asList(MainActivity.sales.stream().filter(item -> item.getId().toLowerCase().contains(newText.toLowerCase())).toArray(SaleDisplayModel[]::new)));
        setSales(list);
    }

    public void setSales(ArrayList<SaleDisplayModel> newSales){
        this.sales = newSales;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_sale, parent, false);

        return new SaleRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(sales.get(position).getId());
        holder.seller.setText(sales.get(position).getSeller());

        Date date = new Date(sales.get(position).getDate());
        Format dateFormat = new SimpleDateFormat("dd/MM/yy");
        Format timeFormat = new SimpleDateFormat("HH:mm");
        holder.date.setText(dateFormat.format(date));
        holder.time.setText(timeFormat.format(date));

        holder.stock.removeAllViews();

        for(SaleDisplayModel.StockDisplayModel stock : sales.get(position).getStock()) {
            View rowView;
            if(stock.getStockId()==0) { //Then item has been deleted and stock id is default value = 0.
                rowView = this.createStockRowView("Deleted", "Deleted", stock.getSize(), stock.getPrice());
            }else{
                rowView = this.createStockRowView(stock.getBrand(), stock.getName(), stock.getSize(), stock.getPrice());
            }
            holder.stock.addView(rowView);
        }
    }

    @Override
    public int getItemCount() {
        return sales.size();
    }

    private void notifyDelete(int id){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), "SHOP_MANAGER_CHANNEL_ID")
                .setStyle(new NotificationCompat.BigTextStyle()).setSmallIcon(R.drawable.icon_sales)
                .setContentTitle("Sale Deleted")
                .setContentText("Sale successfully deleted!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context.getApplicationContext());

        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(id, builder.build());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView seller;
        TextView date;
        TextView time;

        LinearLayout stock;

        public ViewHolder(@NonNull View view) {
            super(view);

            view.setOnLongClickListener(c -> {
                int index = getAdapterPosition();
                SaleDisplayModel s = sales.get(index);
                SoldStock[] stocks =  Arrays.stream(s.getStock()).map(item ->
                        new SoldStock(item.getStockId(), item.getSize(), item.getPrice())).
                        toArray(SoldStock[]::new);
                Sale sale = new Sale(s.getSeller(), s.getDate(), stocks);
                sale.setId(s.getId());
                FirestoreDB.deleteSale(sale, new FirestoreDB.Callback() {
                    @Override
                    public void onComplete(Sale[] salez) {
                        sales.remove(index);
                        notifyDataSetChanged();
                        Random rnd = new Random();
                        notifyDelete(rnd.nextInt(100));
                    }

                    @Override
                    public void onError() {

                    }
                });
                return true;
            });

            id = view.findViewById(R.id.recyclerview_sale_text_id);
            seller = view.findViewById(R.id.recyclerview_sale_text_seller);
            date = view.findViewById(R.id.recyclerview_sale_text_date);
            time = view.findViewById(R.id.recyclerview_sale_text_time);
            stock = view.findViewById(R.id.recyclerview_sale_list_items);
        }
    }

    public View createStockRowView(String brand, String name, String size, float price) {
        ConstraintLayout view = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.recyclerview_sale_stock_row, null, false);
        TextView nameView = view.findViewById(R.id.recyclerview_sale_list_item_text_name);
        TextView sizeView = view.findViewById(R.id.recyclerview_sale_list_item_text_size);
        TextView priceView = view.findViewById(R.id.recyclerview_sale_list_item_text_price);

        nameView.setText(new StringBuilder().append(brand).append(" - ").append(name).toString());
        sizeView.setText(size);
        priceView.setText(String.valueOf(price));

        return view;
    }
}
