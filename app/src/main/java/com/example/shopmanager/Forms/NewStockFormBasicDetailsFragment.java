package com.example.shopmanager.Forms;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.shopmanager.MainActivity;
import com.example.shopmanager.R;
import com.example.shopmanager.Storage.RoomApi.Shoe;
import com.example.shopmanager.Storage.RoomApi.StockDB;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class NewStockFormBasicDetailsFragment extends Fragment implements NewStockActivity.FormFragment {

    TextInputEditText editTextName;
    TextInputEditText editTextBrand;
    TextInputEditText editTextCode;
    TextInputEditText editTextColor;

    ConstraintLayout errorGlobal;
    ConstraintLayout errorName;
    ConstraintLayout errorBrand;
    ConstraintLayout errorCode;
    ConstraintLayout errorColor;

    ImageView img;

    Button imageBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_newstock_form_basicdetails, container, false);

        editTextName = view.findViewById(R.id.fragment_newstock_form_basicdetails_edittext_name);
        editTextBrand = view.findViewById(R.id.fragment_newstock_form_basicdetails_edittext_brand);
        editTextCode = view.findViewById(R.id.fragment_newstock_form_basicdetails_edittext_code);
        editTextColor = view.findViewById(R.id.fragment_newstock_form_basicdetails_edittext_color);

        errorGlobal = view.findViewById(R.id.fragment_newstock_form_basicdetails_constraint_globalerror);
        errorName = view.findViewById(R.id.fragment_newstock_form_basicdetails_constraint_nameerror);
        errorBrand = view.findViewById(R.id.fragment_newstock_form_basicdetails_constraint_branderror);
        errorCode = view.findViewById(R.id.fragment_newstock_form_basicdetails_constraint_codeerror);
        errorColor = view.findViewById(R.id.fragment_newstock_form_basicdetails_constraint_colorerror);

        img = view.findViewById(R.id.fragment_newstock_form_basicdetails_imageview);

        imageBtn = view.findViewById(R.id.fragment_newstock_form_basicdetails_button_addimage);
        imageBtn.setOnClickListener(c -> {
            chooseImage();
        });

        Shoe state = NewStockActivity.shoe;
        if(state.getName() != null) editTextName.setText(state.getName());
        if(state.getBrand() != null) editTextBrand.setText(state.getBrand());
        if(state.getCode() != null) editTextCode.setText(state.getColor());
        if(state.getColor() != null) editTextColor.setText(state.getColor());
        if(state.getImg() != null) {
            bitmap = StockDB.decodeBlob(state.getImg());
            img.setImageBitmap(bitmap);
            imageBtn.setText("");
        }

        return view;
    }


    private void chooseImage(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), 200);
    }

    Bitmap bitmap;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 200) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), selectedImageUri);
                        img.setImageBitmap(bitmap);
                        imageBtn.setText("");

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public boolean validateForm() {
        boolean isValid = true;

        if(editTextName.getText().length() == 0) {
            isValid = false;
            errorName.setVisibility(View.VISIBLE);
        } else {
            errorName.setVisibility(View.GONE);
        }

        if(editTextBrand.getText().length() == 0) {
            isValid = false;
            errorBrand.setVisibility(View.VISIBLE);
        } else {
            errorBrand.setVisibility(View.GONE);
        }

        if(editTextCode.getText().length() == 0) {
            isValid = false;
            errorCode.setVisibility(View.VISIBLE);
        } else {
            errorCode.setVisibility(View.GONE);
        }

        if(editTextColor.getText().length() == 0) {
            isValid = false;
            errorColor.setVisibility(View.VISIBLE);
        } else {
            errorColor.setVisibility(View.GONE);
        }


        List<Shoe> similarShoes = MainActivity.stockDatabase.stockDao().getExistingStock(
                editTextBrand.getText().toString(),
                editTextCode.getText().toString(),
                editTextColor.getText().toString()
        );

        if(NewStockActivity.shoe.getId()==0 && similarShoes.size() > 0) {
            isValid = false;
            errorGlobal.setVisibility(View.VISIBLE);
        } else {
            errorGlobal.setVisibility(View.GONE);
        }


        return isValid;
    }


    @Override
    public void fillData(Shoe data) {
        data.setName(editTextName.getText().toString());
        data.setBrand(editTextBrand.getText().toString());
        data.setColor(editTextColor.getText().toString());
        data.setCode(editTextCode.getText().toString());
        data.setImg(StockDB.decodeBitmap(bitmap));
        data.setDate(new Date().getTime());
    }

}