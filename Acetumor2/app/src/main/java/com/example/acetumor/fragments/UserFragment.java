package com.example.acetumor.fragments;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.acetumor.components.UserFeedbackActivity;
import com.example.acetumor.components.UserForumActivity;
import com.example.acetumor.components.UserInfoActivity;
import com.example.acetumor.R;
import com.example.acetumor.components.UserArticleActivity;
import com.example.acetumor.components.UserResultActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private BottomNavigationView userNavigationView;
    public UserFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
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
                Intent settingIntent = new Intent(getContext(), UserInfoActivity.class);
                startActivity(settingIntent);
            }
        });

        // feedback page
        Button feedback = (Button)view.findViewById(R.id.user_feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingIntent = new Intent(getContext(), UserFeedbackActivity.class);
                startActivity(settingIntent);
            }
        });

        userNavigationView = (BottomNavigationView) view.findViewById(R.id.user_navigation);
        userNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        return view;
    }

}