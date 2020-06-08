package com.example.acetumor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.acetumor.components.UserFeedbackActivity;
import com.example.acetumor.components.UserForumActivity;
import com.example.acetumor.components.UserInfoActivity;
import com.example.acetumor.components.UserArticleActivity;
import com.example.acetumor.fragments.HomeFragment;
import com.example.acetumor.fragments.ForumFragment;
import com.example.acetumor.fragments.CompassFragment;
import com.example.acetumor.fragments.UserFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.guoxiaoxing.phoenix.core.listener.ImageLoader;
import com.guoxiaoxing.phoenix.picker.Phoenix;

import static com.example.acetumor.util.Constants.ERROR_DIALOG_REQUEST;
import static com.example.acetumor.util.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.example.acetumor.util.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    BottomNavigationView bottomNavigation;
    private boolean mLocationPermissionGranted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNavigation.setSelectedItemId(R.id.nav_home);
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


    @Override
    protected void onResume() {
        super.onResume();
        // check map service
        if (checkMapServices() && !mLocationPermissionGranted) getLocationPermission();

        // check which fragment to show
        Intent intent = getIntent();
        if (intent != null) {
            String userMessage = intent.getStringExtra(UserInfoActivity.EXTRA_MESSAGE);
            if (userMessage == null) userMessage = intent.getStringExtra(UserFeedbackActivity.EXTRA_MESSAGE);
            if (userMessage == null) userMessage = intent.getStringExtra(UserForumActivity.EXTRA_MESSAGE);
            if (userMessage == null) userMessage = intent.getStringExtra(UserArticleActivity.EXTRA_MESSAGE);
            if (userMessage != null) {
                bottomNavigation.setSelectedItemId(R.id.nav_user);
            }
            else {
                bottomNavigation.setSelectedItemId(R.id.nav_home);
            }
        }
    }




    // Map functions
    private boolean checkMapServices(){
        if(isServicesOK()){
            return isMapsEnabled();
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        assert manager != null;
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occurred but we can resolve it
            Log.d(TAG, "isServicesOK: an error occurred but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.d(TAG, "here");
        mLocationPermissionGranted = false;
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        if (requestCode == PERMISSIONS_REQUEST_ENABLE_GPS) {
            if (!mLocationPermissionGranted) {
                getLocationPermission();
            }
        }

    }

}