package com.example.shopmanager.Stocks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopmanager.Forms.NewStockActivity;
import com.example.shopmanager.Home.HomeActivity;
import com.example.shopmanager.MainActivity;
import com.example.shopmanager.R;
import com.example.shopmanager.Storage.RoomApi.Entities.Shoes;
import com.example.shopmanager.Storage.RoomApi.Entities.Stock;
import com.example.shopmanager.Storage.RoomApi.Shoe;
import com.example.shopmanager.Storage.RoomApi.StockDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StockActivity extends AppCompatActivity{

    FloatingActionButton buttonBack;
    FloatingActionButton buttonEdit;
    FloatingActionButton buttonDelete;

    TextView name;
    TextView productCode;

    TextView category;
    TextView gender;

    TextView currentPrice;
    TextView originalPrice;
    TextView discountPercent;

    TextView inventoryCount;
    TextView sizesCount;
    TextView colorCount;

    ImageView shoeImg;
    ImageView imageQRCode;

    LinearLayout stockList;

    private void notifyDelete(int id){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "SHOP_MANAGER_CHANNEL_ID")
                .setStyle(new NotificationCompat.BigTextStyle()).setSmallIcon(R.drawable.icon_stock)
                .setContentTitle("Stock Deleted")
                .setContentText("Stock successfully deleted!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(id, builder.build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        String code = getIntent().getExtras().getString("code");
        ArrayList<Shoe> shoes = (ArrayList<Shoe>) MainActivity.stockDatabase.stockDao().getShoesSameColor(code);
        if(shoes.size()==0){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        Shoe initial = shoes.get(0);

        buttonBack = findViewById(R.id.activity_stock_container_header_button_back);
        buttonEdit = findViewById(R.id.activity_stock_container_header_button_edit);
        buttonDelete = findViewById(R.id.activity_stock_container_header_button_delete);

        buttonBack.setOnClickListener(v -> {
            onBackPressed();
        });

        buttonEdit.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), NewStockActivity.class);
            startActivity(i);
            NewStockActivity.shoe = initial;
        });

        buttonDelete.setOnClickListener(v -> {
            Stock deletedStock = MainActivity.stockDatabase.stockDao().getStockRaw(initial.getId());
            Shoes deletedShoe = MainActivity.stockDatabase.stockDao().getShoeRaw(initial.getCode());

            MainActivity.stockDatabase.stockDao().deleteStock(deletedStock);
            MainActivity.stockDatabase.stockDao().deleteShoe(deletedShoe);
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            MainActivity.updateStock();
            notifyDelete(5);
            startActivity(i);
            finish();
        });

        name = findViewById(R.id.activity_stock_container_details_text_name);
        productCode = findViewById(R.id.activity_stock_container_details_text_productcode);

        category = findViewById(R.id.activity_stock_container_categories_text_category);
        gender = findViewById(R.id.activity_stock_container_categories_text_gender);

        currentPrice = findViewById(R.id.activity_stock_container_details_text_currentprice);
        originalPrice = findViewById(R.id.activity_stock_container_details_text_originalprice);
        discountPercent = findViewById(R.id.activity_stock_container_details_text_salepercent);

        inventoryCount = findViewById(R.id.activity_stock_list_header_text_itemcount);
        sizesCount = findViewById(R.id.activity_stock_list_header_text_sizes);
        colorCount = findViewById(R.id.activity_stock_list_header_text_colors);
        shoeImg = findViewById(R.id.activity_stock_container_image_image_stock);
        imageQRCode = findViewById(R.id.activity_stock_qrcode_image);

        stockList = findViewById(R.id.activity_stock_list);

        if(initial.getImg()!=null){
            shoeImg.setImageBitmap(StockDB.decodeBlob(initial.getImg()));

            ViewGroup.LayoutParams layoutParams = shoeImg.getLayoutParams();
            layoutParams.width = 500;
            layoutParams.height = 500;
            shoeImg.setLayoutParams(layoutParams);
        }

        name.setText(new StringBuilder().append(initial.getBrand()).append(" - ").append(initial.getName()));
        productCode.setText(new StringBuilder().append("#").append(initial.getCode()));

        category.setText(initial.getCategory());
        gender.setText(Shoe.getGender(initial.getGender()));

        if(initial.isSale_enabled()) {
            currentPrice.setText(new StringBuilder(String.valueOf(initial.getSale_price())).append("€"));
            originalPrice.setText(new StringBuilder(String.valueOf(initial.getPrice())).append("€"));
            int discount = (int) ((initial.getPrice() - initial.getSale_price()) / initial.getPrice() * 100);
            discountPercent.setText(new StringBuilder().append(discount).append("%"));
        } else {
            currentPrice.setText(new StringBuilder(String.valueOf(initial.getPrice())).append("€"));
            originalPrice.setVisibility(View.INVISIBLE);
            discountPercent.setVisibility(View.INVISIBLE);
        }


        int totalInventory = 0;
        int totalColors = 0;
        int totalSizes = 0;
        for(int i =  0; i < shoes.size(); i++) {
            Shoe shoe = shoes.get(i);
            HashMap<String, Integer> sizes = shoe.getSizesFormatted();

            totalColors++;
            for(Map.Entry<String, Integer> entry : sizes.entrySet()) {
                totalInventory += entry.getValue();
                totalSizes++;
            }

            stockList.addView(createInventoryRow(shoe.getColor(), sizes));
        }

        inventoryCount.setText(new StringBuilder().append(totalInventory));
        colorCount.setText(new StringBuilder().append(totalColors));
        sizesCount.setText(new StringBuilder().append(totalSizes));

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "https://qrapi.vercel.app/api/generate?text=shopmanager://" + initial.getCode(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String code = response.getString("code").replace("data:image/png;base64,", "");
                            Log.d("QR", "Code: " + code);
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                byte[]  bytesImage = Base64.getDecoder().decode(code);
                                Bitmap image = StockDB.decodeBlob(bytesImage);
                                imageQRCode.setImageBitmap(image);
                                imageQRCode.setOnClickListener(v -> {
                                    saveImage(initial.getCode(), image);
                                });

                            } else {
                                Toast.makeText(StockActivity.this, "Can't load load QR Code image", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
            );

            requestQueue.add(request);
            requestQueue.start();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    private View createInventoryRow(String color, HashMap<String, Integer> sizes) {
        ConstraintLayout view = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.activity_stock_inventory_row, null, false);

        TextView colorView = view.findViewById(R.id.activity_stock_list_item_text_color);
        TextView totalSizesView = view.findViewById(R.id.activity_stock_list_item_text_totalsizes);
        LinearLayout colorList = view.findViewById(R.id.activity_stock_list_item_list_sizes);

        colorList.removeAllViews();

        Integer totalCount = 0;
        Object[] sizeKeys = sizes.keySet().toArray();
        for(int i = 0; i < sizes.size(); i++) {
            String size = (String) sizeKeys[i];
            Integer count = sizes.get(sizeKeys[i]);
            totalCount += count;

            colorList.addView(createInventoryRowsSizeTag(size, count));
        }

        colorView.setText(color);
        totalSizesView.setText(String.valueOf(totalCount));

        return view;
    }

    private View createInventoryRowsSizeTag(String size, Integer count) {
        LinearLayout sizeRoot = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_stock_inventory_row_size_tag, null, false);

        LinearLayout.LayoutParams sizeRootLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sizeRootLayoutParams.setMargins(0, 0, 16, 0);
        sizeRoot.setLayoutParams(sizeRootLayoutParams);

        TextView sizeView = sizeRoot.findViewById(R.id.activity_stock_list_item_list_sizes_item_text_size);
        TextView countView = sizeRoot.findViewById(R.id.activity_stock_list_item_list_sizes_item_sizeamount);

        sizeView.setText(size);
        countView.setText(String.valueOf(count));

        return sizeRoot;
    }

    private void notifyDownload(int id){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "SHOP_MANAGER_CHANNEL_ID")
                .setSmallIcon(R.drawable.icon_stock)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setContentTitle("QR-CODE Saved")
                .setContentText("QR-CODE for this product saved in your gallery.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(id, builder.build());
    }


    private Uri saveImage(String displayName, Bitmap image) {
        try {
            ContentValues imageData = new ContentValues();
            imageData.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
            imageData.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            imageData.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);

            ContentResolver resolver = getContentResolver();
            Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageData);

            OutputStream stream = resolver.openOutputStream(uri);
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();
            notifyDownload(16);
            return uri;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

