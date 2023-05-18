package com.example.shopmanager.Forms;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.shopmanager.MainActivity;
import com.example.shopmanager.R;

import java.io.IOException;

public class NewStockFormBasicDetailsFragment extends Fragment {

    Button imageBtn;

    ImageView img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_newstock_form_basicdetails, container, false);
        imageBtn = view.findViewById(R.id.fragment_newstock_form_basicdetails_button_addimage);
        imageBtn.setOnClickListener(c -> {
            chooseImage();
        });
        return view;
    }

    ImageView IVPreviewImage;

    private void chooseImage(){
        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 200);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("file", "test-1");
        if (resultCode == RESULT_OK) {
            Log.d("file", "test-2");

            // compare the resultCode with the
            // SELECT_PICTURE constant
            Log.d("file", String.valueOf(requestCode));
            if (requestCode == 200) {
                Log.d("file", "test-3");
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), selectedImageUri);
                        try{
                            MainActivity.stockDatabase.updateImage("ntelos123", bitmap);
                        }catch(Exception e) {
                            Log.w("error", e);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Log.d("file", "success");
                }
            }
        }
    }
}