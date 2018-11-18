package com.example.sauhardpant.restaurantroulette.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sauhardpant.restaurantroulette.R;
import com.example.sauhardpant.restaurantroulette.ViewModel.BaseResultsViewModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class BaseResultsFragment extends Fragment {
    private static final String TAG = BaseResultsFragment.class.getSimpleName();
    private BaseResultsViewModel viewModel;

    public BaseResultsFragment() {}

    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_base_results, container, false);

        viewModel = ViewModelProviders.of(this).get(BaseResultsViewModel.class);
        viewModel.init(getContext());

        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        viewModel.getUserLocation();
                    } else {
                        Log.d(TAG, "onCreateView: Missing location permissions");
                    }
                });

        return rootView;
    }
}
