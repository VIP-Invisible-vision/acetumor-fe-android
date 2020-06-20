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
        setContentView(R.layout.activity_take_photo);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        startActivity(new Intent(this, TestImageActivity.class));
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    private void showImg(ArrayList<TImage> images) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("images", images);
        startActivity(intent);
    }
}
