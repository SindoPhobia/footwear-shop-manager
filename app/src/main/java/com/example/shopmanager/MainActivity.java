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
import android.widget.Toast;

import com.example.shopmanager.Home.HomeActivity;
import com.example.shopmanager.Sales.SaleDisplayModel;
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



    public interface SearchFilter{
        public void setUpSearchFilter();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        stockDatabase = Room.databaseBuilder(
                getApplicationContext(), StockDB.class, "StockDB"
                ).allowMainThreadQueries().build();

        Intent startActivityIntent = new Intent(this, HomeActivity.class);

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

        populateRoom();
    }

    private void populateRoom(){
        Brands brand1 = new Brands();
//        brand1.setId(1);
        brand1.setName("    ");
        Brands brand2 = new Brands();
//        brand2.setId(2);
        brand2.setName("Adidas");
        Brands brand3 = new Brands();
//        brand3.setId(3);
        brand3.setName("Puma");

        Categories cat1 = new Categories();
//        cat1.setId(1);
        cat1.setName("Athletic");
        Categories cat2 = new Categories();
//        cat2.setId(2);
        cat2.setName("Hiking");
        Categories cat3 = new Categories();
//        cat3.setId(3);
        cat3.setName("Walking");

        Colors color1 = new Colors();
//        color1.setId(1);
        color1.setName("Black");
        Colors color2 = new Colors();
//        color2.setId(2);
        color2.setName("White");
        Colors color3 = new Colors();
//        color3.setId(3);
        color3.setName("Red");

        Shoes shoe1 = new Shoes( "adibas", 42.5f,
                false, 42.5f, "ntelos123", new Date().getTime(), 0);
        Shoes shoe2 = new Shoes( "Adibas", 62.5f,
                false, 22.5f, "ntelos456", new Date().getTime(), 0);
        Shoes shoe3 = new Shoes( "Adibas", 12.5f,
                false, 12.5f, "ntelos789", new Date().getTime(), 0);

        Stock stock1 = new Stock( 1, 1, 1, 1);
        Stock stock2 = new Stock( 2, 2, 2, 2);
        Stock stock3 = new Stock( 3, 3, 3, 3);
        shoe1.setSizes("41-2,42-2,43-2,44-2,5-2,6-2,7-2,8-2,9-2");
        shoe2.setSizes("1-3,2-3,3-3,4-3,5-3,6-3,7-3,8-3,9-3");
        shoe3.setSizes("1-5,2-4");

//        stockDatabase.stockDao().insertBrands(brand1);
//        stockDatabase.stockDao().insertBrands(brand2);
//        stockDatabase.stockDao().insertBrands(brand3);
//        stockDatabase.stockDao().insertCategories(cat1);
//        stockDatabase.stockDao().insertCategories(cat2);
//        stockDatabase.stockDao().insertCategories(cat3);
//
//        stockDatabase.stockDao().insertColors(color1);
//        stockDatabase.stockDao().insertColors(color2);
//        stockDatabase.stockDao().insertColors(color3);
//
//        stockDatabase.stockDao().insertShoe(shoe1);
//        stockDatabase.stockDao().insertShoe(shoe2);
//        stockDatabase.stockDao().insertShoe(shoe3);
////
//        stockDatabase.stockDao().insertStock(stock1);
//        stockDatabase.stockDao().insertStock(stock2);
//        stockDatabase.stockDao().insertStock(stock3);
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