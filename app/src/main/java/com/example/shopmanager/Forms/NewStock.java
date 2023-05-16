package com.example.shopmanager.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shopmanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewStock extends AppCompatActivity {

    static final int TOTAL_FORM_LOCATIONS = 4;
    int currentFormLocationIndex;

    FragmentManager formTabsManager;

    FloatingActionButton buttonBack;
    Button buttonNextTab, buttonPreviousTab;
    FragmentContainerView formFragments;

    View tabProgress1, tabProgress2, tabProgress3, tabProgress4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_stock);

        buttonNextTab = findViewById(R.id.activity_newstock_button_nexttab);
        buttonPreviousTab = findViewById(R.id.activity_newstock_button_previoustab);
        formFragments = findViewById(R.id.activity_newstock_fragment_form);

        tabProgress1 = findViewById(R.id.activity_newstock_header_progress1);
        tabProgress2 = findViewById(R.id.activity_newstock_header_progress2);
        tabProgress3 = findViewById(R.id.activity_newstock_header_progress3);
        tabProgress4 = findViewById(R.id.activity_newstock_header_progress4);

        buttonBack = findViewById(R.id.activity_newsale_container_header_button_back);
        buttonBack.setOnClickListener(v -> {
            onBackPressed();
        });

        formTabsManager = getSupportFragmentManager();

        currentFormLocationIndex = 0;

        buttonNextTab.setOnClickListener(v -> {
            if(currentFormLocationIndex == TOTAL_FORM_LOCATIONS - 1) return;
            changeFormTab(++currentFormLocationIndex);
        });

        buttonPreviousTab.setOnClickListener(v -> {
            if(currentFormLocationIndex == 0) return;
            changeFormTab(--currentFormLocationIndex);
        });

    }

    private void changeFormTab(int location) {
        Fragment fragmentFormTab;

        if(location == 0) {
            buttonPreviousTab.setVisibility(View.GONE);
        } else {
            buttonPreviousTab.setVisibility(View.VISIBLE);
        }

        if(location == TOTAL_FORM_LOCATIONS - 1) {
            buttonNextTab.setText(getString(R.string.activity_newstock_button_complete));
        } else {
            buttonNextTab.setText(getString(R.string.activity_newstock_button_nexttab));
        }

        switch(location) {
            case 0:
                tabProgress1.setBackground(ContextCompat.getDrawable(this, R.drawable.background_rounded_full_blue));
                tabProgress2.setBackground(ContextCompat.getDrawable(this, R.drawable.background_rounded_full_outlined));
                tabProgress3.setBackground(ContextCompat.getDrawable(this, R.drawable.background_rounded_full_outlined));
                tabProgress4.setBackground(ContextCompat.getDrawable(this, R.drawable.background_rounded_full_outlined));
                fragmentFormTab = new NewStockFormBasicDetailsFragment();
                break;
            case 1:
                tabProgress1.setBackground(ContextCompat.getDrawable(this, R.drawable.background_rounded_full_blue));
                tabProgress2.setBackground(ContextCompat.getDrawable(this, R.drawable.background_rounded_full_blue));
                tabProgress3.setBackground(ContextCompat.getDrawable(this, R.drawable.background_rounded_full_outlined));
                tabProgress4.setBackground(ContextCompat.getDrawable(this, R.drawable.background_rounded_full_outlined));
                fragmentFormTab = new NewStockFormCategorizations();
                break;
            case 2:
                tabProgress1.setBackground(ContextCompat.getDrawable(this, R.drawable.background_rounded_full_blue));
                tabProgress2.setBackground(ContextCompat.getDrawable(this, R.drawable.background_rounded_full_blue));
                tabProgress3.setBackground(ContextCompat.getDrawable(this, R.drawable.background_rounded_full_blue));
                tabProgress4.setBackground(ContextCompat.getDrawable(this, R.drawable.background_rounded_full_outlined));
                fragmentFormTab = new NewStockFormInventory();
                break;
            case 3:
                tabProgress1.setBackground(ContextCompat.getDrawable(this, R.drawable.background_rounded_full_blue));
                tabProgress2.setBackground(ContextCompat.getDrawable(this, R.drawable.background_rounded_full_blue));
                tabProgress3.setBackground(ContextCompat.getDrawable(this, R.drawable.background_rounded_full_blue));
                tabProgress4.setBackground(ContextCompat.getDrawable(this, R.drawable.background_rounded_full_blue));
                fragmentFormTab = new NewStockFormPrice();
                break;
            default:
                fragmentFormTab = new NewStockFormBasicDetailsFragment();
        }

        FragmentTransaction transaction = formTabsManager.beginTransaction();
        transaction.replace(R.id.activity_newstock_fragment_form, fragmentFormTab).commit();
    }
}