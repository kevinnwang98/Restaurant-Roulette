package com.example.sauhardpant.restaurantroulette.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.apollographql.apollo.yelp.SearchYelpQuery;
import com.example.sauhardpant.restaurantroulette.R;
import com.example.sauhardpant.restaurantroulette.ViewModel.BaseResultsViewModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseResultsFragment extends Fragment {
    private static final String TAG = BaseResultsFragment.class.getSimpleName();
    private BaseResultsViewModel viewModel;

    @BindView(R.id.list_view_container)
    FrameLayout listViewFragmentContainer;

    public BaseResultsFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(BaseResultsViewModel.class);
        viewModel.init(getContext());

        subscribeToViewModels();
    }

    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_base_results, container, false);
        ButterKnife.bind(this, rootView);

        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        viewModel.getUserLocation();
                    } else {
                        Log.d(TAG, "onCreateView: Missing location permissions");
                    }
                });

        if (getFragmentManager() != null) {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.list_view_container, new BusinessResultsListViewFragment()).commit();
        }

        return rootView;
    }

    public void subscribeToViewModels() {
        viewModel.getLocation().observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                Log.d(TAG, "on location changed: " + location.getLongitude() + ", " + location.getLatitude());
            }
        });
    }
}
