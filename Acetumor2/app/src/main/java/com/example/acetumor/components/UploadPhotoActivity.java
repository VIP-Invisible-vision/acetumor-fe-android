package com.example.acetumor.components;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.acetumor.R;

import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TIntentWap;
import org.devio.takephoto.model.TResult;

import java.util.ArrayList;

public class UploadPhotoActivity extends org.devio.takephoto.app.TakePhotoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomHelper customHelper = new CustomHelper(findViewById(R.id.activity_test_image));
        customHelper.onClick(R.id.test_photo, getTakePhoto());
//        setContentView(R.layout.activity_test_image);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        startActivity(new Intent(this, TestImageActivity.class));
    }

}
