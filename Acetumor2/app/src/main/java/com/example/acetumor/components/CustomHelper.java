package com.example.acetumor.components;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.acetumor.R;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.LubanOptions;
import org.devio.takephoto.model.TakePhotoOptions;

import java.io.File;

public class CustomHelper {
    private View rootView;

    public static CustomHelper of(View rootView) {
        return new CustomHelper(rootView);
    }

    public CustomHelper(View rootView) {
        this.rootView = rootView;
    }

    public void onClick(int id, TakePhoto takePhoto) {
        Log.d("here", "in CustomHelper line 32");
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        switch (id) {
            case R.id.test_photo:
                int limit = 5; // maximum number of photos


//                takePhoto.onPickMultipleWithCrop(limit, getCropOptions()); //crop or not

                //Log.d("msg", "!!!!!!!");
                takePhoto.onPickFromGallery();

                break;
            case R.id.test_camera:
                Log.d("msg", "CustomHelper_line_79");
                takePhoto.onPickFromCapture(imageUri);
                break;
            default:
                break;
        }
    }

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();

        builder.setWithOwnGallery(true);

        takePhoto.setTakePhotoOptions(builder.create());

    }

    private void configCompress(TakePhoto takePhoto) {

        takePhoto.onEnableCompress(null, false);
    }

    private CropOptions getCropOptions() {
        int height = 800;
        int width = 800;
        boolean withWonCrop = true; // with take photo crop tool

        CropOptions.Builder builder = new CropOptions.Builder();


        builder.setOutputX(width).setOutputY(height);

        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }
}
