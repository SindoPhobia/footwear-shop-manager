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
import com.example.shopmanager.Storage.RoomApi.StockDao;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("SHOP_MANAGER_CHANNEL_ID", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createStock(){
        String[] cats = new String[]{"Lifestyle", "Running", "Basket", "Football"};
        for(int i=0;i<cats.length;i++){
            Categories cat = new Categories();
            cat.setName(cats[i]);
            stockDatabase.stockDao().insertCategories(cat);
        }
        Brands b1 = new Brands();
        b1.setName("Adidas");
        Brands b2 = new Brands();
        b2.setName("Nike");
        stockDatabase.stockDao().insertBrands(b1);
        stockDatabase.stockDao().insertBrands(b2);


        try{
            String[] colors = {"White", "Black", "Blue", "Green", "Red", "Yellow"};
            for(int i=0;i<colors.length;i++){
                Colors color = new Colors();
                color.setName(colors[i]);
                stockDatabase.stockDao().insertColors(color);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        Date d = new Date();
        String[] names = new String[]{"Stan Smith", "Air Force", "Air Jordan 1", "Mercurial", "Pegasus"};
        Shoes s1 = new Shoes("Stan Smith", 110f, false, 0f, "zfx-32", d.getTime(), 0, "42-3,44-4,45-6");
        Shoes s2 = new Shoes("Stan Smith", 110f, false, 0f, "zfx-32", d.getTime()+50, 0, "42-3,44-4,45-6,41-8");
        Shoes s3 = new Shoes("Stan Smith", 110f, false, 0f, "zfx-32", d.getTime()+60, 0, "42-3,44-10,45-6");
        Shoes s4 = new Shoes("Stan Smith", 110f, false, 0f, "zfx-32", d.getTime()+20, 0, "42-15,44-14");
        stockDatabase.stockDao().insertShoe(s1);
        stockDatabase.stockDao().insertShoe(s2);
        stockDatabase.stockDao().insertShoe(s3);
        stockDatabase.stockDao().insertShoe(s4);

        Stock st1 = new Stock(1, 1, 4, 1);
        Stock st2 = new Stock(2, 1, 2, 1);
        Stock st3 = new Stock(3, 1, 5, 1);
        Stock st4 = new Stock(4, 1, 3, 1);

        Shoes s5 = new Shoes("Air Force", 119.99f, true, 99.99f, "air-7001", d.getTime()+20, 2, "41-5,42-5,45-2");
        stockDatabase.stockDao().insertShoe(s5);
        Stock st5 = new Stock(5, 1, 1, 2);

        Shoes s6 = new Shoes("Air Jordan 1", 209.99f, false, 0f, "air-7002", d.getTime()+19, 2, "42-6,43-10");
        stockDatabase.stockDao().insertShoe(s6);
        Stock st6 = new Stock(6, 3, 5, 2);

        Shoes s7 = new Shoes("Mercurial", 74.99f, false, 0f, "merc-8001", d.getTime()+5, 0, "38-6,39-5,40-4,41-3,42-3,44-10");
        stockDatabase.stockDao().insertShoe(s7);
        Stock st7 = new Stock(7, 4, 6, 2);

        Shoes s8 = new Shoes("Pegasus", 129.99f, false, 0f,"pega-3001", d.getTime()+4, 2, "42-3,44-4,45-6,41-8");
        stockDatabase.stockDao().insertShoe(s8);
        Stock st8 = new Stock(8, 2, 2, 2);

        stockDatabase.stockDao().insertStock(st1);
        stockDatabase.stockDao().insertStock(st2);
        stockDatabase.stockDao().insertStock(st3);
        stockDatabase.stockDao().insertStock(st4);
        stockDatabase.stockDao().insertStock(st5);
        stockDatabase.stockDao().insertStock(st6);
        stockDatabase.stockDao().insertStock(st7);
        stockDatabase.stockDao().insertStock(st8);
    }


    public void populateData(Intent intent){
        updateStock();
        try{
//            createStock();
        }catch(Exception e){
            e.printStackTrace();
        }

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