package com.example.sauhardpant.restaurantroulette.ViewModel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.example.sauhardpant.restaurantroulette.model.YelpInteractor;

public class BaseResultsViewModel extends ViewModel {
    private static final String TAG = BaseResultsViewModel.class.getSimpleName();
    private static final long MIN_TIME = 60000; // update every minute
    private static final float MIN_DISTANCE = 500; // update every 500m

    private Context mContext;
    private Location mUserLocation;
    private YelpInteractor interactor;

    public void init(Context context) {
        this.mContext = context;
        interactor = new YelpInteractor();
    }

    @SuppressLint("MissingPermission")
    public void getUserLocation() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                interactor.getNearbyRestaurants(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
    }


}
