package com.example.acetumor.components;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.acetumor.MainActivity;
import com.example.acetumor.R;
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
                intent.putExtra("login", true);
                startActivity(intent);
            }
        });

        // submit user information to backend
        Button userInfoDone = findViewById(R.id.user_info_done);
        userInfoDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "user");
                intent.putExtra("login", true);
                startActivity(intent);
            }
        });

        EditText userName = findViewById(R.id.user_edit_name);
        EditText password1 = findViewById(R.id.change_password_1);
        EditText password2 = findViewById(R.id.change_password_2);

        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("username");
            if (username != null) {
                userName.setText(username);
            }

        }

    }

}
