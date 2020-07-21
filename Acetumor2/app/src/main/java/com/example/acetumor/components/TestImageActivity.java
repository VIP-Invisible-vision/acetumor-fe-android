package com.example.acetumor.components;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.acetumor.MainActivity;
import com.example.acetumor.R;

import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;

import java.io.File;
import java.util.ArrayList;


public class TestImageActivity extends AppCompatActivity{
    private static final String TAG = "TestActivity";

    private TestImageButton selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image);
        final Button back = (Button) findViewById(R.id.test_back);
        final ImageButton camera = (ImageButton) findViewById(R.id.test_camera);
        final ImageButton gallery = (ImageButton) findViewById(R.id.test_photo);
        TestImageButton breast = new TestImageButton((ImageButton) findViewById(R.id.test_choice_breast), true);
        TestImageButton brain = new TestImageButton((ImageButton) findViewById(R.id.test_choice_brain), false);
        TestImageButton lung = new TestImageButton((ImageButton) findViewById(R.id.test_choice_lung), false);
        TestImageButton eye = new TestImageButton((ImageButton) findViewById(R.id.test_choice_eye), false);
        TestImageButton stomach = new TestImageButton((ImageButton) findViewById(R.id.test_choice_stomach), false);
        selected = breast;

        final TestImageButton[] buttons = new TestImageButton[]{breast, brain, lung, eye, stomach};
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // set image button bg color
        for (int i = 0; i < buttons.length; i++) {
            final int finalI = i;
            buttons[i].getBtn().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelected(buttons[finalI]);
                }
            });
        }

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TakePhotoActivity.class);
                intent.putExtra("type", selected.getBtn().getId());
                startActivity(intent);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UploadPhotoActivity.class);
                intent.putExtra("type", selected.getBtn().getId());
                startActivity(intent);
            }
        });

    }


    public void setSelected(TestImageButton selected) {
        if (this.selected == null) {
            Log.d("msg", "first");
            this.selected = selected;
            this.selected.getBtn().setBackground(getDrawable(R.drawable.round_button_selected));
            this.selected.updateClick();
        } else if (this.selected.equals(selected)) {
            Log.d("msg", "cancel");
            this.selected.updateClick();
            this.selected.getBtn().setBackground(getDrawable((R.drawable.round_button)));
            this.selected = null;
        } else {
            Log.d("msg", "update");
            this.selected.getBtn().setBackground(getDrawable(R.drawable.round_button));
            this.selected = selected;
            this.selected.updateClick();
            this.selected.getBtn().setBackground(getDrawable(R.drawable.round_button_selected));
        }
    }

}