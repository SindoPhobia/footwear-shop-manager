package com.example.shopmanager.Forms;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.shopmanager.R;
import com.example.shopmanager.Storage.RoomApi.Shoe;
import com.google.android.material.textfield.TextInputEditText;

public class NewStockFormCategorizations extends Fragment implements NewStock.FormFragment {

    ConstraintLayout errorCategory;
    ConstraintLayout errorGender;

    TextInputEditText editTextCategories;
    RadioGroup radioGroupGender;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newstock_form_categorizations, container, false);

        radioGroupGender = view.findViewById(R.id.fragment_newstock_form_categorizations_radiogroup_gender);
        editTextCategories = view.findViewById(R.id.fragment_newstock_form_categorizations_edittext_category);
        errorCategory = view.findViewById(R.id.fragment_newstock_form_categorizations_constraint_categoryerror);
        errorGender = view.findViewById(R.id.fragment_newstock_form_categorizations_constraint_gendererror);

        return view;
    }

    @Override
    public boolean validateForm() {
        boolean isValid = true;

        if(editTextCategories.getText().length() == 0) {
            errorCategory.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorCategory.setVisibility(View.GONE);
        }

        if(radioGroupGender.getCheckedRadioButtonId() == -1) {
            errorGender.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            errorGender.setVisibility(View.GONE);
        }

        return isValid;
    }

    @Override
    public void fillData(Shoe data) {
        data.setCategory(editTextCategories.getText().toString());
        RadioButton btn = radioGroupGender.findViewById(radioGroupGender.getCheckedRadioButtonId());
        String text = btn.getText().toString();
        data.setGender(Shoe.getGenderCode(text));
    }
}