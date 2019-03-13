package com.example.sauhardpant.restaurantroulette.ViewModel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.apollographql.apollo.yelp.SearchYelpQuery;
import com.example.sauhardpant.restaurantroulette.ViewModel.Interfaces.BusinessResultListener;
import com.example.sauhardpant.restaurantroulette.ViewModel.Interfaces.CustomBottomNavClickListener;
import com.example.sauhardpant.restaurantroulette.model.YelpInteractor;

import java.util.List;

public class BaseResultsViewModel extends ViewModel implements CustomBottomNavClickListener {
    private static final String TAG = BaseResultsViewModel.class.getSimpleName();
    private static final long MIN_TIME = 60000; // update every minute
    private static final float MIN_DISTANCE = 500; // update every 500m

    private MutableLiveData<Location> userLocation = new MutableLiveData<>();
    private MediatorLiveData<List<SearchYelpQuery.Business>> businessList = new MediatorLiveData<>();
    private MediatorLiveData<Boolean> testLiveData = new MediatorLiveData<>();

    private Context mContext;
    private YelpInteractor interactor;

    public void init(Context context) {
        this.mContext = context;
        interactor = new YelpInteractor();

        linkMediatorSources();
    }

    @NonNull
    public LiveData<Location> getLocation() {
        return userLocation;
    }

    @NonNull
    public LiveData<List<SearchYelpQuery.Business>> getBusinessList() {
        return businessList;
    }

    @SuppressLint("MissingPermission")
    public void getUserLocation() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                userLocation.postValue(location);
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

    @Override
    public void onBottomNavMenuClicked(String menuItem) {
        Log.d(TAG, "onBottomNavMenuClicked: " + menuItem);
    }

    private void linkMediatorSources() {
        businessList.removeSource(userLocation);

        businessList.addSource(userLocation, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                interactor.getNearbyRestaurants(location, new BusinessResultListener() {
                    @Override
                    public void onResult(List businesses) {
                        for (int i = 0; i < businesses.size(); i++) {
                            businessList.postValue(businesses);
                        }
                    }

                    @Override
                    public void onError() {
                        Log.d(TAG, "onError: Something went wrong with fetching business data");
                    }
                });
            }
        });
    }


}
