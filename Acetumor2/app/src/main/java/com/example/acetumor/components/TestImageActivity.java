package com.example.acetumor.components;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.acetumor.MainActivity;
import com.example.acetumor.R;
import com.guoxiaoxing.phoenix.core.listener.ImageLoader;
import com.guoxiaoxing.phoenix.picker.Phoenix;


public class TestImageActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";
    private TestImageButton breast;
    private TestImageButton brain;
    private TestImageButton lung;
    private TestImageButton eye;
    private TestImageButton stomach;

    private TestImageButton selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.wtf("msg", "here1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image);

        final Button back = (Button) findViewById(R.id.test_back);
        final ImageButton camera = (ImageButton) findViewById(R.id.test_camera);
        breast = new TestImageButton((ImageButton) findViewById(R.id.test_choice_breast), true);
        brain = new TestImageButton((ImageButton) findViewById(R.id.test_choice_brain), false);
        lung = new TestImageButton((ImageButton) findViewById(R.id.test_choice_lung), false);
        eye = new TestImageButton((ImageButton) findViewById(R.id.test_choice_eye), false);
        stomach = new TestImageButton((ImageButton) findViewById(R.id.test_choice_stomach), false);
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
        for (int i=0; i < buttons.length; i++) {
            final int finalI = i;
            buttons[i].getBtn().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelected(buttons[finalI]);
                }
            });
        }

//        camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dispatchTakePictureIntent();
//            }
//        });


    }

    // take photos
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
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
