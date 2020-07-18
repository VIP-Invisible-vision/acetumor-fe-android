package com.example.acetumor.fragments;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.acetumor.components.LoginActivity;
import com.example.acetumor.components.UserFeedbackActivity;
import com.example.acetumor.components.UserForumActivity;
import com.example.acetumor.components.UserInfoActivity;
import com.example.acetumor.R;
import com.example.acetumor.components.UserArticleActivity;
import com.example.acetumor.components.UserResultActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserFragment extends Fragment {
    private Boolean loginStatus;
    private BottomNavigationView userNavigationView;

    public UserFragment() {
        this.loginStatus = false;
    }
    public UserFragment(Boolean login){
        this.loginStatus = login;
    }
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.user_nav_forum:
                            openFragment(UserForumActivity.class);
                            return true;
                        case R.id.user_nav_article:
                            openFragment(UserArticleActivity.class);
                            return true;
                        case R.id.user_nav_result:
                            openFragment(UserResultActivity.class);
                            return true;
                    }
                    return false;
                }
            };

    public void openFragment(Class fragment) {
        Intent settingIntent = new Intent(getContext(), fragment);
        startActivity(settingIntent);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        // setting page
        final Button setting = (Button)view.findViewById(R.id.user_info);
        setting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (loginStatus) {
                    Intent settingIntent = new Intent(getContext(), UserInfoActivity.class);
                    startActivity(settingIntent);
                } else {
                    Toast.makeText(getContext(), "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // feedback page
        Button feedback = (Button)view.findViewById(R.id.user_feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginStatus) {
                    Intent settingIntent = new Intent(getContext(), UserFeedbackActivity.class);
                    startActivity(settingIntent);
                } else {
                    Toast.makeText(getContext(), "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // login page
        Button login = (Button)view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loginStatus) {
                    Intent settingIntent = new Intent(getContext(), LoginActivity.class);
                    startActivity(settingIntent);
                }
            }
        });

        userNavigationView = (BottomNavigationView) view.findViewById(R.id.user_navigation);
        userNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        return view;
    }

}