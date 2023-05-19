package com.example.shopmanager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.CursorWindow;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.shopmanager.Home.HomeActivity;
import com.example.shopmanager.Sales.SaleDisplayModel;
import com.example.shopmanager.Stocks.StockActivity;
import com.example.shopmanager.Stocks.StockDisplayModel;
import com.example.shopmanager.Storage.Analytics.SalesAnalytics;
import com.example.shopmanager.Storage.Firestore.Collections.Sale;
import com.example.shopmanager.Storage.Firestore.Collections.SoldStock;
import com.example.shopmanager.Storage.Firestore.FirestoreDB;
import com.example.shopmanager.Storage.RoomApi.Entities.Brands;
import com.example.shopmanager.Storage.RoomApi.Entities.Categories;
import com.example.shopmanager.Storage.RoomApi.Entities.Colors;
import com.example.shopmanager.Storage.RoomApi.Entities.Shoes;
import com.example.shopmanager.Storage.RoomApi.Entities.Stock;
import com.example.shopmanager.Storage.RoomApi.Shoe;
import com.example.shopmanager.Storage.RoomApi.StockDB;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static StockDB stockDatabase;
    public static FirestoreDB firestoreDB;
    public static SalesAnalytics salesAnalytics;

    public static ArrayList<SaleDisplayModel> sales;
    public static ArrayList<StockDisplayModel> stock;

    CheckBox checkbox1, checkbox2, checkbox3;



    public interface SearchFilter{
        public void setUpSearchFilter();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkbox1 = findViewById(R.id.main_activity_checkbox_1);
        checkbox2 = findViewById(R.id.main_activity_checkbox_2);
        checkbox3 = findViewById(R.id.main_activity_checkbox_3);

        createNotificationChannel();

        stockDatabase = Room.databaseBuilder(
                getApplicationContext(), StockDB.class, "StockDB"
                ).allowMainThreadQueries().build();

        Intent dataIntent = getIntent();
        String data = dataIntent.getDataString();
        Intent startActivityIntent;

        if(data == null) {
            startActivityIntent = new Intent(this, HomeActivity.class);
        } else {
            data = data.replace("shopmanager://", "");
            startActivityIntent = new Intent(this, StockActivity.class);
            startActivityIntent.putExtra("code", data);
        }

        checkbox1.setChecked(true);


        firestoreDB = new FirestoreDB();
        firestoreDB.init(FirebaseFirestore.getInstance());

        salesAnalytics = new SalesAnalytics();
        salesAnalytics.init(new FirestoreDB.CallbackAggregate() {
            @Override
            public void onComplete(int count) {
                populateData(startActivityIntent);
            }

            @Override
            public void onError() {

            }
        });

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 32 * 1024 * 1024); //the 32MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: Grab data from both databases for the main recycler views

    }

    public static void updateStock(){
        List<StockDisplayModel> stockModel = new ArrayList<>(Arrays.asList(StockDisplayModel.parseStockToDisplayModel(
                (ArrayList<Shoe>) MainActivity.stockDatabase.stockDao().getStockDesc())));
        if(stockModel.size()>=1){
            stock = new ArrayList<>(stockModel);
        }else{
            stock = new ArrayList<>();
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("SHOP_MANAGER_CHANNEL_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public void populateData(Intent intent){
        updateStock();

        FirestoreDB.getLatestSales(50, new FirestoreDB.Callback() {
            @Override
            public void onComplete(Sale[] salesData) {
                ArrayList<SaleDisplayModel> salesDisplayModel = new ArrayList<>();
                checkbox2.setChecked(true);
                for(Sale sale : salesData){
                    SaleDisplayModel saleDisplayModel = new SaleDisplayModel(
                            sale.getId(),
                            sale.getSeller(),
                            sale.getDate()
                    );
                    SaleDisplayModel.StockDisplayModel[] soldStock = new SaleDisplayModel.StockDisplayModel[sale.getSoldStock().length];
                    for(int i=0;i<sale.getSoldStock().length;i++){
                        SoldStock soldTmp = sale.getSoldStock()[i];
                        ArrayList<Shoe> returnData = (ArrayList<Shoe>) MainActivity.stockDatabase.stockDao().
                                getStock(soldTmp.getStock_id());
                        if(returnData.size()==0) {
                            SaleDisplayModel.StockDisplayModel soldStockDisplay = new SaleDisplayModel.StockDisplayModel(
                                    soldTmp.getSize(),
                                    soldTmp.getPrice()
                            );
                            soldStock[i] = soldStockDisplay;
                            continue;
                        }
                        Shoe shoe = returnData.get(0);

                        SaleDisplayModel.StockDisplayModel soldStockDisplay = new SaleDisplayModel.StockDisplayModel(
                                soldTmp.getStock_id(),
                                shoe.getName(),
                                shoe.getBrand(),
                                shoe.getColor(),
                                soldTmp.getSize(),
                                soldTmp.getPrice()
                        );
                        soldStock[i] = soldStockDisplay;
                    }
                    saleDisplayModel.setStockDisplayModel(soldStock);
                    salesDisplayModel.add(saleDisplayModel);

                }
                checkbox3.setChecked(true);
                startActivity(intent);
                finish();
                if(salesDisplayModel==null || salesDisplayModel.size()==0) {
                    sales = new ArrayList<>();
                    return;
                }
                sales = new ArrayList<>(salesDisplayModel);
            }

            @Override
            public void onError() {

            }
        });
    }

}