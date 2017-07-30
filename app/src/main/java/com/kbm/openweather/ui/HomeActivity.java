package com.kbm.openweather.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.kbm.openweather.MainActivity;
import com.kbm.openweather.R;
import com.kbm.openweather.ui.currentweather.CurrentWeatherFragment;
import com.kbm.openweather.ui.forecast.ForecastFragment;
import com.kbm.openweather.utils.LocationStatusHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This activity contains the navigation of the app and also getting the user's current location using  Google play services location APIs
 * https://developer.android.com/training/location/index.html
 */
public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final int MY_PERMISSION_ACCESS_LOCATION = 1;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    private int lastCheckedMenuItem = -1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.rltv_loading_fetching_location)
    RelativeLayout fechingLocationRelativeLayout;
    GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initializeActionBar();
        startGettingLocation();
    }


    /**
     * initialize action bar and navigation drawer
     */
    private void initializeActionBar() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            LoginManager.getInstance().logOut();
            MainActivity.start(this);
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        lastCheckedMenuItem = id;
        if (id == R.id.nav_current_weather) {
            addCurrentWeatherFragment();
        } else if (id == R.id.nav_next_five_days) {

            if (mCurrentLocation != null) {
                addForecastFragment();
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * checks if has permission to access location if not then prompt to request permission
     */
    private void startGettingLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //should check if location is enabled

            initializeGoogleApiClient();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_ACCESS_LOCATION);
        }
    }

    void initializeGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        if (mGoogleApiClient.isConnected() == false) {
            mGoogleApiClient.connect();
        }
    }

    private void requestLocationUpdate() {
        if (mLocationRequest == null) {
            checkIfLocationIsTurnedOff();
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
            mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        mGoogleApiClient, mLocationRequest, this);
            } catch (Exception ex) {

            }

        }
    }

    private void checkIfLocationIsTurnedOff() {
        boolean res = LocationStatusHelper.checkIfLocationIsEnabled(this);
        if (res == false) {
            new AlertDialog.Builder(this).setTitle(R.string.location_services_are_off)
                    .setMessage(R.string.error_location_is_off)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(viewIntent);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();

        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
         /*   mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mCurrentLocation != null) {
                // got locations
            } else {
                //check if location services are off must turn on first
                requestLocationUpdate();
            }*/
            requestLocationUpdate();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        // do logic
        // enough for one update
        removeLocationUpdates();
        mCurrentLocation = location;
        fechingLocationRelativeLayout.setVisibility(View.GONE);
        initializeFragments(Double.toString(location.getLatitude()), Double.toString(location.getLongitude()));
    }

    private void initializeFragments(String latitude, String longitude) {
        if (lastCheckedMenuItem == -1 || lastCheckedMenuItem == R.id.nav_current_weather) {
            addCurrentWeatherFragment();
            navigationView.setCheckedItem(R.id.nav_current_weather);
        } else {
            addForecastFragment();

            navigationView.setCheckedItem(R.id.nav_next_five_days);
        }
    }

    private void addCurrentWeatherFragment() {
        if (getSupportFragmentManager().findFragmentByTag(CurrentWeatherFragment.TAG) == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frm_container, CurrentWeatherFragment.newInstance(Double.toString(mCurrentLocation.getLatitude()), Double.toString(mCurrentLocation.getLongitude())), CurrentWeatherFragment.TAG)
                    .commit();
        }
    }

    private void addForecastFragment() {
        if (getSupportFragmentManager().findFragmentByTag(ForecastFragment.TAG) == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frm_container,
                            ForecastFragment.newInstance(Double.toString(mCurrentLocation.getLatitude()), Double.toString(mCurrentLocation.getLongitude())),
                            ForecastFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_ACCESS_LOCATION) {
            if (grantResults.length > 0)
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        startGettingLocation();
                    } catch (Exception ex) {
                        String x = ex.getMessage();

                    }
                } else {
                    Toast.makeText(HomeActivity.this, R.string.location_permission_denied, Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeLocationUpdates();
    }

    private void removeLocationUpdates() {
        try {
            if (mGoogleApiClient != null)
                if (mGoogleApiClient.isConnected() == true) {
                    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);// call before disconnecting
                    mGoogleApiClient.disconnect();
                }
        } catch (Exception ex) {
            Log.e("onStop exception", ex.getMessage());


        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }
}
