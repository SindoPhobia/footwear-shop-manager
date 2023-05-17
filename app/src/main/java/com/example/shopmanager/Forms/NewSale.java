package com.example.shopmanager.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.TypefaceCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopmanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class NewSale extends AppCompatActivity {

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
    }

    private void searchForResults(String input) {
        if(input.length() > 0) {
            containerResultsColorSelect.setVisibility(View.GONE);
            containerResultsSizeSelect.setVisibility(View.GONE);
            containerResults.setVisibility(View.VISIBLE);
        } else {
            containerResultsColorSelect.setVisibility(View.GONE);
            containerResultsSizeSelect.setVisibility(View.GONE);
            containerResults.setVisibility(View.GONE);
        }

        // TODO: Get all shoes that match input
        String[] results = { "Nike - puma", "Adibas - cringe", "One salonica??" };


        listResults.removeAllViews();

        for(String result : results) {
            View resultView = getLayoutInflater().inflate(R.layout.view_result, null, false);
            ((TextView)resultView.findViewById(R.id.view_result_text_result)).setText(result);
            
            resultView.setOnClickListener(v -> {
                prepareColorSelect(result);
            });

            listResults.addView(resultView);
        }

    }

    private void prepareColorSelect(String selectedShoe) {
        textResultColorSelectName.setText(selectedShoe);

        // TODO: Get all colors of shoe
        String[] colors = { "Black", "White", "Skati... kserwgw" };

        listResultsColors.removeAllViews();

        for(String color : colors) {
            TextView colorView = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_colorselectrow, null, false);
            colorView.setText(color);

            colorView.setOnClickListener(v -> {
                prepareSizeSelect(selectedShoe, color);
            });

            listResultsColors.addView(colorView);
        }

        containerResults.setVisibility(View.GONE);
        containerResultsSizeSelect.setVisibility(View.GONE);
        containerResultsColorSelect.setVisibility(View.VISIBLE);
    }

    private void prepareSizeSelect(String selectedShoe, String selectedColor) {
        textResultSizeSelectName.setText(selectedShoe);
        textResultSizeSelectColor.setText(selectedColor);

        // TODO: Get all sizes of shoe
        String[] sizes = { "41" , "42", "43" };

        listResultsSizes.removeAllViews();

        for(String size : sizes) {
            TextView sizeView = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_sizeselectorrow, null, false);
            sizeView.setText(size);

            sizeView.setOnClickListener(v -> {
                addSaleEntry(selectedShoe, selectedColor, size);
                inputSearchProduct.setText("");
                containerResults.setVisibility(View.GONE);
                containerResultsColorSelect.setVisibility(View.GONE);
                containerResultsSizeSelect.setVisibility(View.GONE);
            });

            listResultsSizes.addView(sizeView);
        }

        containerResultsColorSelect.setVisibility(View.GONE);
        containerResultsSizeSelect.setVisibility(View.VISIBLE);
    }

    private void addSaleEntry(String selectedShoe, String selectedColor, String selectedSize) {
        // TODO: Get all shoe data
        AddStockModel entry = new AddStockModel("", selectedShoe, "Category", selectedSize, 1, 420, false, 0);

        addStockModelList.add(entry);
        addStockRecyclerViewAdapter.notifyItemChanged(addStockModelList.size() - 1);
    }
}