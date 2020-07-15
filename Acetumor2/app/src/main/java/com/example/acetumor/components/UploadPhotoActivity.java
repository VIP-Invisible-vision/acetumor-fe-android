package com.example.acetumor.components;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.acetumor.R;
import com.squareup.picasso.Picasso;

import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TIntentWap;
import org.devio.takephoto.model.TResult;

import java.io.File;
import java.util.ArrayList;

public class UploadPhotoActivity extends org.devio.takephoto.app.TakePhotoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomHelper customHelper = new CustomHelper(findViewById(R.id.activity_test_image));
        customHelper.onClick(R.id.test_photo, getTakePhoto());
        setContentView(R.layout.show_iamge);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        startActivity(new Intent(this, TestImageActivity.class));
    }

    @Override
    public void takeSuccess(TResult result) {
//        if (result.getImage().getOriginalPath() != null) {
//            Log.d("here", "TAKE success!!!!!!!!!!!!!!!!!!!!" + result.getImage().getOriginalPath());
//        }
        File imgFile = new File(result.getImage().getOriginalPath());

        ImageView imageView = (ImageView)findViewById(R.id.result_image);

        if (imageView == null) {Log.d("here", "NO!!!!!!!!!!!!!!!!!!!!!!!!!");}

        Picasso.get().load(imgFile).into(imageView);
    }

    @Override
    public void takeCancel() {
        Log.d("here", "TAKE CANCEL");
        super.onBackPressed();
    }


}
