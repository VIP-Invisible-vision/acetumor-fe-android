package com.example.acetumor.components;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.acetumor.MainActivity;
import com.example.acetumor.R;

public class UserForumActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forum);

        Button back = findViewById(R.id.user_forum_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "user");
                intent.putExtra("login", true);
                startActivity(intent);
            }
        });
    }


}