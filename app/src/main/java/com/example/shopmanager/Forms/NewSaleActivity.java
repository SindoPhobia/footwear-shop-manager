package com.example.shopmanager.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shopmanager.MainActivity;
import com.example.shopmanager.R;
import com.example.shopmanager.Sales.SalesActivity;
import com.example.shopmanager.Storage.Firestore.Collections.Sale;
import com.example.shopmanager.Storage.Firestore.Collections.SoldStock;
import com.example.shopmanager.Storage.Firestore.FirestoreDB;
import com.example.shopmanager.Storage.RoomApi.Shoe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class NewSaleActivity extends AppCompatActivity {

    AddStockRecyclerViewAdapter addStockRecyclerViewAdapter;
    ArrayList<AddStockModel> addStockModelList;

    RecyclerView addStockRecyclerView;

    ConstraintLayout containerInputNewSale;
    ConstraintLayout containerResults;
    LinearLayout listResults;

    ConstraintLayout containerResultsColorSelect;
    TextView textResultColorSelectName;
    LinearLayout listResultsColors;

    ConstraintLayout containerResultsSizeSelect;
    TextView textResultSizeSelectName;
    TextView textResultSizeSelectColor;
    LinearLayout listResultsSizes;

    FloatingActionButton buttonBack;
    EditText inputSearchProduct;
    TextInputEditText inputSoldBy;
    Button buttonAddSale;

    ConstraintLayout stockError;
    ConstraintLayout soldByError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sale);

        addStockRecyclerView = findViewById(R.id.activity_newsale_container_form_recyclerview_products);

        containerInputNewSale = findViewById(R.id.activity_newsale_container_form_container_searchiteminputwrapper);
        containerResults = findViewById(R.id.activity_newsale_container_form_container_results);
        listResults = findViewById(R.id.activity_newsale_container_form_list_results);
        containerResultsColorSelect = findViewById(R.id.activity_newsale_container_form_container_selectcolor);
        listResultsColors = findViewById(R.id.activity_newsale_container_form_container_selectcolor_list_colors);
        textResultColorSelectName = findViewById(R.id.activity_newsale_container_form_container_selectcolor_text_selectedname);
        containerResultsSizeSelect = findViewById(R.id.activity_newsale_container_form_container_selectsize);
        textResultSizeSelectName = findViewById(R.id.activity_newsale_container_form_container_selectsize_text_selectname);
        textResultSizeSelectColor = findViewById(R.id.activity_newsale_container_form_container_selectsize_text_selectcolor);
        listResultsSizes = findViewById(R.id.activity_newsale_container_form_container_selectsize_list_sizes);

        inputSearchProduct = findViewById(R.id.activity_newsale_container_form_edittext_searchproduct);
        inputSoldBy = findViewById(R.id.activity_newsale_container_form_edittext_soldby);
        buttonBack = findViewById(R.id.activity_newsale_container_header_button_back);
        buttonAddSale = findViewById(R.id.activity_newsale_button_addsale);

        stockError = findViewById(R.id.activity_newsale_container_form_container_searcherror);
        soldByError = findViewById(R.id.activity_newsale_container_form_container_soldbyerror);

        buttonBack.setOnClickListener(v -> {
            onBackPressed();
        });

        inputSearchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchForResults(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addStockModelList = new ArrayList<>();
        addStockRecyclerViewAdapter = new AddStockRecyclerViewAdapter(this, addStockModelList);
        addStockRecyclerView.setAdapter(addStockRecyclerViewAdapter);
        addStockRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAddSale.setOnClickListener(v -> {
            boolean hasError = false;

            if (inputSoldBy.getText().length() == 0) {
                soldByError.setVisibility(View.VISIBLE);
                hasError = true;
            } else {
                soldByError.setVisibility(View.GONE);
            }

            if (addStockModelList.size() == 0) {
                stockError.setVisibility(View.VISIBLE);
                hasError = true;
            } else {
                stockError.setVisibility(View.GONE);
            }


            if (hasError) return;
            SoldStock[] sold = addStockModelList.stream().map(item -> new SoldStock(item.getId(), item.getSize(),item.isSaleEnabled() ? item.getSalePrice() : item.getPrice())).toArray(SoldStock[]::new);
            FirestoreDB.addSale(new Sale(inputSoldBy.getText().toString(), new Date().getTime(), sold), new FirestoreDB.Callback() {
                @Override
                public void onComplete(Sale[] sales) {
                    Intent i = new Intent(getApplicationContext(), SalesActivity.class);
                    startActivity(i);
                    finish();
                    notifySaleUpload(Integer.parseInt(sales[0].getId()));
                }

                @Override
                public void onError() {
                }
            });
        });
    }

    private void notifySaleUpload(int id){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "SHOP_MANAGER_CHANNEL_ID")
                .setStyle(new NotificationCompat.BigTextStyle()).setSmallIcon(R.drawable.icon_sales)
                .setContentTitle("Sale Creation")
                .setContentText("Sale successfully uploaded!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(id, builder.build());
    }

    private void hideContainers(){
        containerResultsColorSelect.setVisibility(View.GONE);
        containerResultsSizeSelect.setVisibility(View.GONE);
        containerResults.setVisibility(View.GONE);
    }

    private void searchForResults(String input) {
        if(input.length() > 0) {
            containerResultsColorSelect.setVisibility(View.GONE);
            containerResultsSizeSelect.setVisibility(View.GONE);
            containerResults.setVisibility(View.VISIBLE);
        } else {
            hideContainers();
        }

        listResults.removeAllViews();

        ArrayList<Shoe> stock = (ArrayList<Shoe>) MainActivity.stockDatabase.stockDao().getStock(input.replaceAll("-| ", ""));

        if(stock.size()==0){
            hideContainers();
        }

        int length = Math.min(stock.size(), 6);
        for(int i = 0; i < length; i++) {
            Shoe result = stock.get(i);
            String name = new StringBuilder().append(result.getBrand()).append(" - ").append(result.getName()).toString();

            View resultView = getLayoutInflater().inflate(R.layout.view_result, null, false);
            ((TextView)resultView.findViewById(R.id.view_result_text_result)).setText(name);

            resultView.setOnClickListener(v -> {
                prepareColorSelect(result);
            });

            listResults.addView(resultView);
        }
    }

    private void prepareColorSelect(Shoe selectedShoe) {
        textResultColorSelectName.setText(selectedShoe.getName());
        listResultsColors.removeAllViews();

        ArrayList<Shoe> similarShoes = (ArrayList<Shoe>) MainActivity.stockDatabase.stockDao().getShoesSameColor(selectedShoe.getCode());

        for(Shoe shoe : similarShoes) {
            TextView colorView = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_colorselectrow, null, false);
            colorView.setText(shoe.getColor());

            colorView.setOnClickListener(v -> {
                prepareSizeSelect(shoe);
            });

            listResultsColors.addView(colorView);
        }

        containerResults.setVisibility(View.GONE);
        containerResultsSizeSelect.setVisibility(View.GONE);
        containerResultsColorSelect.setVisibility(View.VISIBLE);
    }

    private void prepareSizeSelect(Shoe selectedShoe) {
        textResultSizeSelectName.setText(selectedShoe.getName());
        textResultSizeSelectColor.setText(selectedShoe.getColor());
        listResultsSizes.removeAllViews();

        HashMap<String, Integer> sizes = selectedShoe.getSizesFormatted();
        sizes.forEach((size, count) -> {
            TextView sizeView = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_sizeselectorrow, null, false);
            sizeView.setText(size);

            sizeView.setOnClickListener(v -> {
                addSaleEntry(selectedShoe, size);
                inputSearchProduct.setText("");
                containerResults.setVisibility(View.GONE);
                containerResultsColorSelect.setVisibility(View.GONE);
                containerResultsSizeSelect.setVisibility(View.GONE);
            });

            listResultsSizes.addView(sizeView);
        });

        containerResultsColorSelect.setVisibility(View.GONE);
        containerResultsSizeSelect.setVisibility(View.VISIBLE);
    }

    private void addSaleEntry(Shoe selectedShoe, String selectedSize) {
        AddStockModel entry = new AddStockModel(selectedShoe.getName(), selectedShoe.getBrand(), selectedShoe.getCategory(), selectedSize, selectedShoe.getId(), selectedShoe.getPrice(), selectedShoe.isSale_enabled(), selectedShoe.getSale_price());

        addStockModelList.add(entry);
        addStockRecyclerViewAdapter.notifyItemChanged(addStockModelList.size() - 1);
    }
}