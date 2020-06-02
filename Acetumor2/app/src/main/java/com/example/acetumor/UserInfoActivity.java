package com.example.acetumor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.example.acetumor.fragments.UserFragment;

public class UserInfoActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Button userInfoBack = findViewById(R.id.user_info_back);
        userInfoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "user");
                startActivity(intent);
            }
        });

        Button userInfoDone = findViewById(R.id.user_info_done);
        userInfoDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "user");
                startActivity(intent);
            }
        });

    }

}
