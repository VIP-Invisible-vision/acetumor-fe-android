package com.example.acetumor;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.acetumor.fragments.HomeFragment;
import com.example.acetumor.fragments.ForumFragment;
import com.example.acetumor.fragments.CompassFragment;
import com.example.acetumor.fragments.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(HomeFragment.newInstance("", ""));
    }
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            openFragment(HomeFragment.newInstance("", ""));
                            return true;
                        case R.id.nav_location:
                            openFragment(CompassFragment.newInstance("", ""));
                            return true;
                        case R.id.nav_forum:
                            openFragment(ForumFragment.newInstance("", ""));
                            return true;
                        case R.id.nav_user:
                            openFragment(UserFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };
}