package com.example.acetumor.components;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.acetumor.MainActivity;
import com.example.acetumor.R;

public class UserFeedbackActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feedback);

        Button submit = findViewById(R.id.user_feedback_submit);
        Button back = findViewById(R.id.user_feedback_back);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "user");
                intent.putExtra("login", true);
                startActivity(intent);
            }
        });

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
