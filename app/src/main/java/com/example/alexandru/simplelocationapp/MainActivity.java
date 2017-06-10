package com.example.alexandru.simplelocationapp;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private final String LOG_TAG = "locationApp";
    private final int MAX_INACTIVE = 5;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;
    private TextView textViewLocation;
    private TextView textViewLatitude;
    private TextView textViewLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        textViewLocation = (TextView) findViewById(R.id.locationTextView);
        textViewLatitude = (TextView) findViewById(R.id.locationLat);
        textViewLongitude = (TextView) findViewById(R.id.locationLong);

    }


    @Override
    protected void onStart() {
        super.onStart();

        mGoogleApiClient.connect();
    }


    @Override
    protected void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(MAX_INACTIVE);

        Log.e(LOG_TAG, mGoogleApiClient.isConnected() + "");


        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);

        // mGoogleApiClient.

    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.e(LOG_TAG, "SUSPENDED");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e(LOG_TAG, "FAILED " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.e(LOG_TAG, location.toString());
        textViewLocation.setText(location.toString());
        textViewLatitude.setText("Lat: " + location.getLatitude() + "");
        textViewLongitude.setText("Long: " + location.getLongitude() + "");



    }
}
