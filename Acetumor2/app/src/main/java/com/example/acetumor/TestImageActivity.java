package com.example.acetumor;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import android.os.Bundle;

public class  TestImageActivity extends AppCompatActivity {
=======
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestImageActivity extends AppCompatActivity {
>>>>>>> fragment_home

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image);
<<<<<<< HEAD
=======

        final Button back = (Button) findViewById(R.id.tBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
>>>>>>> fragment_home
    }
}
