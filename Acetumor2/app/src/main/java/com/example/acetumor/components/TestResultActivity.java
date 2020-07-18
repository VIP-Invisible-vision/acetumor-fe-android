package com.example.acetumor.components;

import androidx.appcompat.app.AppCompatActivity;

import com.example.acetumor.MainActivity;
import com.example.acetumor.R;
import com.lwb.piechart.PieChartView;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class TestResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        Intent intent = getIntent();
        int b = intent.getIntExtra("b", TakePhotoActivity.b);
        int i = intent.getIntExtra("i", TakePhotoActivity.i);
        int iv = intent.getIntExtra("iv", TakePhotoActivity.iv);
        int n = intent.getIntExtra("n", TakePhotoActivity.n);
        Button back = findViewById(R.id.test_result_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        PieChartView pieChartView = findViewById(R.id.pie_chart_view);
        pieChartView.addItemType(new PieChartView.ItemType("Insitu", i, 0xffed544a));
        pieChartView.addItemType(new PieChartView.ItemType("Invasive", iv, 0xff76f5dd));
        pieChartView.addItemType(new PieChartView.ItemType("Benigh", b, 0xff29302f));
        pieChartView.addItemType(new PieChartView.ItemType("Normal", n, 0xffdebe7a));
    }

}